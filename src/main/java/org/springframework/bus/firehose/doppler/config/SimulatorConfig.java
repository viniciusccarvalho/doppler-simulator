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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Vinicius Carvalho
 */
@ConfigurationProperties(prefix = "simulator")
@Component
public class SimulatorConfig {

    private CounterEventDefinition counterEvent;
    private ValueMetricDefinition valueMetric;
    private List<ResourceDefinition> resources;
    private Double eventsPerSecond = 100.0;

    public Double getEventsPerSecond() {
        return eventsPerSecond;
    }

    public void setEventsPerSecond(Double eventsPerSecond) {
        this.eventsPerSecond = eventsPerSecond;
    }

    public CounterEventDefinition getCounterEvent() {
        return counterEvent;
    }

    public void setCounterEvent(CounterEventDefinition counterEvent) {
        this.counterEvent = counterEvent;
    }

    public ValueMetricDefinition getValueMetric() {
        return valueMetric;
    }

    public void setValueMetric(ValueMetricDefinition valueMetric) {
        this.valueMetric = valueMetric;
    }

    public List<ResourceDefinition> getResources() {
        return resources;
    }

    public void setResources(List<ResourceDefinition> resources) {
        this.resources = resources;
    }
}
