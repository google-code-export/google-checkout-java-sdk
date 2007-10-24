<%@ taglib prefix="a" uri="http://jmaki/v1.0/jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
    <head>
        <link rel="stylesheet" href="jmaki-standard-footer.css" type="text/css"></link>
        <title>My Pet Store</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    </head>
    <body>
        <div id="outerBorder">
            
            <div id="header">
                <div id="banner">My Pet Store</div>
                
                <div id="subheader">                    
                    <div>
                        <a href="mailto:feedback@youraddress">Feedback</a> |
                        <a href="#">Site Map</a> |
                        <a href="#">Home</a>
                    </div>
                    
                </div> <!-- sub-header -->
              </div> <!-- header -->
              <jsp:useBean id="webHelper" scope="request" 
                           class="com.google.checkout.samples.store.web.WebHelper" />
            <div id="main">
                <div id="leftSidebar">                    
                    <a:widget name="jmaki.accordionMenu"
                              value="${webHelper.jsonForAccordianMenu}" />                    
                </div> <!-- leftSidebar -->
                <div id="content" style="height:600px">
                    <a:widget name="extras.jmaki.ibrowser"
                              subscribe="/ibrowse"
                              args="{width:600, height:600}"/>                    
                </div> <!-- content -->
            </div> <!-- main -->
            <div id="footer">Footer</div>              
        </div> <!-- outerborder -->
    </body>
</html>
