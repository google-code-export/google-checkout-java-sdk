Google Storefront Example
-------------------------

The following steps will demonstrate how to build the Google Storefront
sample store.

-+
 |
 +-src
 |  |
 |  +-com/google/checkout/samples/samplestore
 |                      |
 |                      +-client
 |                      |
 |                      +-public
 |                      |
 |                      +-server
 |
 +-www
    |
    +-com.google.checkout.samples.samplestore.GridStore
                        |
                        +-WEB-INF
                             | 
                             +-lib
 
 A. Setup
 These examples require:
   i. a recent version of GWT (1.4.61 or later). Find GWT at <insert site for GWT here>
   ii. a recent version of ant (1.6 or later). Find ant at: http://ant.apache.org
 
 There are a number of properties which you'll need to set to customize your 
 build environment. These properties need to be set in a file called build.properties
 in your home directory. There's a sample build.properties.sample file in the base
 directory of this source tree.
 
 You will need to set the gwt.home and gwt.platform.jar properties. gwt.hom needs
 to point at the directory where you have installed the GWT and gwt.platform.jar
 should refer to the name of the jar file (not including path) which contains the 
 GWT compiler (on Mac this is gwt-dev-mac.jar). The jar file is located in the
 top directory of the GWT Software Developer Kit.
 
 You will also need to set the store.name to the type of store you would like to build.
 Currently, only one store exists: this store is the GridStore. In the future, we 
 would like to create different types of stores. The next planned store will be
 the IBrowseStore. For more information on the IBrowseStore, please see the TODO.txt
 located at this project's root dir.

B. Building the sample online store as a war.

1. Copying the gwt-servlet.jar to WEB-INF/lib.

Run the following target:
ant setup

This step copies the gwt-servlet.jar to your www/com.google.checkout.samples.samplestore.${store.name}

2.Change the color scheme of the sample store.
 
Navigate to the "src/com/google/checkout/samples/samplestore" dir.
Open up the ${store.name}.gwt.xml corresponding to the store you want to build in
your favorite text editor. Edit the line with the stylesheet tag:
  e.g. <stylesheet src="style/GridStore-default.css"/>
  
You can change the 'src' attribute to point to any of the styles found under
"src/com/google/checkout/samples/samplestore/public/style". Simply have the 'src'
attribute point to 'style/*.css'.

3. Compiling the store.

Run the following command from the project's root directory:
sh GridStore-compile

4. Building the web application (.war file)

Run the following command from the project's root directory:
ant buildWar

5. Deploying the web application to your web server.

Run the following command from the project's root directory:
ant deploy