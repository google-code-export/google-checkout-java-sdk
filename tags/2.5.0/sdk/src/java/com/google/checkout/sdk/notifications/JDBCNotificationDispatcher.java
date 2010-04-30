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
package com.google.checkout.sdk.notifications;

import com.google.checkout.sdk.domain.OrderSummary;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>A {@link BaseNotificationDispatcher} which uses a JDBC Connection to
 * handle transactionality. This is more general than
 * {@link NamedDatabaseNotificationDispatcher} because it makes no
 * assumptions about your table or column layouts; therefore, you must override
 * {@link #hasAlreadyHandled} and {@link #rememberSerialNumber} with code
 * specific to your needs.</p>
 * 
*
 * @see BaseNotificationDispatcher
 */
public abstract class JDBCNotificationDispatcher extends BaseNotificationDispatcher {

  private final Connection databaseConnection;
  
  private Integer transactionIsolation = null;
  private Boolean autoCommit = null;
  
  protected JDBCNotificationDispatcher(
      HttpServletRequest request, HttpServletResponse response,
      Connection databaseConnection) {
    super(request, response);
    this.databaseConnection = databaseConnection;
  }

  @Override
  protected void startTransaction(String serialNumber, OrderSummary orderSummary,
      Notification notification) throws Exception {
    transactionIsolation = databaseConnection.getTransactionIsolation();
    autoCommit = databaseConnection.getAutoCommit();
    databaseConnection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
    databaseConnection.setAutoCommit(false);
  }

  @Override
  protected void commitTransaction(String serialNumber, OrderSummary orderSummary,
      Notification notification) throws Exception {
    databaseConnection.commit();
    databaseConnection.setTransactionIsolation(transactionIsolation);
    databaseConnection.setAutoCommit(autoCommit);
  }

  @Override
  protected void rollBackTransaction(String serialNumber, OrderSummary orderSummary,
      Notification notification) throws Exception {
    databaseConnection.rollback();
    if (transactionIsolation != null) {
      databaseConnection.setTransactionIsolation(transactionIsolation);
    }
    if (autoCommit != null) {
      databaseConnection.setAutoCommit(autoCommit);
    }
  }
  
  protected Connection getConnection() {
    return databaseConnection;
  }
}
