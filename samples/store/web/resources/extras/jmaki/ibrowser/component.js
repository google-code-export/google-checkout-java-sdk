/* Copyright 2006 Sun Microsystems, Inc. All rights reserved. You may not modify,
   use, reproduce, or distribute this software except in compliance with the terms
   of the License at: http://developer.sun.com/berkeley_license.html
   $Id: component.js,v 1.12 2006/12/31 19:50:05 gmurray71 Exp $ 
*/

/**
* ImageScroller - A multipurpose item brower
* @ Author: Greg Murray
*
*/
// define the namespaces
jmaki.namespace("jmaki.widgets.extras.jmaki.ibrowser");

if(!jmaki.FlickrLoad) {

jmaki.FlickrProxySearch = function(service, topic){

    var target;
    
    if (typeof  topic == 'undfined') {
        topic = "flickrSearch";
    }
	
    this.searchPhotos = function(tags) {
        // build and encode the last URL parameter tags=_target.value
        target = encodeURIComponent("tags=" + tags);
        var url = service + "?key=flickrtagsearch&urlparams=" + target;
        jmaki.doAjax({url: url, callback: function(req) { var _req=req; postProcess(_req);}});
    }
	
    function postProcess(req) {
        if (req.readyState == 4) {
            if (req.status == 200) {
                if (req.responseText != '') {
                    var response = eval("(" + req.responseText + ")");
		    jmaki.publish(topic, response.photos);
                } else {
	                jmaki.publish(topic, []);
	            }
            }
        }
    }
}
/**
*  Insert a script tag in the head of the document which will inter load the flicker photos
*  and call jsonFlickrFeed(obj) with the corresponding object.
*
*/
jmaki.FlickrLoader = function(apiKey) {
    
    this.load = function(tags, callback) {
        if (typeof _globalScope.flickrListeners == 'undefined') {
            _globalScope.flickrListeners = {};
        }
        var listeners = _globalScope.flickrListeners[tags];
        if (typeof listeners == 'undefined') {
            listeners = [];
        }
        listeners.push(callback);
        _globalScope.flickrListeners[tags] = listeners;      
        
        _globalScope.jsonFlickrFeed = function(args) {

            var title = args.title;
            var tagsEnd = title.indexOf("tagged ");
            var tagNames = title.substring(tagsEnd + "tagged ".length, title.length);
            tagNames = tagNames.replace(/ and /, ',');
            var tListeners = _globalScope.flickrListeners[tagNames];
            if (tListeners != null) {
                for (var i = 0; i < tListeners.length; i++) {
                    tListeners[i](args,tagNames);
                }
                // release the listeners for this tag
                delete _globalScope.flickrListeners[tagNames];
            }
        }
        var s = document.createElement("script");
        var url ="http://www.flickr.com/services/feeds/photos_public.gne?tags=" + tags + "&format=json";
        if (typeof apiKey != 'undefined') {
            url += "appid=" + apiKey;
        }
        s.src = url;
        s.type = "text/javascript";
        s.charset = "utf-8";
        document.body.appendChild(s);      
    }
}


}


