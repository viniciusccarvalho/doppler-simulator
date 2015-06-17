package org.springframework.bus.firehose.doppler.config;

/**
 * @author Vinicius Carvalho
 */
public class ResourceDefinition {

    private String name;
    private Integer size;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
