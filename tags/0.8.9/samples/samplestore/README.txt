Google Storefront Example
-------------------------

Please read the Storefront Wiki Page (http://code.google.com/p/google-checkout-java-sdk/wiki/Storefront) 
before continuing.

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
   i. a recent version of GWT (1.4.61 or later). Find GWT at http://code.google.com/webtoolkit/download.html
   ii. a recent version of ant (1.6 or later). Find ant at: http://ant.apache.org
 
 There are a number of properties which you'll need to set to customize your 
 build environment. These properties need to be set in a file called build.properties
 in your home directory or in the project's root directory. There build.properties 
 file in the base directory of this source tree, you are expected to modify it.
 
 You will need to set the gwt.home and gwt.platform.jar properties. gwt.home needs
 to point at the directory where you have installed the GWT and gwt.platform.jar
 should refer to the name of the jar file (not including path) which contains the 
 GWT compiler (on Mac this is gwt-dev-mac.jar). The jar file is located in the
 top directory of the GWT Software Developer Kit.
 
 You will also need to set the store.name to the type of store you would like to build.
 Currently, only one store exists: this store is the GridStore.

B. Building the sample online store as a war.

1. Edit merchant specific information.

Navigate to the "src/com/google/checkout/samples/samplestore/server". Edit the GridStore.xml with your
favorite text editor. Enter your customer specific information for your sample store.

2. Run ant from the root directory.

You should now be able to see your Storefront on your local server.
