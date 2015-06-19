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

import org.cloudfoundry.dropsonde.events.EventFactory;
import org.cloudfoundry.dropsonde.events.MetricFactory;
import org.springframework.bus.firehose.doppler.config.ContainerMetricDefinition;
import org.springframework.bus.firehose.doppler.util.random.RandomCollection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Vinicius Carvalho
 */
public class ContainerMetricSimulator extends BaseSimulator {

    private RangedMetric cpu;
    private RangedMetric memory;
    private RangedMetric disk;
    private RandomCollection<Map> applicationIds;

    public ContainerMetricSimulator(ContainerMetricDefinition definition, Map<String, RandomCollection<Resource>> availableResources) {
        super(definition.getResources(), availableResources);
        this.applicationIds = new RandomCollection<>();
        configure(definition);
    }

    @Override
    public EventFactory.Envelope data() {
        Resource resource = internalResources.next().next();
        Map application = applicationIds.next();
        EventFactory.Envelope envelope = EventFactory.Envelope.newBuilder()
                .setEventType(EventFactory.Envelope.EventType.ContainerMetric)
                .setOrigin(resource.getName())
                .setTimestamp(System.currentTimeMillis())
                .setIndex(resource.getIndex().toString())
                .setIp(resource.getIp())
                .setContainerMetric(
                        MetricFactory.ContainerMetric.newBuilder()
                        .setApplicationId(application.get("id").toString())
                        .setCpuPercentage(cpu.value())
                        .setDiskBytes(disk.value().longValue())
                        .setMemoryBytes(memory.value().longValue())
                        .setInstanceIndex((Integer)application.get("index"))
                        .build()
                )
                .build();
        return envelope;
    }

    private void configure(ContainerMetricDefinition definition){
        this.cpu = new RangedMetric(definition.getCpuPercentage());
        this.memory = new RangedMetric(definition.getMemoryBytes());
        this.disk = new RangedMetric(definition.getDiskBytes());
        for(int i=0;i<definition.getApplicationId().getSize();i++){
            Map application = new HashMap<>();
            application.put("index",i);
            application.put("id",UUID.randomUUID().toString());
            applicationIds.add(1.0, application);
        }
    }
}
