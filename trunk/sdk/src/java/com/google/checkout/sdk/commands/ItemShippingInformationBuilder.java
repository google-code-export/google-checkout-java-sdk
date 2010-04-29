/*******************************************************************************
 * Copyright (C) 2010 Google Inc.
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

import com.google.checkout.sdk.domain.ItemId;
import com.google.checkout.sdk.domain.ItemShippingInformation;
import com.google.checkout.sdk.domain.TrackingData;
import com.google.checkout.sdk.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder object for List{ItemShippingInformation}. This allows you to inform
 * Google Checkout of item-level shipping orders, allowing your buyers to see
 * shipping information on a per-item basis.
 * 
 * Shouldn't be reused, isn't threadsafe, etc; its intended use is in
 * {@link OrderCommands#chargeAndShipOrder(ItemShippingInformationBuilder)}
 * where it can be used as:
 * <code>
 * CommandPoster commandPoster;
 * ...
 * commandPoster.postChargeOrder(new ShippingInformationBuilder()
 *     .addShipping("itemId",
 *         new TrackingDataBuilder().addShipping("USPS", "1234")));
 * </code> 
 * 
*
 */
public class ItemShippingInformationBuilder {
  private final List<ItemShippingInformation> shippingDatas = new ArrayList<ItemShippingInformation>();

  public ItemShippingInformationBuilder addShipping(String merchantItemId, TrackingDataBuilder trackingDatas) {
    return addShipping(Utils.makeItemId(merchantItemId), trackingDatas);
  }

  public ItemShippingInformationBuilder addShipping(ItemId id, TrackingDataBuilder trackingDatas) {
    ItemShippingInformation shippingInformation = new ItemShippingInformation();
    shippingInformation.setItemId(id);
    List<TrackingData> build = trackingDatas.build();
    ItemShippingInformation.TrackingDataList trackingDataList =
      new ItemShippingInformation.TrackingDataList();
    trackingDataList.getTrackingData().addAll(build);
    shippingInformation.setTrackingDataList(trackingDataList);
    shippingDatas.add(shippingInformation);
    
    return this;
  }
  
  List<ItemShippingInformation> build() {
    return shippingDatas;
  }
}
