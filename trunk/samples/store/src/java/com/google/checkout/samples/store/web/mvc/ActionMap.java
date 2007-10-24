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

package com.google.checkout.samples.store.web.mvc;

import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;

/**
 * A data structure to provide a map of actions for an request URI
 * @author inder@google.com (Inderjeet Singh)
 */
public class ActionMap {
    
    private static class ActionUnit {
        String actionUri;
        Action action;
        public ActionUnit(String uri, Action action) {
            this.actionUri = uri;
            this.action = action;
        }
    }
    private final Collection<ActionUnit> table = new ArrayList<ActionUnit>();
    private final String controllerPrefix;
    
    public ActionMap(String controllerPrefix) {
      this.controllerPrefix = controllerPrefix;
    }

    public Action get(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        int index = requestURI.indexOf(contextPath) + contextPath.length();
        String controllerPath = requestURI.substring(index);
        index = controllerPath.indexOf(controllerPrefix) + controllerPrefix.length();
        String actionKey = controllerPath.substring(index);
        return get(actionKey);
    }
    
    public Action get(String uri) {
        ActionUnit au = getActionUnit(uri);
        return au == null ? null : au.action;
    }
    
    private ActionUnit getActionUnit(String uri) {
        for (ActionUnit au : table) {
            if (uri.startsWith(au.actionUri)) {
                return au;
            }
        }
        return null;
    }
    public void put(String uri, Action action) {
        ActionUnit au = getActionUnit(uri);
        if (au == null) {
            table.add(new ActionUnit(uri, action));
        } else {
            au.action = action;
        }
    }
}
