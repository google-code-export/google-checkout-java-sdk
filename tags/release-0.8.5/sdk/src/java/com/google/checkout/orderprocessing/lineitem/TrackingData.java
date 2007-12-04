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

package com.google.checkout.orderprocessing.lineitem;

/**
 * This class encapsulates tracking data information used as part of the line
 * item shipping API.
 * 
 * @author simonjsmith@google.com
 */
public class TrackingData {

  private final String carrier;

  private final String trackingNumber;

  /**
   * Constructor which takes the parameter carrier and trackingNumber.
   * 
   * @param carrier The parameter carrier.
   * @param trackingNumber The parameter trackingNumber.
   */
  public TrackingData(String carrier, String trackingNumber) {
    this.carrier = carrier;
    this.trackingNumber = trackingNumber;
  }

  /**
   * Get the carrier.
   * 
   * @return The carrier.
   */
  public String getCarrier() {
    return carrier;
  }

  /**
   * Get the tracking number.
   * 
   * @return The tracking number.
   */
  public String getTrackingNumber() {
    return trackingNumber;
  }
}
