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


import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.conversion.annotations.Conversion;
import com.opensymphony.xwork2.validator.annotations.Validation;


import java.util.logging.Logger;


/**
 * Action that handles the incoming request for catalog JSON
 * @author inder@google.com (Inderjeet Singh)
 */
@Validation()
@Conversion()
public class CatalogJsonAction extends ActionSupport {

  private static final Logger logger = Logger.getLogger(CatalogJsonAction.class.getName());
  
  public String execute() throws Exception {
    return SUCCESS;
  }
}
