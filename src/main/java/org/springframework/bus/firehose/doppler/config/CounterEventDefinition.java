package org.springframework.bus.firehose.doppler.config;

import java.util.List;

/**
 * @author Vinicius Carvalho
 */
public class CounterEventDefinition extends AbstractEvent{

    private List<RangedMetricDefinition> metrics;

    public List<RangedMetricDefinition> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<RangedMetricDefinition> metrics) {
        this.metrics = metrics;
    }

    @Override
    public String getType() {
        return "CounterEvent";
    }


}
