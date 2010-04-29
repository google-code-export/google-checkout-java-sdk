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

import com.google.checkout.sdk.domain.ItemShippingInformation;
import com.google.checkout.sdk.domain.TrackingData;

import junit.framework.TestCase;

import java.util.List;

/**
*
 */
public class ItemShippingInformationBuilderTest extends TestCase {

  public void testBuilderOnce() {
    ItemShippingInformationBuilder builder = new ItemShippingInformationBuilder();
    builder.addShipping("merchantItemId", new TrackingDataBuilder().addTrackingData("c", "tn"));
    List<ItemShippingInformation> build = builder.build();
    assertEquals(1, build.size());
    assertEquals("merchantItemId", build.get(0).getItemId().getMerchantItemId());
    List<TrackingData> trackingData = build.get(0).getTrackingDataList().getTrackingData();
    assertEquals(1, trackingData.size());
    assertEquals("c", trackingData.get(0).getCarrier());    
    assertEquals("tn", trackingData.get(0).getTrackingNumber());
  }
  
  public void testBuilderManyItems() {
    ItemShippingInformationBuilder builder = new ItemShippingInformationBuilder();
    builder.addShipping("mid1", new TrackingDataBuilder().addTrackingData("c1", "tn1"));
    builder.addShipping("mid2", new TrackingDataBuilder()
        .addTrackingData("c2a", "tn2a")
        .addTrackingData("c2b", "tn2b"));
    builder.addShipping("mid1", new TrackingDataBuilder()
        .addTrackingData("c3a", "tn3a")
        .addTrackingData("c3b", "tn3b"));
    List<ItemShippingInformation> build = builder.build();
    ItemShippingInformation elem = null;
    assertEquals(3, build.size());
    
    elem = build.get(0);
    assertEquals("mid1", elem.getItemId().getMerchantItemId());
    assertEquals(1, elem.getTrackingDataList().getTrackingData().size());
    assertEquals("c1", getTrackingData(elem, 0).getCarrier());
    assertEquals("tn1", getTrackingData(elem, 0).getTrackingNumber());
    
    elem = build.get(1);
    assertEquals("mid2", elem.getItemId().getMerchantItemId());
    assertEquals(2, elem.getTrackingDataList().getTrackingData().size());
    assertEquals("c2a", getTrackingData(elem, 0).getCarrier());
    assertEquals("tn2a", getTrackingData(elem, 0).getTrackingNumber());
    assertEquals("c2b", getTrackingData(elem, 1).getCarrier());
    assertEquals("tn2b", getTrackingData(elem, 1).getTrackingNumber());
    
    elem = build.get(2);
    assertEquals("mid1", elem.getItemId().getMerchantItemId());
    assertEquals(2, elem.getTrackingDataList().getTrackingData().size());
    assertEquals("c3a", getTrackingData(elem, 0).getCarrier());
    assertEquals("tn3a", getTrackingData(elem, 0).getTrackingNumber());
    assertEquals("c3b", getTrackingData(elem, 1).getCarrier());
    assertEquals("tn3b", getTrackingData(elem, 1).getTrackingNumber());
  }

  private TrackingData getTrackingData(ItemShippingInformation elem, int tdIndex) {
    return elem.getTrackingDataList().getTrackingData().get(tdIndex);
  }  
}
