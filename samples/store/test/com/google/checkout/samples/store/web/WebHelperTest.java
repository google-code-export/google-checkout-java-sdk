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

package com.google.checkout.samples.store.web;

import com.google.checkout.samples.store.model.Category;
import com.google.checkout.samples.store.model.DummyData;
import com.google.checkout.samples.store.model.ModelFacade;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for {@link WebHelper}
 * @author inder@google.com (Inderjeet Singh)
 */
public class WebHelperTest {

  private WebHelper webHelper;
  @Before
    public void setUp() {
    webHelper = new WebHelper(new ModelFacade());
  }

  @Test
  public void appendMenuJsonForCategory() {
    String expected = "{label : 'Reptiles', menu : [" 
        + "{label : 'Turtles', action : { topic: '/controller', message: { value : 'turtles'}}}"
        + "]}";
    StringBuilder sb = new StringBuilder();
    String result = webHelper.appendMenuJsonForCategory(DummyData.REPTILES_CATEGORY, sb).toString();
    assertEquals(expected, result);
  }
  
  @Test
  public void appendMenuJsonForCategories() {
    String expected = "menu : [" 
            + "{label : 'Reptiles', menu : ["  
            + "{label : 'Turtles', action : { topic: '/controller', message: { value : 'turtles'}}}" 
            + "]}, " 
            + "{label : 'Birds', selected : true, menu : [" 
            + "{label : 'Birds 0', action : { topic: '/controller', message: { value : 'birds0'}}}, " 
            + "{label : 'Birds 1', action : { topic: '/controller', message: { value : 'birds1'}}" 
            + "}]}]";
    Collection<Category> categories = 
        Arrays.asList(DummyData.REPTILES_CATEGORY, DummyData.BIRDS_CATEGORY);
    StringBuilder sb = new StringBuilder();
    String result = webHelper.appendMenuJsonForCategories(categories, sb).toString();
    assertEquals(expected, result);
  }
  
  /**
   * Test of getJsonForAccordianMenu method, of class WebHelper.
   */
  @Test
  public void getJsonForAccordianMenu() {
    String expected = "{menu : [" 
            + "{label : 'Cats'}, "
            + "{label : 'Reptiles', menu : ["  
            + "{label : 'Turtles', action : { topic: '/controller', message: { value : 'turtles'}}}" 
            + "]}, " 
            + "{label : 'Birds', selected : true, menu : [" 
            + "{label : 'Birds 0', action : { topic: '/controller', message: { value : 'birds0'}}}, " 
            + "{label : 'Birds 1', action : { topic: '/controller', message: { value : 'birds1'}}" 
            + "}]}]}";
    Collection<Category> categories = Arrays.asList(DummyData.CATS_CATEGORY, 
            DummyData.REPTILES_CATEGORY, DummyData.BIRDS_CATEGORY);
    String result = webHelper.getJsonForAccordianMenu(categories);
    assertEquals(expected, result);
  }
}