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