if (!jmaki.widgets.extras.jmaki.ibrowser.ImageScroller) {

	jmaki.widgets.extras.jmaki.ibrowser.ImageScroller = function(baseDir,
	 divId,
	 startWidth
	 ,startHeight,
	 si) {
	    var _this = this;
	    this.uuid = divId;
	    var initialized = false;
	    var scaleIcons = true;
	    var autoSizeH = false;
	    var autoSizeW = false;
	    
	    var oldResize;
	    var prevSize = -1;
	    if (typeof si != 'unefined') scaleIcons = si;
	
	   // this is the main container div
	    var containerDiv = document.getElementById(this.uuid);
	    var imageDiv;
	
	    // default sizes are all based on the width of the container   
	    var VIEWPORT_WIDTH;
	
	    if (typeof startWidth != 'undefined') {
	        VIEWPORT_WIDTH = Number(startWidth);
	     	containerDiv.style.width = VIEWPORT_WIDTH + "px";
	    } else {
	        VIEWPORT_WIDTH = containerDiv.clientWidth;
	        autoSizeW = true;
	    }
	
	    var VIEWPORT_HEIGHT;
	
	    if (typeof startHeight != 'undefined') {
	        VIEWPORT_HEIGHT = Number(startHeight);
	        containerDiv.style.height = VIEWPORT_HEIGHT + "px";
	    } else {
	        VIEWPORT_HEIGHT = containerDiv.clientHeight;
	        containerDiv.style.height = VIEWPORT_HEIGHT + "px";
	        autoSizeH = true;
	    }
	
	    this.CHUNK_SIZE=5;
	    this.PREFETCH_THRESHHOLD = 3;
	   
	    var IMAGE_PANE_ID =  this.uuid  + "_imagePane";
	    var IMAGE_PANE_BUFFER_ID = this.uuid  + "_imageBufferPane";
	    var PADDING = 3;
	    
	    var LOADING_IMG_URI = baseDir + "/images/indicator-black.gif";
	    var MINIMIZE_IMG_URI = baseDir + "/images/minimize.gif";
	    var MAXIMIZE_IMG_URI = baseDir + "/images/maximize.gif";
	    var INDICATOR_IMG_URI = "/images/indicator-black.gif";
	    var MAXIMIZE_IMG_TOOLTIP = "Show Details";
	    var MINIMIZE_IMG_TOOLTIP = "Show Less Details";
	    var RIGHT_IMG_URI = baseDir + "/images/right.gif";
	    var LEFT_IMG_URI = baseDir + "/images/left.gif";
	
	    var leftButton;
	    var rightButton;
	    
	    var fontSize;
	    var IMAGEPANE_WIDTH;
	    var NAV_WIDTH;
	    var NAV_HEIGHT;
	    var ICON_SIZE;
	    var IMAGEPANE_HEIGHT;
	
	    var INFOPANE_DEFAULT_HEIGHT;
	    var INFOPANE_EXPAND_HEIGHT;
	    var THUMB_WIDTH;
	    var THUMB_HEIGHT;
	
	    // this is an array of the tiles which are divs for each thumb
	
	    var tiles = [];
	
	    // for scrolling
	    var SCROLL_INCREMENT = 5;
	    var INFOPANE_INCREMENT = 3;
	
	    var tileY;
	    var tileX;
	
	    // this is the index of the image tile on the far left
	    var index = 0;
	
	    // keeps track how for the scroll has gone 
	    var offset = 0;
	
	    var timeout = 30; // in ms
	    var isScrollingRight = false;
	    var isScrollingLeft = false;
	
	    // large image pane
	    var imageDiv;
	    var imagePane;
	    var imageLoadingPane;
	
	    // images 
	    var minimizeImage;
	    var indicatorImage;
	
	    var infoPane;
	
	    var minimizeLink;
	    var infoPaneLoop = 0;
	    var maximizing = false;
	    var minimizing = false;
	    var maximized = false;
	
	    // prefetch thresh-hold
	    var prefetchThreshold = 2;
	
	    // a growing list of items;
	
	    var items = [];
	
	    // used for debugging when debug is true
	    var debug = false;
	    
	    var statusDiv;
	    var status2Div;   
	    var showingBuffer = false;
	    var imageBuffer;
	    var imageReloadTries = 0;
	    var IMG_RELOAD_RETRY_MAX = 30;
	    
	    // used for url book marking
	
	    var pid;
	    var currentChunck;
	
	     // this map contains all the items 
	
	    var map;
	
	    // callback handler
	    this.callbackHandler;
	    
	    this.initialize = function () {
	        map = new jmaki.Map();
	        
	        if (typeof window.onresize != 'undefined') {
	            oldResize = window.onresize;
	        }
                // add a resize listener
                if (window.addEventListener) {
                   window.addEventListener("resize", resize, false);
                } else if (window.attachEvent) {
                    window.attachEvent("onresize", resize);
                }
	       // window.onresize =resize;
	
		var loadImage;
	        // for status output
	        statusDiv = document.getElementById("status");
	        status2Div = document.getElementById("status_2");
	        initLayout();
	        initialized = true;
	    }
	    
	    function resize(){
	        if (prevSize != document.body.clientWidth) {
	            window.onresize = null;   
	            if (oldResize) oldResize();
	            calculateSizes();
	            layout();
	
	            window.onresize = resize;
	            }
	    }
	
	    // load up
	    _this.initialize();
	
	    this.getItems = function() {
	        return map;
	    }
	
	    this.getGroupId = function() {
	        return pid;
	    }
	
	    this.reset = function() {
	        resetTitles()
	        tiles = [];
	        index = 0;
	        offset = 0;
	        currentChunck = 0;
                leftButton.style.visibility="hidden";
                rightButton.style.visibility="hidden";
	    }
	
	    function resetTitles() {
	        for (var l = 0; l < tiles.length; l++) {
	            tiles[l].parentNode.removeChild(tiles[l]);
	        }
	    } 
	
	    // event bound to the mouseOut event of both scroll buttons
	    function scrollDone() {
	        isScrollingLeft = false;
	        isScrollingRight = false;
	    }
	
	    // looping method for time out 
	    function scroll() {
	        if (isScrollingRight) scrollRight();
	        else if (isScrollingLeft) scrollLeft();
	    }
	
	     // do the value list pre-emptive fetching
	    function prefetch() {
	        if (isScrollingRight && (index + _this.PREFETCH_THRESHHOLD) % _this.CHUNK_SIZE == 0) {
	            if ((index / _this.CHUNK_SIZE) != currentChunck) {
	                currentChunck = index / _this.CHUNK_SIZE;
	                // fire an event
	                if (typeof _this.callbackHandler != 'undefined') {
	                    _this.callbackHandler({type:"getChunck", id: pid, index: index, currentChunck: currentChunck, fontSize:fontSize});
	                }
	            }
	        }
	    }
	
	    this.setGroupId = function(id) {
	        pid = id;
	    }
	
	    this.addItems = function(inItems) {
	
	        for (var loop=0; loop < inItems.length ; loop++) {
	            items.push(inItems[loop]);
	            map.put(inItems[loop].id, inItems[loop]);
	            createTile(inItems[loop]);
	            if (loop == 0 && !loadImage) {
	                showImage(inItems[loop].id);
	            }   
	        }
	
	        drawTiles();
	        rightButton.style.visibility="visible";
	        _this.hideProgressIndicator();
	    }
	    
	    this.showProgressIndicator = function() {
	        if (indicatorImage) {
	            indicatorImage.style.visibility = "visible";
	        }
	    }  
	
	    this.hideProgressIndicator = function() {
	        indicatorImage.style.visibility = "hidden";
	    }
	
	    function postImageLoad(loadIntoBuffer) {
	        if (debug) {
	            status2Div.innerHTML = "Try " + imageReloadTries + " " + url + " image.complete=" + imageBuffer.complete;
	        }
	
	        // keep calling this funtion until imageReloadTries < IMG_RELOAD_RETRY_MAX
		    if (!imageBuffer.complete) {
	            if (imageReloadTries < IMG_RELOAD_RETRY_MAX) {
	                setTimeout(function(){
	                    this.loadIntoBuffer = loadIntoBuffer;
	                    postImageLoad(loadIntoBuffer);
	                    },
	                    500);
	            } else {
	                _this.hideProgressIndicator();
	            }
	            imageReloadTries = imageReloadTries + 1;
	            return;
	        }
	
	        var id;
	        _this.hideProgressIndicator();
	        if (loadIntoBuffer) {
	            imageLoadingPane.src = imageBuffer.src;
	        } else {
	           imagePane.src = imageBuffer.src;
	        }
	        // do a cross fade as long as the images aren't the same
	        if (imageLoadingPane.src != imagePane.src) {
	            crossFade(0,loadIntoBuffer );
	        }
	    }
	
	
	    this.showImage = function(itemId) {
	        _this.showProgressIndicator();
	        var i = map.get(itemId); 
	        if (!i) {
	            return;
	        }
	        if (typeof this.callbackHandler != 'undefined') {
	            this.callbackHandler({type:"showingItem", id: itemId, gid: pid, uuid: _this.uuid, fontSize:fontSize});
	        }
	
	        // create the image pane and append the description nodes
	        // asumption is that if the imagePane is not set neigher are the info children
	        if (typeof imagePane == 'undefined') {
	            imagePane = document.createElement("img");
	            imagePane.style.visibility = "hidden";
	            imagePane.style.position = "absolute";
	            imagePane.id = IMAGE_PANE_ID;
	            imageLoadingPane = document.createElement("img");
	            imageLoadingPane.style.position = "absolute";
	            imageLoadingPane.style.visibility = "hidden";
	            imageLoadingPane.id = IMAGE_PANE_BUFFER_ID;
	            var border = 2;
	            if (typeof imageDiv.parentNode != "undefined") {
	                imageDiv.parentNode.appendChild(imagePane);
	                imageDiv.parentNode.appendChild(imageLoadingPane);
	                imagePane.style.left = tileX + border + "px";
	                imagePane.style.top = tileY + border + "px";
	                imageLoadingPane.style.left = tileX + border + "px";
	                imageLoadingPane.style.top = tileY + border + "px";
	            }
	
	            imagePane.style.width = (IMAGEPANE_WIDTH - border) + "px";
	            imagePane.style.height = (IMAGEPANE_HEIGHT - border) + "px";
	            imageLoadingPane.style.width = (IMAGEPANE_WIDTH - border) + "px";
	            imageLoadingPane.style.height = (IMAGEPANE_HEIGHT - border) + "px";
	            loadImage(i.imageURL, false);
	        } else {
	             imageLoadingPane.style.visibility = "visible";            
	             if (showingBuffer) {
	                 showingBuffer = false;
	             } else {
	                 showingBuffer = true;
	             }
	             loadImage(i.imageURL, showingBuffer);
	        }
	    }
	
	    function loadImage(url, loadIntoBuffer) {
	        imageReloadTries = 0;
	        imageBuffer = new Image();
	        if (loadIntoBuffer) {
	            imageBuffer.src = url;
	            imageLoadingPane.onLoad = setTimeout(function(){
                        this.url=url;this.loadIntoBuffer = loadIntoBuffer;postImageLoad(loadIntoBuffer,url);},0);
	        } else {
	            imageBuffer.src = url;
	            imageBuffer.onLoad = setTimeout(function(){
                        this.url = url;this.loadIntoBuffer = loadIntoBuffer;postImageLoad(loadIntoBuffer,url);},0);
	        }
	    }
	
	    function setOpacity(opacity, id) {
	
	        var target = document.getElementById(id);
	        if (typeof target.style.filter != 'undefined') {
	            target.style.filter = "alpha(opacity:" + opacity + ")"; 
	        } else {
	            target.style.opacity = opacity/100;
	        }            
	    }
	
	    function crossFade(count,loadIntoBuffer) {
	       imagePane.style.visibility = "visible";
	       var percentage = Number(count);
	        if (loadIntoBuffer) {
	            setOpacity(100 - percentage, IMAGE_PANE_ID);
	            setOpacity(percentage, IMAGE_PANE_BUFFER_ID);
	        } else {
	            setOpacity(100 - percentage, IMAGE_PANE_BUFFER_ID);
	            setOpacity(percentage, IMAGE_PANE_ID);
	        }
	
	       if (percentage < 100) {
	            percentage = percentage + 10;
	            setTimeout(function(){
                        this.loadIntoBuffer = loadIntoBuffer;this.percentage = percentage;crossFade(percentage,loadIntoBuffer);}, 25);
	        }
	    }
	
	    
	    // calling this function will result in the maximizing event being fired
	    // if the pane is maximized it will asume the event want to minimize
	    this.doMaximize = function() {
	        if (!maximizing && !minimizing && !maximized) {
	            infoPaneLoop = INFOPANE_DEFAULT_HEIGHT;
	            maximizing = true;
	            minimizing = false;
	        } else if (!maximizing && !minimizing) {
	            minimizing = true;
	            maximizing = false;
	        }
	        setTimeout(changeInfoPane, 0);
	    }
	
	    // will handle either minimizing or maximing but not both
	    // this method is called recursively until the maximinging 
	    // or minimizing is done.
	    function changeInfoPane() {
	        if (maximizing) {
	            maxmizeInfoPane();
	        } else if (minimizing) {
	            minimizeInfoPane();
	        }
	    }
	
	    function maxmizeInfoPane() {
	
	        if (infoPaneLoop < INFOPANE_EXPAND_HEIGHT) {
	            infoPaneLoop = infoPaneLoop + INFOPANE_INCREMENT;
	            var clipMe = 'rect(' + '0px,' + VIEWPORT_WIDTH +  'px,'+  infoPaneLoop +'px,' +  0 + 'px)';
	            infoPane.style.clip = clipMe;
	            infoPane.style.height = infoPaneLoop + "px";
	            infoPane.style.top = (tileY + (PADDING *1) + INFOPANE_DEFAULT_HEIGHT + IMAGEPANE_HEIGHT) - infoPaneLoop + "px";
	            setTimeout(changeInfoPane, 5);
	        } else {
	            minimizeImage.className = "infopaneMinimizeIcon";
	            minimizeImage.src = MINIMIZE_IMG_URI;
	            minimizeLink.title = MINIMIZE_IMG_TOOLTIP;
	            maximized = true;
	            maximizing = false;
	            minimizing = false;
	        }
	    }
	
	    function minimizeInfoPane() {
	
	       if (infoPaneLoop > INFOPANE_DEFAULT_HEIGHT) {
	            infoPaneLoop = infoPaneLoop - INFOPANE_INCREMENT;
	            var clipMe = 'rect(' + '0px,' + VIEWPORT_WIDTH +  'px,'+  infoPaneLoop +'px,' +  0 + 'px)';
	            infoPane.style.clip = clipMe;
	            infoPane.style.height = infoPaneLoop + "px";
	            infoPane.style.top = (tileY + (PADDING) + INFOPANE_DEFAULT_HEIGHT + IMAGEPANE_HEIGHT) - infoPaneLoop + "px";
	            if (debug) {
	                status2Div.innerHTML = "minimize infoPaneLoop =" + infoPaneLoop +  " infopane.top=" + infoPane.style.top;
	            }
	            setTimeout(changeInfoPane, 5);
	        } else {
		        minimizeImage.src = MAXIMIZE_IMG_URI;
	            minimizeLink.title = MAXIMIZE_IMG_TOOLTIP;
	            maximizing = false;
	            minimizing = false;
	            maximized = false;
	        }
	    }
	
	    function scrollRight() {
	
	        isScrollingRight = true;
	        if (index >= tiles.length) {
	            // hide the rightButton
	            rightButton.style.visibility="hidden";
	            return;
	        } else {
	            leftButton.style.visibility="visible";
	        }
	
	        offset = offset - SCROLL_INCREMENT;
	        drawTiles();
	        setTimeout(scroll, timeout);
	    }
	
	    function getNext() {
	        isScrollingRight = true;
	        setTimeout(scroll, timeout);
	    }
	
	    function getPrevious () {
	        isScrollingLeft = true;
	        setTimeout(scroll, timeout);
	    }
	
	    function scrollLeft() {
	        if (offset >= 0) {
	            leftButton.style.visibility="hidden";
	            return;
	        } else {
	            leftButton.style.visibility="visible";
	        }
	        offset = offset + SCROLL_INCREMENT;
	        drawTiles();
	        setTimeout(scroll, timeout);
	    }
	
	    function drawTiles() {
	
	        // draw the first one if its off the screen
	        // check if the far right image is out view
	        var overHang;
	        var tileTop = tileY + INFOPANE_DEFAULT_HEIGHT + IMAGEPANE_HEIGHT + (PADDING)+ "px";
	        var temp = offset;
	        index = Math.floor((offset)/THUMB_WIDTH); 
	        overHang =  offset % THUMB_WIDTH;
	
	        if (overHang < 0) {
	            overHang = overHang * -1;
	        }
	
	        if (index < 0) {
	            index = index * -1;
	        }
	
	        // check for next set of images
	        prefetch();
	        var startIndex = index;
	        if (overHang > 0 && index > 0) {
	            startIndex = index -1;
	        }
	
	        var stopIndex = index + Math.round(VIEWPORT_WIDTH / THUMB_WIDTH);    
	        if (stopIndex > tiles.length) {
	            stopIndex = tiles.length;
	        }
	        var displayX = 0;
	
	        if (startIndex > 0) {
	            tiles[startIndex-1].style.width = THUMB_WIDTH + "px";
	            tiles[startIndex-1].style.height = THUMB_HEIGHT + "px";
	            var clipMe = 'rect(' + '0px,' + THUMB_WIDTH +  'px,'+  (THUMB_HEIGHT + 1) +'px,' + THUMB_WIDTH + 'px)';
	            tiles[startIndex-1].style.clip = clipMe;
	        }
	
	        for (var tl=startIndex; tl < stopIndex; tl++) {
	            if (debug) {
	             statusDiv.innerHTML = "overhang=" + overHang +  " startIndex=" + startIndex +
                                           " stopIndex="  + stopIndex + " offset=" + offset + " displayX=" + displayX;
	            }
	
	            // this is for the first tile to be diplayed
	            if (overHang  > 0 && tl == startIndex) {
	                rightButton.style.visibility="visible";
	                // clip: rect(top right bottom left) - borders of the clipped area
	                // clip the left
	                var clipMe = 'rect(' + '0px,' + THUMB_WIDTH +  'px,'+  (THUMB_HEIGHT + 1) +'px,' +  overHang + 'px)';
	                tiles[tl].style.clip = clipMe;
	                tiles[tl].style.left = (tileX  - overHang) + "px";
	                tiles[tl].style.top = tileTop;
	                displayX = displayX + (THUMB_WIDTH - overHang);
	                // render the far right squares
	            } else if (tl == stopIndex -1) {
	                var underHang = VIEWPORT_WIDTH - displayX ;
	                if (underHang > 0) {
	
	                    var clipMe = 'rect(' + '0px,' + (underHang) + "px," + (THUMB_HEIGHT + 1) +'px,' +  0 + 'px)';
	                    tiles[tl].style.clip = clipMe;
	                    tiles[tl].style.left =  tileX + (offset + (tl * THUMB_WIDTH)) + 'px';
	                    tiles[tl].style.visibility = "visible";
	                    tiles[tl].style.top = tileTop;
	                    // resize the previous one to its real length
	                } else if (underHang < 0 && tl > 0) {
	                    var clipMe = 'rect(' + '0px,' + (THUMB_WIDTH + underHang) + "px," + (THUMB_HEIGHT + 1) +'px,' +  0 + 'px)';
	                    tiles[tl-1].style.clip = clipMe;
	                    tiles[tl-1].style.visibility = "visible";
	                    tiles[tl-1].style.top = tileTop;
	                    tiles[tl-1].style.left = tileX + (offset + ((tl -1) * THUMB_WIDTH)) + 'px';
	                } else { 
	                    tiles[tl].style.left = '0px';
	                    tiles[tl].style.visibility = "hidden";
	                }
	                // render everything else
	            } else {
	                // restore to full length in case it has not already
	                var clipMe = 'rect(' + '0px,' + THUMB_WIDTH  + "px," + (THUMB_HEIGHT + 1) +'px,' +  0 + 'px)';
	                tiles[tl].style.clip = clipMe;
	                displayX = displayX + THUMB_WIDTH;
	                tiles[tl].style.left = tileX + (offset + (tl * THUMB_WIDTH)) + 'px';
	                tiles[tl].style.visibility = "visible";
	                tiles[tl].style.top = tileTop;
	            }	    	
	        }
	        if (stopIndex < tiles.length) {
	            tiles[stopIndex].style.visibility = "hidden";
	            tiles[stopIndex].style.left = "0px";
	        }
	    }
	    
	    function initLayout() {
 	        calculateSizes();
	        leftButton = document.createElement("img");
	        rightButton = document.createElement("img");
	        infoPane = document.getElementById(_this.uuid + "_infopane");
	        infoPane.style.visibility="visible";
	        leftButton.style.visibility="hidden";
	        if (rightButton.attachEvent) {
	            rightButton.attachEvent('onmouseover',function(e){scrollDone();getNext();});
	            rightButton.attachEvent('onmouseout',function(e){scrollDone();});
	            leftButton.attachEvent('onmouseover',function(e){scrollDone();getPrevious();});
	            leftButton.attachEvent('onmouseout',function(e){scrollDone();});
	        } else if (rightButton.addEventListener) {
	            rightButton.addEventListener('mouseover',function(e){scrollDone();getNext();}, false);
	            rightButton.addEventListener('mouseout',function(e){scrollDone();}, false);
	            leftButton.addEventListener('mouseover',function(e){scrollDone();getPrevious();}, false);
	            leftButton.addEventListener('mouseout',function(e){scrollDone();}, false);
	        }
	        
	        // create image div
	        imageDiv = document.createElement("div");
	        containerDiv.appendChild(imageDiv);
	        imageDiv.className = "imagePane";
	        
	        rightButton.className = "scrollButton";
	        leftButton.className = "scrollButton";
	        rightButton.src = RIGHT_IMG_URI;
	        leftButton.src = LEFT_IMG_URI;
	        if (!scaleIcons) {
	            rightButton.style.height = "22px";
	            rightButton.style.width = "23px";
	            leftButton.style.height = "22px";
	            leftButton.style.width =  "23px";
	        }
	
	        containerDiv.appendChild(rightButton);
	        containerDiv.appendChild(leftButton);
	        createInfoPane();
	        layout();
	    }
	    
	    function calculateSizes() {
            var containerDiv = document.getElementById(_this.uuid);
            if (!containerDiv) return;
	        var pos = getPosition(containerDiv);
	            if (autoSizeH) {
	                if (containerDiv.parentNode.nodeName == "BODY") {
	                    if (window.innerHeight){
	                        VIEWPORT_HEIGHT = window.innerHeight - pos.y -10;
	                    } else {
	                        VIEWPORT_HEIGHT = containerDiv.clientHeight -1;
	                    }
	                    containerDiv.style.height = VIEWPORT_HEIGHT + "px";
	                } else {
	                    var _tNode = containerDiv.parentNode;
	                    while(_tNode != null &&
	                        _tNode.clientHeight == 0) {
	                        _tNode = _tNode.parentNode;
	                    }
	                    if (_tNode == null) {
	                        VIEWPORT_HEIGHT = 400;
	                    } else {
	                        VIEWPORT_HEIGHT = _tNode.clientHeight -1;
	                    }
	                    containerDiv.style.height = VIEWPORT_HEIGHT + "px";
	                }
	            }
	
	            if (autoSizeW) {
	                //VIEWPORT_WIDTH  = containerDiv.parentNode.clientWidth - 1;
	                var _tNode = containerDiv.parentNode;
	                while(_tNode != null &&
	                      _tNode.clientWidth == 0) {
	                      _tNode = _tNode.parentNode;
	                }
	                if (_tNode == null) {
	                    VIEWPORT_WIDTH = 400;
	                } else {
	                    VIEWPORT_WIDTH = _tNode.clientWidth -1;
	                }
	                containerDiv.style.width = VIEWPORT_WIDTH + "px";
	            }
	         prevSize = VIEWPORT_WIDTH;
	        // all sizes are realitive the the viewport width
	        fontSize = Math.round(VIEWPORT_HEIGHT * .06);
	        IMAGEPANE_WIDTH = VIEWPORT_WIDTH-1;
	        NAV_WIDTH = Math.round(VIEWPORT_WIDTH * .0575);
	        NAV_HEIGHT = Math.round(VIEWPORT_HEIGHT * .0575);
	        ICON_SIZE = Math.round(VIEWPORT_WIDTH * .035);
	        IMAGEPANE_HEIGHT = Math.round(VIEWPORT_HEIGHT / 1.49);
	        INFOPANE_DEFAULT_HEIGHT = Math.round(VIEWPORT_HEIGHT / 5.67);
	        INFOPANE_EXPAND_HEIGHT = Math.round(VIEWPORT_HEIGHT / 2.86);
	        THUMB_WIDTH = Math.round(VIEWPORT_WIDTH / 5);;
	        THUMB_HEIGHT = Math.round(VIEWPORT_HEIGHT / 6.67);
	    }
	
         function layout() {
            var containerDiv = document.getElementById(_this.uuid);
            if (!containerDiv) return;
            if (!rightButton) return;
            if (imageDiv) {
	          imageDiv.style.height = IMAGEPANE_HEIGHT - 2  + "px";
	          imageDiv.style.width = IMAGEPANE_WIDTH - 2 + "px";
	        }
	        if (scaleIcons) {
	            rightButton.style.height = NAV_HEIGHT + "px";
	            rightButton.style.width = NAV_WIDTH + "px";
	            leftButton.style.height = NAV_HEIGHT + "px";
	            leftButton.style.width = NAV_WIDTH + "px";
	        }
	        var point = getPosition(containerDiv);
	        tileX = 0;
	        tileY = 0;
	        var ua = navigator.userAgent.toLowerCase();
	        // TODO: make generic
	        if (ua.indexOf('Safari') != -1) {
	            timeout = 20;
	        }        
	        if (rightButton) {
                var buttonW = Number(rightButton.style.width.split('px')[0]);
                var rightX = tileX + VIEWPORT_WIDTH - (buttonW + PADDING);
                leftButton.style.left = (tileX + PADDING) + "px";
                var buttonY = Math.round(tileY + INFOPANE_DEFAULT_HEIGHT + IMAGEPANE_HEIGHT + (buttonW /2)) ;
	            rightButton.style.top = buttonY + "px";
	            rightButton.style.left = rightX + "px";
            }
            
	        leftButton.style.top = buttonY + "px";
	        imageDiv.style.left = tileX + 1 + "px";
	        imageDiv.style.top = tileY + 1 + "px";
	        
	        if (typeof imagePane != 'undefined') {           
	             imagePane.style.left = tileX + 1;
	             imagePane.style.top = tileY + 1;
	             imagePane.style.width = (IMAGEPANE_WIDTH - 2) + "px";
	             imagePane.style.height = (IMAGEPANE_HEIGHT - 2) + "px";
	         }
	        if (typeof imageLoadingPane != 'undefined') {           
	             imageLoadingPane.style.left = tileX + 1;
	             imageLoadingPane.style.top = tileY + 1;
	             imageLoadingPane.style.width = (IMAGEPANE_WIDTH - 2) + "px";
	             imageLoadingPane.style.height = (IMAGEPANE_HEIGHT - 2) + "px";
	         }
	        infoPane.style.width = VIEWPORT_WIDTH + "px";
	        infoPane.style.left = tileX + "px";
	        if (maximized) {
	            infoPane.style.top = (tileY + (PADDING) + INFOPANE_DEFAULT_HEIGHT + IMAGEPANE_HEIGHT) - infoPaneLoop + "px";
	            var clipMe = 'rect(' + '0px,' + VIEWPORT_WIDTH +  'px,'+  INFOPANE_DEFAULT_HEIGHT + infoPaneLoop +'px,' +  0 + 'px)';
                    infoPane.style.clip = clipMe;
	        } else {
	            infoPane.style.height = INFOPANE_DEFAULT_HEIGHT  + "px";
	            infoPane.style.top = (tileY + IMAGEPANE_HEIGHT  + (PADDING)) + "px";
	            var clipMe = 'rect(' + '0px,' + VIEWPORT_WIDTH +  'px,'+  INFOPANE_DEFAULT_HEIGHT  +'px,' +  0 + 'px)';
                    infoPane.style.clip = clipMe;
	        }
	
	        if (maximized) {  
	            if (typeof infoPane.style.height != 'undefined') { 
	                var h = infoPane.style.height.split('px');
	                infoPaneLoop = h[0];
	            }
	        } else {
	            infoPaneLoop = INFOPANE_DEFAULT_HEIGHT;
	        }
	        if (scaleIcons) {
	        	indicatorImage.style.width = ICON_SIZE + "px";
	        	indicatorImage.style.height = ICON_SIZE + "px";
				minimizeImage.style.width = ICON_SIZE + "px";
	        	minimizeImage.style.height = ICON_SIZE + "px";
	    	}
	        if (typeof this.callbackHandler != 'undefined') {
	        	this.callbackHandler({type:"showingItem", id: itemId, gid: pid, uuid: _this.uuid, fontSize:fontSize});
	        }
	        drawTiles();
	    }
	    
	    function createInfoPane() {
	
	        var infoTableMinimize = document.getElementById(_this.uuid + "_infopaneDetailsIcon");
	        indicatorImage = document.createElement("img");
	        indicatorImage.src = LOADING_IMG_URI;     
	        indicatorImage.className = "indicatorIcon";
	
	
	        indicatorImage.style.visibility = "hidden";
	        var indicatorCell = document.getElementById(_this.uuid  + "_infopaneIndicator");
	        if (indicatorCell) {
	            indicatorCell.style.width = (10);
	            indicatorCell.appendChild(indicatorImage);
	        }        
	
	        minimizeLink = document.createElement("a");
	        minimizeLink.title = MAXIMIZE_IMG_TOOLTIP;
	        minimizeImage = document.createElement("img");
	        minimizeImage.className = "infopaneDetailsIcon";
	        minimizeImage.src = MAXIMIZE_IMG_URI;
	        minimizeImage.className = "infopaneMaximizeIcon";
	        if (infoTableMinimize) {
	            minimizeLink.appendChild(minimizeImage);
	            infoTableMinimize.appendChild(minimizeLink);
	        }
	        // attach event listeners for the expanding onf the infopane
	        if (typeof minimizeLink.attachEvent != 'undefined') {
	            minimizeLink.attachEvent("onclick",function(e){_this.doMaximize();});
	        } else {
	            minimizeLink.addEventListener("click",function(e){_this.doMaximize();}, true);
	        }
	    }
        
            this.destroy = function() {
                // add a resize listener
               if (window.removeEventListener) {
                   window.removeEventListener("resize", resize, false);
               } else if (window.removeEvent) {
                    window.attachEvent("onresize", resize);
               }            
            }

	
	    function createTile(i) {
	
	        var div = document.createElement("div");
	        div.className = "tile";
	        div.id = i.id;
	        var link = document.createElement("a");
	        var img = document.createElement("img");
	        img.title = i.name;
	        img.src = i.thumbnailURL;
	        img.width= THUMB_WIDTH - 1 ;
	        img.height= THUMB_HEIGHT -1;
	        img.className = "tileImage";
	        link.appendChild(img);
	        link.setAttribute("id", i.id);
	
	        if (typeof div.attachEvent != 'undefined') {
	            div.attachEvent('onclick',function(e){this.id = div.id; _this.showImage(this.id, false);});
	        } else {
	            link.addEventListener('click',function(e){this.id = div.id; _this.showImage(this.id, false);}, true);
	        }
	        div.appendChild(link);
	        containerDiv.appendChild(div);
	
	        tiles.push(div);
	    }
	
    
	    function getPosition(_e) {
	        var pX = 0;
	        var pY = 0;
	        while (_e.offsetParent) {
	                pY += _e.offsetTop;
	                pX += _e.offsetLeft;
	                _e = _e.offsetParent;
	        }
	        return {x: pX, y: pY};
	    }
	
	  }	
}
jmaki.widgets.extras.jmaki.ibrowser.Widget = function(wargs) {

        var _widget = this;
        var subscribe = ["/extras/jmaki/ibrowse"];


	function Controller() {
	
	  // this object structure contains a list of the groups and chunks that have been loaded
	
	  var pList = new GroupList();
	
	  var CHUNK_SIZE=5;
	  var originalURL;
	  var useDOMInjection;
	
	  this.handleEvent = function(args) {
	
	      if (args.type == "showingItem") {
	        var groupId = args.gid;
	      	window.location.href= originalURL +  "#" + groupId + "," + args.id;
	        showItemDetails(args.id, args.uuid, args.fontSize);
	
	      } else if (args.type == "getChunck") {
	
	          //get chunck with args;
	      }
	  }

	  function showItemDetails(id, uuid, fontSize) {
	
	      var infoName = document.getElementById(uuid + "_infopaneName");
	      infoName.style.fontSize = fontSize + "px";
	      var infoDescription =  document.getElementById(uuid + "_infopaneDescription");
	      infoDescription.style.fontSize = (fontSize-1) + "px";
	      var infoShortDescription =  document.getElementById(uuid + "_infopaneShortDescription");  
	      infoShortDescription.style.fontSize = (fontSize-1) + "px";
	      var i = pList.getItem(id);
	      setNodeText(infoName, genHTML(i.name));
	
	      var sd ="";
	      var description = "";
	      if (i.description) description = genHTML(i.description)
	      if (description.length > 70) {
	        description.substring(0,71) + "...";
	      } else {
	          sd = description;
	      }
	
	      setNodeText(infoShortDescription,description);
	      //setNodeText(infoDescription, description);
	  } 
	
	    function setNodeText(t, text) {
	
	      if (typeof t == 'undefined') return;
	
	      if (typeof useDOMInjection == 'undefined') {
	          testInnerHTML(t);
	      }
	
	      if (useDOMInjection) {
	          if (t.lastChild == null) {
	              var child = document.createTextNode(text);
	              t.appendChild(child);
	          } else {
	            t.lastChild.nodeValue = text;
	          }
	      } else {
	          t.innerHTML = text;
	      }
	  }
	
	  function testInnerHTML(infoName) {
	        var testString = "   ";
	        infoName.innerHTML = testString;
	        if (!useDOMInjection && infoName.innerHTML != testString) {
	            useDOMInjection = true;
	        } else {
	            useDOMInjection = false;
	        }
	  }
	
	  /**
	  * Un-Escape HTML
	  */
	
	  function genHTML(orig) {
	
	      var decode = orig.replace(/&gt;/g, ">");
	      decode = decode.replace(/&lt;/g, "<");
	      decode = decode.replace(/&quot;/g, "\"");
	      decode = decode.replace(/&amp;/g, "&");
	      return decode;
	  }
	
	  this.initialize = function() {
	      processURLParameters();
	  }
	
	  this.loadJSON = function(service) {     
              // for actions
              if (service && service.message) service = service.message;
              if (service instanceof Array ||
                  (service && service.value && service.value instanceof Array)) {
                  if (service.value) group = service.value;
                  else group = service;
                  _widget.scroller.addItems(group);
                 _widget.scroller.setGroupId('default');
                 pList.addChunck('default', 0, group);
                 _widget.scroller.showImage(group[0].id);                  
              } else if (typeof service == 'string' ||
                         typeof service.value == 'string') {
                         var _url;
                         if (service.value) _url = service.value;
                         else _url = service;
                  jmaki.doAjax({url: _url, callback: function(req) {
                          var group = eval('(' + req.responseText + ')');
                          _widget.scroller.addItems(group.items);
                          _widget.scroller.setGroupId(group.id);
                          pList.addChunck(group.id, 0, group.items);
                          _widget.scroller.showImage(group.items[0].id);
                  }});
              }
	
	  }
	
	  // this needs to happen after we have loaded the intial data
	
	  function processURLParameters() {
	      originalURL = decodeURIComponent(window.location.href);
	      var params = {};
	
	      // look for the params
	      if (originalURL.indexOf("#") != -1) {
	          var qString = originalURL.split('#')[1];
	          var args = qString.split(',');
	          originalURL = originalURL.split('#')[0];
	          // load group args[0] with item args[1];
	          return;
	      }
	
	  }
	
	  /**
	   *  Insert a script tag in the head of the document which will inter load the flicker photos
	   *  and call jsonFlickrFeed(obj) with the corresponding object.
	   *
	   */
	
	  this.loadFlickr = function(tags, scroller, apikey) {
		  var  fl;
		  if (typeof apikey != 'undefined') fl = new FlickrLoader(apikey);
		  else fl = new jmaki.FlickrLoader();
		  fl.load(tags,function(obj,t) {
			  postProcessFlicker(obj,scroller);
		  });
		  
		 function postProcessFlicker(flickrPhotos, scroller) {
	      var fi = [];
	      for (var l=0; l < flickrPhotos.items.length; l++) {
	          var itemId = "flickr_" + l;
	          var description = flickrPhotos.items[l].description;            
	          var start = description.indexOf("src=") + 10;
	          var stop =  description.indexOf("_m.jpg");
	          var imageBase = description.substring(start,stop);
	          var thumbURL = imageBase + "_m.jpg";
	          var imageURL = imageBase + ".jpg";
	          description = "Author: " + flickrPhotos.items[l].author + " tags:" + flickrPhotos.items[l].tags;
	          var price = 0;
	          var name = flickrPhotos.items[l].title;
	          var i = {id:itemId , name: name, thumbnailURL: thumbURL, imageURL: imageURL, description: description};
	          fi.push(i);
	      }
	
	      scroller.addItems(fi);
	      var tags = flickrPhotos.items[0].tags
	      scroller.setGroupId(tags);
	      pList.addChunck(tags, 0, fi);
	      scroller.showImage(fi[0].id);
	  }
	}
	
        function GroupList() {
	
	      var _plist = this;
	      var map = new jmaki.Map();
	      var gmap = new jmaki.Map();
	      
	      this.addChunck = function(pid, chunkNumber, items) {
	          for (var il =0; il < items.length; il++) {
	              gmap.put(items[il].id, items[il]);
	          }
	          map.put(pid + "_" + chunkNumber, items, true);  
	
	      }
	
	      this.getChunck = function(pid, chunkNumber) {
	          return map.get(pid + "_" + chunkNumber);  
	      }
	
	      this.hasChunck = function(pid, chunkNumber) {
	          return (map.get(pid + "_" + chunkNumber) != null);  
	
	      }
	
	      this.getItem = function(id) {
	          return gmap.get(id);
	      }
	
	  }
	
	}
        
    var controller = new Controller();
	controller.initialize();
	var scaleIcons = false;
	if (typeof wargs.args != 'undefined') {
		if (typeof wargs.args.scaleIcons != 'undefined') {
	  	 scaleIcons = wargs.args.scaleIcons;
		}
	}
	_widget.scroller = new jmaki.widgets.extras.jmaki.ibrowser.ImageScroller(wargs.widgetDir,
                       wargs.uuid, wargs.args.width, wargs.args.height,scaleIcons);
	_widget.scroller.callbackHandler = controller.handleEvent;
        
                if (wargs.subscribe){
            if (typeof wargs.subscribe == "string") {
                subscribe = [];
                subscribe.push(wargs.subscribe);
            } else {
                subscribe = wargs.subscribe;
            }
        }

        function doSubscribe(topic, handler) {
            var i = jmaki.subscribe(topic, handler);
            _widget.subs.push(i);
        }

        this.subs = [];
        for (var _i=0; _i < subscribe.length; _i++) {     
            doSubscribe(subscribe[_i]  + "/clear", _widget.scroller.reset);
            doSubscribe(subscribe[_i]  + "/setItems", controller.loadJSON);
        }    

        this.destroy = function() {
            _widget.scroller.destroy();
        }
      
	var apikey;
	var tags='thekt';
	if (typeof wargs.args.sourceType != 'undefined') {
		if (typeof wargs.args.apikey != 'undefined') {
		   apkikey = wargs.args.apikey;
		}
		if (typeof wargs.args.tags != 'undefined') {
		   tags = wargs.args.tags;
		}
		if (typeof wargs.args.tag != 'undefined') {
		   tags = wargs.args.tag;
		}
		tags = tags.toLowerCase();
	    if (wargs.args.sourceType == 'flickr') {
	        controller.loadFlickr(tags, _widget.scroller, apikey);
	    } else if (wargs.args.sourceType == 'json'){
	        controller.loadJSON([wargs.args.url], _widget.scroller);
	    }
	}
}
