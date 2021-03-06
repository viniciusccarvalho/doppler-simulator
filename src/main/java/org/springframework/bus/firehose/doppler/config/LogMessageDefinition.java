/*
 *
 *  Copyright 2015 original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package org.springframework.bus.firehose.doppler.config;

import java.util.List;

/**
 * @author Vinicius Carvalho
 */
public class LogMessageDefinition extends AbstractEvent {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getMessageType() {
        return messageType;
    }

    public void setMessageType(List<String> messageType) {
        this.messageType = messageType;
    }

    public StaticMetricDefinition getAppId() {
        return appId;
    }

    public void setAppId(StaticMetricDefinition appId) {
        this.appId = appId;
    }

    private String message;
    private List<String> messageType;
    private StaticMetricDefinition appId;

    @Override
    public String getType() {
        return "LogMessage";
    }
}
