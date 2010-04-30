/*******************************************************************************
 * Copyright (C) 2009 Google Inc.
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
package com.google.checkout.sdk.commands;

import com.google.checkout.sdk.domain.TrackingData;

import junit.framework.TestCase;

import java.util.List;

/**
*
 */
public class TrackingDataBuilderTest extends TestCase {
  public void testBuilderOnce() {
    TrackingDataBuilder builder = new TrackingDataBuilder();
    builder.addTrackingData("carrier1", "trackingNumber1");
    List<TrackingData> build = builder.build();
    assertEquals(1, build.size());
    assertEquals("carrier1", build.get(0).getCarrier());
    assertEquals("trackingNumber1", build.get(0).getTrackingNumber());
  }
  
  public void testBuilderTwice() {
    TrackingDataBuilder builder = new TrackingDataBuilder();
    builder.addTrackingData("carrier1", "trackingNumber1");
    builder.addTrackingData("carrier2", "trackingNumber2");
    List<TrackingData> build = builder.build();
    assertEquals(2, build.size());
    assertEquals("carrier1", build.get(0).getCarrier());
    assertEquals("trackingNumber1", build.get(0).getTrackingNumber());
    assertEquals("carrier2", build.get(1).getCarrier());
    assertEquals("trackingNumber2", build.get(1).getTrackingNumber());
  }
  
  public void testBuilderNone() {
    TrackingDataBuilder builder = new TrackingDataBuilder();
    List<TrackingData> build = builder.build();
    assertEquals(0, build.size());
  }
  
  public void testBuilderInternalCarrierNulls() {
    TrackingDataBuilder builder = new TrackingDataBuilder();
    builder.addTrackingData("carrier", null);
    List<TrackingData> build = builder.build();
    assertEquals(1, build.size());
    assertEquals("carrier", build.get(0).getCarrier());
    assertNull(build.get(0).getTrackingNumber());
  }

  public void testBuilderInternalTrackingNulls() {
    TrackingDataBuilder builder = new TrackingDataBuilder();
    builder.addTrackingData(null, "trackingNumber");
    List<TrackingData> build = builder.build();
    assertEquals(1, build.size());
    assertNull(build.get(0).getCarrier());
    assertEquals("trackingNumber", build.get(0).getTrackingNumber());
  }
}
