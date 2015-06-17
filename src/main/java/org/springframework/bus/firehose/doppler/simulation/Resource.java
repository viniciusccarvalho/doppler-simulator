package org.springframework.bus.firehose.doppler.simulation;

import org.springframework.bus.firehose.doppler.config.WeightedItem;

/**
 * @author Vinicius Carvalho
 */
public class Resource extends WeightedItem {

    private Integer index;
    private String ip;

    public Resource(String name, Double weight,Integer index, String ip) {
        super(name,weight);
        this.index = index;
        this.ip = ip;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
