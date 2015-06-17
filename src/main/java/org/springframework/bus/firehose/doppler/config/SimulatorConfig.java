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
