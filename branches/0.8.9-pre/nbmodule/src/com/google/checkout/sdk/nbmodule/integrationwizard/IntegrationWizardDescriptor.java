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

package com.google.checkout.sdk.nbmodule.integrationwizard;

import org.openide.WizardDescriptor;

/**
 * A subclass of WizardDescriptor with the added functionality to store
 * an Integration Wizard settings object.
 *
 * @author David Rubel
 */
public class IntegrationWizardDescriptor extends WizardDescriptor {

  private NetbeansSettings settings;

  public IntegrationWizardDescriptor(WizardDescriptor.Panel[] panels) {
    super(panels);
    settings = new NetbeansSettings();
  }

  public NetbeansSettings getSettings() {
    return settings;
  }

  public void setSettings(NetbeansSettings settings) {
    this.settings = settings;
  }

  @Override
  public void updateState() {
    super.updateState();
  }
}
