package org.springframework.bus.firehose.doppler.config;

import java.util.List;

/**
 * @author Vinicius Carvalho
 */
public class ValueMetricDefinition extends AbstractEvent{

    @Override
    public String getType() {
        return "ValueMetric";
    }

    private List<RangedMetricDefinition> metrics;

    public List<RangedMetricDefinition> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<RangedMetricDefinition> metrics) {
        this.metrics = metrics;
    }
}
