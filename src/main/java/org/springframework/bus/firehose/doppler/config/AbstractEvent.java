package org.springframework.bus.firehose.doppler.config;

import java.util.List;

/**
 * @author Vinicius Carvalho
 */
public abstract class AbstractEvent implements Event{

    private Double weight;
    private List<String> resources;



    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }
}
