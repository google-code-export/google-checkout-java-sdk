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

import com.google.checkout.sdk.domain.BackorderItemsRequest;
import com.google.checkout.sdk.domain.CancelItemsRequest;
import com.google.checkout.sdk.domain.ChargeAndShipOrderRequest;
import com.google.checkout.sdk.domain.ItemShippingInformation;
import com.google.checkout.sdk.domain.RequestReceivedResponse;
import com.google.checkout.sdk.domain.ReturnItemsRequest;
import com.google.checkout.sdk.domain.ShipItemsRequest;
import com.google.checkout.sdk.domain.TrackingData;
import com.google.checkout.sdk.testing.AbstractCommandTestCase;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

/**
 * Tests for postpurchase processing Google Checkout orders.
 * 
*
 */
public class CommandPosterImplTest extends AbstractCommandTestCase {
  private OrderCommands commandPoster;
  private List<JAXBElement<?>> commands;
  
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    commands = new ArrayList<JAXBElement<?>>();
    commandPoster = new CommandPosterImplTester(
        apiContext(), "googleOrderNumber", null, commands);
  }
  
  public void testChargeAllShipAll() {
    commandPoster.chargeAndShipOrder();
    assertEquals(1, commands.size());
    ChargeAndShipOrderRequest chargeShipRequest =
      (ChargeAndShipOrderRequest) commands.get(0).getValue(); 
    assertEquals("googleOrderNumber", chargeShipRequest.getGoogleOrderNumber());
    // charge everything
    assertEquals(null, chargeShipRequest.getAmount());
    // ship everything
    assertEquals(null, chargeShipRequest.getTrackingDataList());
  }
  
  public void testChargeSomeShipAll() {
    commandPoster.chargeAndShipOrder(5.00);
    assertEquals(1, commands.size());
    ChargeAndShipOrderRequest chargeShipRequest =
      (ChargeAndShipOrderRequest) commands.get(0).getValue(); 
    assertEquals("googleOrderNumber", chargeShipRequest.getGoogleOrderNumber());
    assertEquals("XXX", chargeShipRequest.getAmount().getCurrency());
    assertEquals(5.00, chargeShipRequest.getAmount().getValue().doubleValue());
    assertEquals(null, chargeShipRequest.getTrackingDataList());
  }
  
  public void testChargeAllShipWithTrackingData() {
    commandPoster.chargeAndShipOrder(5.00,
        new TrackingDataBuilder().addTrackingData("carrier", "trackingNumber"));
    assertEquals(1, commands.size());
    ChargeAndShipOrderRequest chargeShipRequest =
      (ChargeAndShipOrderRequest) commands.get(0).getValue(); 
    
    assertEquals("googleOrderNumber", chargeShipRequest.getGoogleOrderNumber());
    assertEquals(5.00, chargeShipRequest.getAmount().getValue().doubleValue());
    List<TrackingData> trackingDatas = chargeShipRequest.getTrackingDataList().getTrackingData();
    assertEquals("Unexpectedly found " + trackingDatas + " but expected 1 entry",
        1, trackingDatas.size());
    TrackingData trackingData = trackingDatas.get(0);
    assertEquals("carrier", trackingData.getCarrier());
    assertEquals("trackingNumber", trackingData.getTrackingNumber());
  }

  public void testNoEmailChargeShip() {
    OrderCommands tester = new CommandPosterImplTester(
        apiContext(), "googleOrderNumber", false, commands);
    tester.chargeAndShipOrder();
    assertEquals(1, commands.size());
    ChargeAndShipOrderRequest chargeShipRequest =
      (ChargeAndShipOrderRequest) commands.get(0).getValue();
    assertEquals(Boolean.FALSE, chargeShipRequest.isSendEmail());
  }

  public void testNoEmailItemShip() {
    OrderCommands tester = new CommandPosterImplTester(
        apiContext(), "googleOrderNumber", false, commands);
    tester.shipItems(
        new ItemShippingInformationBuilder().addShipping("itemId", new TrackingDataBuilder()
            .addTrackingData("c1", "tn1")
            .addTrackingData("c2", "tn2")));
    assertEquals(1, commands.size());
    ShipItemsRequest shipItemsRequest =
      (ShipItemsRequest) commands.get(0).getValue();
    List<ItemShippingInformation> itemShippingInformation =
      shipItemsRequest.getItemShippingInformationList().getItemShippingInformation();
    assertEquals(Boolean.FALSE, shipItemsRequest.isSendEmail());
    assertEquals("googleOrderNumber", shipItemsRequest.getGoogleOrderNumber());
    assertEquals(1, itemShippingInformation.size());
    assertEquals("itemId", itemShippingInformation.get(0).getItemId().getMerchantItemId());

    assertEquals(2, itemShippingInformation.get(0).getTrackingDataList().getTrackingData().size());
    assertEquals("c1", getTrackingData(itemShippingInformation, 0).getCarrier());
    assertEquals("tn1", getTrackingData(itemShippingInformation, 0).getTrackingNumber());
    assertEquals("c2", getTrackingData(itemShippingInformation, 1).getCarrier());
    assertEquals("tn2", getTrackingData(itemShippingInformation, 1).getTrackingNumber());
  }

  public void testNoEmailBackorderItems() {
    OrderCommands tester = new CommandPosterImplTester(
        apiContext(), "googleOrderNumber", false, commands);
    tester.itemCommands("itemId").backorderItems();
    assertEquals(1, commands.size());
    BackorderItemsRequest backorderItemsRequest =
      (BackorderItemsRequest) commands.get(0).getValue();
    assertEquals(Boolean.FALSE, backorderItemsRequest.isSendEmail());
    assertEquals("googleOrderNumber", backorderItemsRequest.getGoogleOrderNumber());
    assertEquals(1, backorderItemsRequest.getItemIds().getItemId().size());
    assertEquals("itemId", backorderItemsRequest.getItemIds().getItemId().get(0).getMerchantItemId());  
  }

  public void testNoEmailCancelItems() {
    OrderCommands tester = new CommandPosterImplTester(
        apiContext(), "googleOrderNumber", false, commands);
    tester.itemCommands("itemId").cancelItems("because");
    assertEquals(1, commands.size());
    CancelItemsRequest cancelItemsRequest =
      (CancelItemsRequest) commands.get(0).getValue();
    assertEquals(Boolean.FALSE, cancelItemsRequest.isSendEmail());
    assertEquals("googleOrderNumber", cancelItemsRequest.getGoogleOrderNumber());
    assertEquals("because", cancelItemsRequest.getReason());
    assertEquals(1, cancelItemsRequest.getItemIds().getItemId().size());
    assertEquals("itemId", cancelItemsRequest.getItemIds().getItemId().get(0).getMerchantItemId());
  }
  
  public void testNoEmailReturnItems() {
    OrderCommands tester = new CommandPosterImplTester(
        apiContext(), "googleOrderNumber", false, commands);
    tester.itemCommands("itemId").returnItems();
    assertEquals(1, commands.size());
    ReturnItemsRequest returnItemsRequest =
      (ReturnItemsRequest) commands.get(0).getValue();
    assertEquals(Boolean.FALSE, returnItemsRequest.isSendEmail());
    assertEquals("googleOrderNumber", returnItemsRequest.getGoogleOrderNumber());
    assertEquals(1, returnItemsRequest.getItemIds().getItemId().size());
    assertEquals("itemId", returnItemsRequest.getItemIds().getItemId().get(0).getMerchantItemId());  
  }
  
  public void testNoEmailShipItems() {
    OrderCommands tester = new CommandPosterImplTester(
        apiContext(), "googleOrderNumber", false, commands);
    tester.itemCommands("itemId1", "itemId2").shipItems("c", "tn");
    assertEquals(1, commands.size());
    ShipItemsRequest shipItemsRequest =
      (ShipItemsRequest) commands.get(0).getValue();
    assertEquals(Boolean.FALSE, shipItemsRequest.isSendEmail());
    assertEquals("googleOrderNumber", shipItemsRequest.getGoogleOrderNumber());
    List<ItemShippingInformation> itemShippingInformation =
      shipItemsRequest.getItemShippingInformationList().getItemShippingInformation();
    assertEquals(2, itemShippingInformation.size());
    assertEquals("itemId1", itemShippingInformation.get(0).getItemId().getMerchantItemId());
    assertEquals("itemId2", itemShippingInformation.get(1).getItemId().getMerchantItemId());
    for (ItemShippingInformation i : itemShippingInformation) {
      List<TrackingData> trackingDataList = i.getTrackingDataList().getTrackingData();
      assertEquals(1, trackingDataList.size());
      assertEquals("c", trackingDataList.get(0).getCarrier());
      assertEquals("tn", trackingDataList.get(0).getTrackingNumber());
    }
  }
  
  private TrackingData getTrackingData(List<ItemShippingInformation> itemShippingInformation, int index) {
    return itemShippingInformation.get(0).getTrackingDataList().getTrackingData().get(index);
  }
  
  private final class CommandPosterImplTester extends OrderCommandsImpl {
    private final List<JAXBElement<?>> triedToSend;

    private CommandPosterImplTester(ApiContext apiContext,
        String googleOrderNumber, Boolean sendEmail,
        List<JAXBElement<?>> triedToSend) {
      super(apiContext, googleOrderNumber, sendEmail);
      this.triedToSend = triedToSend;
    }
    
    @Override
    protected RequestReceivedResponse postCommand(JAXBElement<?> command) {
      triedToSend.add(command);
      return new RequestReceivedResponse();
    }
  }
}
