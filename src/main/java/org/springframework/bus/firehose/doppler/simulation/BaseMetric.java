package org.springframework.bus.firehose.doppler.simulation;

/**
 * @author Vinicius Carvalho
 */
public abstract class BaseMetric<T> implements Metric {
    protected  String name;
    protected String unit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
