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

import com.google.checkout.sdk.commands.EnvironmentInterface.CommandType;
import com.google.checkout.sdk.domain.ArchiveOrderRequest;
import com.google.checkout.sdk.domain.AuthorizeOrderRequest;
import com.google.checkout.sdk.domain.BackorderItemsRequest;
import com.google.checkout.sdk.domain.CancelItemsRequest;
import com.google.checkout.sdk.domain.CancelOrderRequest;
import com.google.checkout.sdk.domain.ChargeAmountNotification;
import com.google.checkout.sdk.domain.ChargeAndShipOrderRequest;
import com.google.checkout.sdk.domain.ItemId;
import com.google.checkout.sdk.domain.ItemShippingInformation;
import com.google.checkout.sdk.domain.OrderStateChangeNotification;
import com.google.checkout.sdk.domain.OrderSummary;
import com.google.checkout.sdk.domain.RefundAmountNotification;
import com.google.checkout.sdk.domain.RefundOrderRequest;
import com.google.checkout.sdk.domain.RequestReceivedResponse;
import com.google.checkout.sdk.domain.ReturnItemsRequest;
import com.google.checkout.sdk.domain.ShipItemsRequest;
import com.google.checkout.sdk.domain.UnarchiveOrderRequest;
import com.google.checkout.sdk.util.Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBElement;

/**
 * Implements {@link OrderCommands}.
 * 
*
 */
class OrderCommandsImpl implements OrderCommands {
  
  private final ApiContext apiContext;
  private final String googleOrderNumber;
  private final Boolean sendEmails;
  
  public OrderCommandsImpl(ApiContext apiContext, String googleOrderNumber) {
    this(apiContext, googleOrderNumber, null);
  }

  public OrderCommandsImpl(ApiContext apiContext,
      String googleOrderNumber, Boolean sendEmails) {
    this.apiContext = apiContext;
    this.googleOrderNumber = googleOrderNumber;
    this.sendEmails = sendEmails;
  }

  @Override
  public OrderStateChangeNotification cancelOrder(
      String reason) throws CheckoutException {
    CancelOrderRequest cancelOrder = new CancelOrderRequest();
    cancelOrder.setGoogleOrderNumber(googleOrderNumber);
    cancelOrder.setReason(reason);
    return postCommand(cancelOrder.toJAXB()).getOrderStateChangeNotification();
  }
  
  @Override
  public RefundAmountNotification refundOrder(
      String reason) throws CheckoutException {
    RefundOrderRequest refundOrderRequest = new RefundOrderRequest();
    refundOrderRequest.setGoogleOrderNumber(googleOrderNumber);
    refundOrderRequest.setReason(reason);
    return postCommand(refundOrderRequest.toJAXB()).getRefundAmountNotification();
  }

  @Override
  public RefundAmountNotification refundOrder(
      String reason, double refundAmount) throws CheckoutException {
    return refundOrder(reason, BigDecimal.valueOf(refundAmount));
  }

  @Override
  public RefundAmountNotification refundOrder(
      String reason, BigDecimal refundAmount) throws CheckoutException {
    RefundOrderRequest refundOrderRequest = new RefundOrderRequest();
    refundOrderRequest.setGoogleOrderNumber(googleOrderNumber);
    refundOrderRequest.setAmount(apiContext.makeMoney(refundAmount));
    refundOrderRequest.setReason(reason);
    return postCommand(refundOrderRequest.toJAXB()).getRefundAmountNotification();
  }

  @Override
  public void authorizeOrder()
      throws CheckoutException {
    AuthorizeOrderRequest authorizeOrderRequest = new AuthorizeOrderRequest();
    authorizeOrderRequest.setGoogleOrderNumber(googleOrderNumber);
    postCommand(authorizeOrderRequest.toJAXB());
  }

  @Override
  public ItemCommands itemCommands(List<String> itemIds) throws CheckoutException {
    List<ItemId> copyIds = new ArrayList<ItemId>();
    for (String s : itemIds) {
      copyIds.add(Utils.makeItemId(s));
    }
    return new ItemCommandsImpl(this, copyIds);
  }

