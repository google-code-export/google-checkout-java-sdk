/* Copyright 2007 You may not modify, use, reproduce, or distribute this software except in compliance with the terms of the License at:
 http://developer.sun.com/berkeley_license.html
 $Id: component.js,v 1.0 2007/04/15 19:39:59 gmurray71 Exp $
*/
// define the namespaces
jmaki.namespace("jmaki.widgets.jmaki.accordionMenu");

jmaki.widgets.jmaki.accordionMenu.Widget = function(wargs) {

    var _widget = this;
    var publish = "/jmaki/accordionMenu";
    var subscribe =  ["/jmaki/accordionMenu", "/accordion"];
       
    var EXPANDED_HEIGHT = 125;
    var ITEM_HEIGHT = 0;
    var INCREMENT = 12;
    var SMALL_INCREMENT = 1;    
    
    var timeout = 5; // in ms
    
    var accordion = document.getElementById(wargs.uuid);
    var startHeight = accordion.clientHeight;
    
    var  panes = [];
    var oExpandedIndex = -1;
    var nExpandedIndex = -1;
    var oHeight = ITEM_HEIGHT;
    var nHeight = ITEM_HEIGHT;
    var tHeight = 0;
    var expanding = false;
    var items;
    
    var debug = false;
    
    if (wargs.value) {
        items = wargs.value.menu;
    }
    if (wargs.publish) {
        publish = wargs.publish;
    }
    
    function createLinks(tDiv, text, id, linkStyle) {
        var link = document.createElement("a");
        link.className = linkStyle;  
        link.appendChild(document.createTextNode(text));
        link.setAttribute("id", id);
        if (link.attachEvent) {
            link.attachEvent('onmouseover',function(e){initiateExpansion(e.srcElement.getAttribute("id"));});
        } else if (link.addEventListener) {
            link.addEventListener('mouseover',function(e){initiateExpansion(e.currentTarget.getAttribute("id"));}, true);
        }
        tDiv.appendChild(link);
    }    
       
    function Pane(l, item) {
       this.id = l;
       var pane = document.createElement("div");
       pane.className = "accordionPane";       
       this.labelDiv = document.createElement("div");
       this.labelDiv.className = "accordionPaneRow jmakiTitle";
       this.label = document.createElement("div");
       this.label.className = "accordionPaneLabel";     
       createLinks(this.label, item.label, l, "accordionLink");
       this.labelDiv.appendChild(this.label)
       pane.appendChild(this.labelDiv);       
       // this is the part the will expand and contract
       this.content = document.createElement("div");
       this.content.className = "accordionPaneContent";
       pane.appendChild(this.content);
       this.height = 0;
       addMenuItems(item, this.content);       

       this.content.style.height = 0 + "px"
       accordion.appendChild(pane);        
       
    }
    
    Pane.prototype.setHeight = function(nH) {
        this.h = nH;
        if (nH <= 0) nH =0;
        if (nH >= 0) this.content.style.height = nH + "px";
        else this.content.style.height = "0px";
    }
    
    Pane.prototype.getTotalHeight = function() {
        return this.content.offsetHeight;
    }
    
    Pane.prototype.getHeight = function() {
            return this.h;
    }
    
    this.handleEvent = function(args) {
        if (args.type) {
            if (args.type == 'expand') {
                var targetPane = args.targetPane;
                initiateExpansion(targetPane);
            }
        }
    }
  
    function addMenuItems(item, content) {
        for (var l= 0; item.menu && l < item.menu.length; l++) {
            var span = document.createElement("div");
            span.className = "accordionMenuItem jmakiMenuItem";

            var link = document.createElement("a");
            var target = item.menu[l].id;
            link.id = target;
            link.className = "accordionLink";
            link.item =  item.menu[l];

            link.onclick = function(e) {
                if (!e) var e = window.event
                var t;
                if (e.target) t= e.target;
                else if (e.srcElement) t = e.srcElement;            
                processActions(t.item, t.item, 'onSelect');
                // now process hyperlinks
                if (t.item.href) {
                    // if it's just a href nativate to it
                    if (t.item.href && !t.item.target) {
                        window.location.href = t.item.href;
                    } else if (t.item.target) {
                        t.target = t.item.target;                    
                        t.href= t.item.href;
                    }
                }
            }

            link.appendChild(document.createTextNode(item.menu[l].label));

            span.appendChild(link);

            content.appendChild(span);

            if (item.menu && l < item.menu.length - 1) {
                content.appendChild(document.createElement("p"));
            }
            link = null;
        }
        content.style.display= "none";
    }
           
    this.load = function() {
        var selected = 0;
        // create all the rows
        for (var l=0; l < items.length; l++) {
            var pane = new Pane(l,items[l]);
            panes.push(pane);
            if (items[l].selected) selected = l;
        }
        SMALL_INCREMENT = Math.round(INCREMENT / (panes.length - 1));
        var paneHeight = 18;
     
        if (panes.length > 0)paneHeight = panes[0].labelDiv.clientHeight;
        EXPANDED_HEIGHT = (startHeight  - (paneHeight * panes.length));

        initiateExpansion(selected);
    }

    function clone(t) {
       var obj = {};
       for (var i in t) {
            obj[i] = t[i];
       }
       return obj;
    }
    
    function processActions(_t, _pid, _type, _value) {
        if (_t) {
            var _topic = publish;
            var _m = {widgetId : wargs.uuid, type : _type, targetId : _pid};
            if (typeof _value != "undefined") _m.value = _value;
            var action = _t.action;
            if (!action) _topic = _topic + "/" + _type;
            if (action && action instanceof Array) {
              for (var _a=0; _a < action.length; _a++) {
                  var payload = clone(_m);
                  if (action[_a].topic) payload.topic = action[_a].topic;
                  else payload.topic = publish;
                  if (action[_a].message) payload.message = action[_a].message;
                  jmaki.publish(payload.topic,payload);
              }
            } else {
              if (action && action.topic) {
                  _topic = _m.topic = action.topic;
              }
              if (action && action.message) _m.message = action.message;               
              jmaki.publish(_topic,_m);
            }
        }
    } 
        
    function initiateExpansion(id) {        
        // jump out if we are in progress
        if (!expanding && oExpandedIndex != Number(id)) {
            expanding = true;
            nExpandedIndex = Number(id);
            panes[nExpandedIndex].content.style.display= "block";
            expandPane(id);
        }
    }
    
    function expandPane() {
        if (expanding) {
         
         if (nHeight < EXPANDED_HEIGHT) {
             var i = INCREMENT;
             if (nHeight + INCREMENT > EXPANDED_HEIGHT)
                 i = EXPANDED_HEIGHT - nHeight;
                nHeight += i;        
                panes[nExpandedIndex].setHeight(nHeight);

                if (oExpandedIndex != -1) {
                     oHeight = panes[oExpandedIndex].getHeight();
                     oHeight = oHeight - i;
                     panes[oExpandedIndex].setHeight(oHeight);                  
                }
            } else {
                for (var i=0; i < panes.length;i++) {
                    if (i != nExpandedIndex) {
                        panes[i].setHeight(0);
                        panes[i].content.style.display= "none";
                    }
                }
                expanding = false;
                oExpandedIndex = nExpandedIndex;
                nExpandedIndex = -1;
                oHeight = nHeight;
                nHeight = ITEM_HEIGHT;
                return;
            }
            setTimeout(expandPane, timeout);
        }
    }
 
    this.load();
}