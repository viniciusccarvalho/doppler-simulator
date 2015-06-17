package org.springframework.bus.firehose.doppler.config;

/**
 * @author Vinicius Carvalho
 */
public class WeightedItem {

    private String name;
    private Double weight = 1.0;

    public WeightedItem(){}

    public WeightedItem(String name, Double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