  @Override
  public ItemCommands itemCommands(String... itemIds) throws CheckoutException {
    List<ItemId> copyIds = new ArrayList<ItemId>();
    for (String s : itemIds) {
      copyIds.add(Utils.makeItemId(s));
    }
    return new ItemCommandsImpl(this, copyIds);
  }

  @Override
  public ItemCommands itemCommands(ItemId... itemIds) throws CheckoutException {
    List<ItemId> copyIds = new ArrayList<ItemId>();
    for (ItemId s : itemIds) {
      copyIds.add(Utils.makeItemId(s.getMerchantItemId()));
    }
    return new ItemCommandsImpl(this, copyIds);
  }

  @Override
  public ChargeAmountNotification chargeAndShipOrder() throws CheckoutException {
    return chargeAndShipOrder(null, (TrackingDataBuilder)null);
  }
  
  @Override
  public ChargeAmountNotification chargeAndShipOrder(
      double amount) throws CheckoutException {
    return chargeAndShipOrder(
        BigDecimal.valueOf(amount), (TrackingDataBuilder)null);
  }

  @Override
  public ChargeAmountNotification chargeAndShipOrder(
      BigDecimal bigDecimal) throws CheckoutException {
    return chargeAndShipOrder(bigDecimal, (TrackingDataBuilder)null);
  }
  
  @Override
  public ChargeAmountNotification chargeAndShipOrder(
      TrackingDataBuilder shipping) throws CheckoutException {
    return chargeAndShipOrder(null, shipping);
  }
  
  @Override
  public ChargeAmountNotification chargeAndShipOrder(
      double amount, TrackingDataBuilder shipping) throws CheckoutException {
    return chargeAndShipOrder(BigDecimal.valueOf(amount), shipping);
  }

  @Override
  public ChargeAmountNotification chargeAndShipOrder(
      BigDecimal bigDecimal, TrackingDataBuilder shipping) throws CheckoutException {
    return chargeAndShipOrder(bigDecimal, null, shipping);
  }

  @Override
  public ChargeAmountNotification chargeAndShipOrder(
      ItemShippingInformationBuilder shipping) throws CheckoutException {
    return chargeAndShipOrder(null, shipping);
  }

  @Override
  public ChargeAmountNotification chargeAndShipOrder(
      double amount, ItemShippingInformationBuilder shipping) throws CheckoutException {
    return chargeAndShipOrder(BigDecimal.valueOf(amount), shipping);
  }

  @Override
  public ChargeAmountNotification chargeAndShipOrder(BigDecimal amount,
      ItemShippingInformationBuilder shipping) throws CheckoutException {
    return chargeAndShipOrder(amount, shipping, null);
  }
  
  private ChargeAmountNotification chargeAndShipOrder(
      BigDecimal amount, ItemShippingInformationBuilder itemShipping, TrackingDataBuilder orderShipping)
        throws CheckoutException {
    ChargeAndShipOrderRequest chargeShipRequest = new ChargeAndShipOrderRequest();
    chargeShipRequest.setGoogleOrderNumber(googleOrderNumber);
    chargeShipRequest.setSendEmail(sendEmails);
    if (amount != null) {
      chargeShipRequest.setAmount(apiContext.makeMoney(amount));
    }
    if (itemShipping != null) {
      ChargeAndShipOrderRequest.ItemShippingInformationList itemShippingList =
          new ChargeAndShipOrderRequest.ItemShippingInformationList();
      itemShippingList.getItemShippingInformation().addAll(itemShipping.build());
      chargeShipRequest.setItemShippingInformationList(itemShippingList);
    }
    if (orderShipping != null) {
      ChargeAndShipOrderRequest.TrackingDataList orderShippingList =
          new ChargeAndShipOrderRequest.TrackingDataList();
      orderShippingList.getTrackingData().addAll(orderShipping.build());
      chargeShipRequest.setTrackingDataList(orderShippingList);
    }
    return postCommand(chargeShipRequest.toJAXB()).getChargeAmountNotification();
  }
  
