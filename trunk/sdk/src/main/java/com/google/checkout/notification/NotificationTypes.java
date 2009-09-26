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

package com.google.checkout.notification;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class NotificationTypes {
  public static final String AUTHORIZATION_AMOUNT_NOTIFICATION = "authorization-amount-notification";
  public static final String CHARGE_AMOUNT_NOTIFICATION = "charge-amount-notification";
  public static final String CHARGEBACK_AMOUNT_NOTIFICATION = "chargeback-amount-notification";
  public static final String NEW_ORDER_NOTIFICATION = "new-order-notification";
  public static final String ORDER_STATE_CHANGE_NOTIFICATION = "order-state-change-notification";
  public static final String REFUND_AMOUNT_NOTIFICATION = "refund-amount-notification";
  public static final String RISK_INFORMATION_NOTIFICATION = "risk-information-notification";  
}