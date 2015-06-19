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

package org.springframework.bus.firehose.doppler.simulatio;

import org.junit.Test;
import org.springframework.bus.firehose.doppler.config.ContainerMetricDefinition;
import org.springframework.bus.firehose.doppler.config.RangedMetricDefinition;
import org.springframework.bus.firehose.doppler.config.StaticMetricDefinition;
import org.springframework.bus.firehose.doppler.simulation.ContainerMetricSimulator;
import org.springframework.bus.firehose.doppler.simulation.Resource;
import org.springframework.bus.firehose.doppler.util.random.RandomCollection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vinicius Carvalho
 */
public class ContainerMetricSimulatorTests {

    @Test
    public void testRandomData(){

        ContainerMetricDefinition definition = new ContainerMetricDefinition();
        definition.setCpuPercentage(new RangedMetricDefinition("", 1.0, 0.0, 100.0));
        definition.setDiskBytes(new RangedMetricDefinition("", 1.0, 0.0, 100.0));
        definition.setMemoryBytes(new RangedMetricDefinition("", 1.0, 0.0, 100.0));
        definition.setApplicationId(new StaticMetricDefinition("",1.0,5));
        definition.setWeight(1.0);
        definition.setResources(Collections.<String>singletonList("vm1"));
        ContainerMetricSimulator simulator = new ContainerMetricSimulator(definition,availableResources());
        for(int i=0;i<100;i++){
            System.out.println(simulator.data());
        }
    }

    protected Map<String,RandomCollection<Resource>> availableResources(){
        Map<String,RandomCollection<Resource>> resources = new HashMap<>();
        RandomCollection<Resource> resource = new RandomCollection<>();
        resource.add(1.0, new Resource("vm1", 1.0, 0, "10.0.0.2"));
        resources.put("vm1",resource);
        return resources;
    }
}
