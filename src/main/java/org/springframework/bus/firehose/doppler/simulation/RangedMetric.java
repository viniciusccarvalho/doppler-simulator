package org.springframework.bus.firehose.doppler.simulation;

import org.springframework.bus.firehose.doppler.config.RangedMetricDefinition;
import org.springframework.bus.firehose.doppler.simulation.Metric;

import java.util.Random;

/**
 * @author Vinicius Carvalho
 */
public class RangedMetric extends BaseMetric<Double> {

    private double min;
    private double max;

    private Random random;

    public RangedMetric(RangedMetricDefinition definition){
        this.random = new Random();
        this.max = definition.getMax();
        this.min = definition.getMin();
        this.name = definition.getName();
        this.unit = definition.getUnit();
    }

    @Override
    public Double value() {
        return (random.nextDouble() * (max-min))+min;
    }
}
