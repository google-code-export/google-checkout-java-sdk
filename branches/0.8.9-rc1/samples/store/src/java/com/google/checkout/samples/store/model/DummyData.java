/*******************************************************************************
 * Copyright (C) 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package com.google.checkout.samples.store.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Some hard-coded data for testing purposes
 * @author inder@google.com (Inderjeet Singh)
 */
public class DummyData {
  public static final Product CAT0 = new Product("cat0", "Rainbow Lorikeet", 
          "images/princess-thumb.jpg", "images/princess-med.jpg", "A wonderful cat");
  
  public static final Product TURTLE0 = new Product("turtle0", "California Desert Tortoise", 
          "images/california-desert-tortoise-thumb.jpg", 
          "images/california-desert-tortoise-med.jpg", "A turtle description.");
  public static final Product TURTLE1 = new Product("turtle1", "Box Turtle 1", 
          "images/box-turtle-thumb.jpg", "images/box-turtle-med.jpg", "A turtle description.");
  public static final Product TURTLE2 = new Product("turtle2", "Box Turtle 2", 
          "images/box-turtle2-thumb.jpg", "images/box-turtle2-med.jpg", "A turtle description");
  public static final Product TURTLE3 = new Product("turtle3", "Box Turtle 3", 
          "images/box-turtle3-thumb.jpg", "images/box-turtle3-med.jpg", "A turtle description.");

  public static final Product BIRD0_0 = new Product("bird0", "Lovebird", 
           "images/lovebird-thumb.jpg", "images/lovebird-med.jpg", "A princess description.");
  public static final Product BIRD0_1 = new Product("bird1", "Peacock", "images/peacock-thumb.jpg", 
          "images/peacock-med.jpg", "A princess description.");
  public static final Product BIRD0_2 = new Product("bird2", "Dragon Iron", 
          "images/dragon-iron-thumb.jpg", "images/dragon-iron-med.jpg", "A princess description.");
  public static final Product BIRD0_3 = new Product("bird3", "Galah Parrot", 
          "images/galah-parrot-thumb.jpg", "images/galah-parrot-med.jpg", "A princess description.");
  public static final Product BIRD0_4 = new Product("bird4", "Peakcock White", 
          "images/peacock-white-thumb.jpg", "images/peakcock-white-med.jpg", 
          "A princess description.");          
  public static final Product BIRD0_5 = new Product("bird5", "Eagle Stone", 
          "images/eagle-stone-thumb.jpg", "images/eagle-stone-med.jpg", "A princess description.");
  public static final Product BIRD0_6 = new Product("bird6", "Peacock Blue", 
          "images/peacock-blue-thumb.jpg", "images/peacock-blue-med.jpg", 
          "A princess description.");
  public static final Product BIRD0_7 = new Product("bird7", "Eclectus Female", 
          "images/eclectus-female-thumb.jpg", "images/eclectus-female-med.jpg", 
          "A princess description.");
  
  public static final Product BIRD1_0 = new Product("bird8", "Galah Parrot", 
          "images/galah-parrot-thumb.jpg", "images/galah-parrot-med.jpg", "A princess description.");
  public static final Product BIRD1_1 = new Product("bird9", "Peakcock White", 
          "images/peacock-white-thumb.jpg", "images/peakcock-white-med.jpg", 
          "A princess description.");
  public static final Product BIRD1_2 = new Product("bird10", "Eagle Stone", 
          "images/eagle-stone-thumb.jpg", "images/eagle-stone-med.jpg", "A princess description.");
  public static final Product BIRD1_3 = new Product("bird11", "Peacock Blue", 
          "images/peacock-blue-thumb.jpg", "images/peacock-blue-med.jpg", "A princess description.");
  public static final Product BIRD1_4 = new Product("bird12", "Eclectus Female", 
          "images/eclectus-female-thumb.jpg", "images/eclectus-female-med.jpg", 
          "A princess description.");
  public static final Product BIRD1_5 = new Product("bird13", "Lovebird", 
          "images/lovebird-thumb.jpg", "images/lovebird-med.jpg", "A princess description.");
  public static final Product BIRD1_6 = new Product("bird14", "Peacock", "images/peacock-thumb.jpg", 
          "images/peacock-med.jpg", "A princess description.");
  public static final Product BIRD1_7 = new Product("bird15", "Dragon Iron", 
          "images/dragon-iron-thumb.jpg", "images/dragon-iron-med.jpg", "A princess description.");  
  
  public static final Category CATS_CATEGORY = Category.getCategory("Cats");
  public static final Category TURTLES_CATEGORY = Category.getCategory("Turtles");
  public static final Category REPTILES_CATEGORY = Category.getCategory("Reptiles", TURTLES_CATEGORY);
  public static final Category BIRDS0_CATEGORY = Category.getCategory("Birds 0");
  public static final Category BIRDS1_CATEGORY = Category.getCategory("Birds 1");
  public static final Category BIRDS_CATEGORY = 
          Category.getCategory("Birds", BIRDS0_CATEGORY, BIRDS1_CATEGORY);
  
  public static final ProductList CATS = new ProductList(CATS_CATEGORY, CAT0);  
  public static final ProductList TURTLES = 
          new ProductList(TURTLES_CATEGORY, TURTLE0, TURTLE1, TURTLE2, TURTLE3);
  public static final ProductList BIRDS0 =  new ProductList(BIRDS0_CATEGORY, BIRD0_0, BIRD0_1, 
          BIRD0_2, BIRD0_3, BIRD0_4, BIRD0_5, BIRD0_6, BIRD0_7);
  public static final ProductList BIRDS1 =  new ProductList(BIRDS1_CATEGORY, BIRD1_0, BIRD1_1, 
          BIRD1_2, BIRD1_3, BIRD1_4, BIRD1_5, BIRD1_6, BIRD1_7);
  
  public static Collection<Category> getAllCategories() {
    Collection<Category> categories = new ArrayList<Category>();
    categories.add(CATS_CATEGORY);
    
    categories.add(REPTILES_CATEGORY);
    
    categories.add(BIRDS_CATEGORY);
    return categories;
  }
  
  public static HashMap<Category, ProductList> getAllProductLists() {
     HashMap<Category, ProductList> map = new HashMap<Category, ProductList>();
     map.put(CATS.getCategory(), CATS);
     map.put(TURTLES.getCategory(), TURTLES);
     map.put(BIRDS0.getCategory(), BIRDS0);
     map.put(BIRDS1.getCategory(), BIRDS1);
     return map;
  }  
}
