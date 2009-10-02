/*
 * Here is the controller for processing the requests.
 *
 *
 */

                                   
// create the namespace
jmaki.namespace("jmaki.data.catalog");

// create a catalog object for the products so we do not reload them
jmaki.data.catalog = {};


jmaki.subscribe("/controller*", function(args) {
    // get the id of the product we want to view
    var productId = args.message.value;
    jmaki.log("Controller : setting items to " + productId);
    if (!jmaki.data.catalog[productId]) {
      jmaki.doAjax( {
            //url : 'data-' + productId + '.json',
            url : 'control/catalog-json.action?category='+productId,
            callback : function(req) {
                // clear the items in the browser
                jmaki.publish('/ibrowse/clear', {});                
                // convert the response into a JSON object
                var _products = eval("(" + req.responseText + ")");
                jmaki.data.catalog[productId] = _products;
                jmaki.publish('/ibrowse/setItems', { value : _products.items});
            }
          });
    } else {
        // clear the items in the browser
        jmaki.publish('/ibrowse/clear', {});
        // load from the cached data
        var _products = jmaki.data.catalog[productId];
        jmaki.publish('/ibrowse/setItems', { value : _products.items});
    }
    
});

/*
 * This code get called after jMaki is initialized to populate the list
 * 
 */

jmaki.subscribe("/jmaki/runtime/loadComplete", function(args) {
    // start with turtles
    var productId = 'Cats';
    jmaki.log("Initialize : setting items to " + productId);
    jmaki.doAjax( {
            url : 'control/catalog-json.action?category='+productId,
            callback : function(req) {
                // clear the items in the browser
                jmaki.publish('/ibrowse/clear', {});                
                // convert the response into a JSON object
                var _products = eval("(" + req.responseText + ")");
                jmaki.data.catalog[productId] = _products;
                jmaki.publish('/ibrowse/setItems', { value : _products.items});
            }
          });  
    
});