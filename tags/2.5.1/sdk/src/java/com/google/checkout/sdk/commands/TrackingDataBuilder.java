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

import com.google.checkout.sdk.domain.TrackingData;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder object for List{TrackingData}. This allows you to inform
 * Google Checkout of lists of tracking data, so your users can track their
 * orders. It isn't per-item, for that, see {@link ItemShippingInformationBuilder},
 * but if there's only one item in the cart or you do not use merchant item ids,
 * this is generally good enough.
 *
 * Shouldn't be reused, isn't threadsafe, etc; its intended use is in
 * {@link OrderCommands#chargeAndShipOrder(TrackingDataBuilder)}
 * where it can be used as:
 *
 * <code>
 * CommandPoster commandPoster;
 * ...
 * commandPoster.postChargeOrder(new TrackingDataBuilder()
 *     addShipping("USPS", "1234"));
 * </code>

 *
 */
public class TrackingDataBuilder {

  private final List<TrackingData> trackingDatas = new ArrayList<TrackingData>();

  public TrackingDataBuilder addTrackingData(String carrier, String trackingNumber) {
    TrackingData trackingData = new TrackingData();
    trackingData.setCarrier(carrier);
    trackingData.setTrackingNumber(trackingNumber);
    trackingDatas.add(trackingData);

    return this;
  }

  List<TrackingData> build() {
    return trackingDatas;
  }
}
