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

/**
 * @author Vinicius Carvalho
 */
public class ContainerMetricDefinition extends AbstractEvent {


    private StaticMetricDefinition applicationId;
    private RangedMetricDefinition cpuPercentage;
    private RangedMetricDefinition memoryBytes;
    private RangedMetricDefinition diskBytes;

    public StaticMetricDefinition getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(StaticMetricDefinition applicationId) {
        this.applicationId = applicationId;
    }

    public RangedMetricDefinition getCpuPercentage() {
        return cpuPercentage;
    }

    public void setCpuPercentage(RangedMetricDefinition cpuPercentage) {
        this.cpuPercentage = cpuPercentage;
    }

    public RangedMetricDefinition getMemoryBytes() {
        return memoryBytes;
    }

    public void setMemoryBytes(RangedMetricDefinition memoryBytes) {
        this.memoryBytes = memoryBytes;
    }

    public RangedMetricDefinition getDiskBytes() {
        return diskBytes;
    }

    public void setDiskBytes(RangedMetricDefinition diskBytes) {
        this.diskBytes = diskBytes;
    }

    @Override

    public String getType() {
        return "ContainerMetric";
    }
}
