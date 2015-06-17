package org.springframework.bus.firehose.doppler.config;

/**
 * @author Vinicius Carvalho
 */
public class ContainerMetricDefinition extends AbstractEvent {


    private RangedMetricDefinition applicationId;
    private RangedMetricDefinition cpuPercentage;
    private RangedMetricDefinition memoryBytes;
    private RangedMetricDefinition diskBytes;

    public RangedMetricDefinition getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(RangedMetricDefinition applicationId) {
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
