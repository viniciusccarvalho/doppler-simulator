package org.springframework.bus.firehose.doppler.config;

/**
 * @author Vinicius Carvalho
 */
public class RangedMetricDefinition extends WeightedItem{

    private Double min;
    private Double max;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    private String unit;

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }


}