  @Override
  public void shipItems(ItemShippingInformationBuilder shipping)
      throws CheckoutException {
    ShipItemsRequest shipItemsRequest = new ShipItemsRequest();
    shipItemsRequest.setGoogleOrderNumber(googleOrderNumber);
    List<ItemShippingInformation> trackingDatas = shipping.build();
    ShipItemsRequest.ItemShippingInformationList itemShippingList =
      new ShipItemsRequest.ItemShippingInformationList();
    itemShippingList.getItemShippingInformation().addAll(trackingDatas);
    shipItemsRequest.setItemShippingInformationList(itemShippingList);
    shipItemsRequest.setSendEmail(sendEmails);
    postCommand(shipItemsRequest.toJAXB());
  }
  
  @Override
  public void archiveOrder() {
    ArchiveOrderRequest archiveOrderRequest = new ArchiveOrderRequest();
    archiveOrderRequest.setGoogleOrderNumber(googleOrderNumber);
    postCommand(archiveOrderRequest.toJAXB());
  }
  
  @Override
  public void unarchiveOrder() {
    UnarchiveOrderRequest unarchiveOrderRequest = new UnarchiveOrderRequest();
    unarchiveOrderRequest.setGoogleOrderNumber(googleOrderNumber);
    postCommand(unarchiveOrderRequest.toJAXB());
  }
  
  @Override
  public OrderSummary getOrderSummary() {
    return apiContext.reportsRequester().requestOrderSummaries(
        Collections.singletonList(googleOrderNumber)).get(0);
  }
  
  class ItemCommandsImpl implements ItemCommands {

    private final OrderCommandsImpl commandPoster;
    private final List<ItemId> itemIds;

    public ItemCommandsImpl(
        OrderCommandsImpl commandPoster, List<ItemId> itemIds) {
      this.commandPoster = commandPoster;
      this.itemIds = itemIds;
    }

    // cancels just the items
    @Override
    public void cancelItems(
        String reason) throws CheckoutException {
      CancelItemsRequest cancelItems = new CancelItemsRequest();
      cancelItems.setGoogleOrderNumber(commandPoster.googleOrderNumber);
      CancelItemsRequest.ItemIds idsObject = new CancelItemsRequest.ItemIds();
      idsObject.getItemId().addAll(itemIds);
      cancelItems.setItemIds(idsObject);
      cancelItems.setReason(reason);
      cancelItems.setSendEmail(commandPoster.sendEmails);
      postCommand(cancelItems.toJAXB());
    }

    @Override
    public void backorderItems()
        throws CheckoutException {
      BackorderItemsRequest backorderItems = new BackorderItemsRequest();
      backorderItems.setGoogleOrderNumber(commandPoster.googleOrderNumber);
      BackorderItemsRequest.ItemIds idsObject =
        new BackorderItemsRequest.ItemIds();
      idsObject.getItemId().addAll(itemIds);
      backorderItems.setItemIds(idsObject);
      backorderItems.setSendEmail(commandPoster.sendEmails);
      postCommand(backorderItems.toJAXB());
    }

    @Override
    public void returnItems() throws CheckoutException {
      ReturnItemsRequest returnItems = new ReturnItemsRequest();
      returnItems.setGoogleOrderNumber(commandPoster.googleOrderNumber);
      ReturnItemsRequest.ItemIds idsObject = new ReturnItemsRequest.ItemIds();
      idsObject.getItemId().addAll(itemIds);
      returnItems.setItemIds(idsObject);
      returnItems.setSendEmail(commandPoster.sendEmails);
      postCommand(returnItems.toJAXB());
    }

    @Override
    public void shipItems(String carrier, String trackingNumber)
        throws CheckoutException {
      ItemShippingInformationBuilder shippingBuilder = new ItemShippingInformationBuilder();
      for(ItemId id : itemIds) {
        shippingBuilder.addShipping(id,
            new TrackingDataBuilder().addTrackingData(carrier, trackingNumber));
      }
      commandPoster.shipItems(shippingBuilder);
    }
  }
  
  protected RequestReceivedResponse postCommand(JAXBElement<?> command) {
    return (RequestReceivedResponse) apiContext.postCommand(
        CommandType.ORDER_PROCESSING, command);
  }
}
