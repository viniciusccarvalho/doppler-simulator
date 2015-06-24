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
public class HttpStartStopDefinition extends AbstractEvent{

    private List<WeightedItem> peerType;
    private List<WeightedItem> method;
    private String[] uri;
    private String[] userAgent;
    private List<WeightedItem> statusCode;
    private RangedMetricDefinition contentLenght;
    private RangedMetricDefinition responseTime;
    private StaticMetricDefinition applicationId;

    public List<WeightedItem> getPeerType() {
        return peerType;
    }

    public void setPeerType(List<WeightedItem> peerType) {
        this.peerType = peerType;
    }

    public List<WeightedItem> getMethod() {
        return method;
    }

    public void setMethod(List<WeightedItem> method) {
        this.method = method;
    }

    public String[] getUri() {
        return uri;
    }

    public void setUri(String[] uri) {
        this.uri = uri;
    }

    public String[] getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String[] userAgent) {
        this.userAgent = userAgent;
    }

    public List<WeightedItem> getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(List<WeightedItem> statusCode) {
        this.statusCode = statusCode;
    }

    public RangedMetricDefinition getContentLenght() {
        return contentLenght;
    }

    public void setContentLenght(RangedMetricDefinition contentLenght) {
        this.contentLenght = contentLenght;
    }

    public StaticMetricDefinition getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(StaticMetricDefinition applicationId) {
        this.applicationId = applicationId;
    }

    public RangedMetricDefinition getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(RangedMetricDefinition responseTime) {
        this.responseTime = responseTime;
    }

    @Override
    public String getType() {
        return "HttpStartStop";
    }


}
