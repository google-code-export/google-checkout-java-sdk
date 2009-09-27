package com.google.checkout.sdk.eclipsemodule.integrationwizard;

import com.google.checkout.sdk.module.integrationwizard.Settings;

import org.eclipse.core.resources.IProject;

public class EclipseSettings extends Settings {
  private IProject project;
  
  public EclipseSettings() {
    super();
  }
  
  @Override
  public void reset() {
    super.reset();
    project = null;
  }
  
  public IProject getProject() {
    return project;
  }
  
  public void setProject(IProject project) {
    this.project = project;
  }
}
