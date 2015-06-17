package org.springframework.bus.firehose.doppler.simulation;

import org.springframework.bus.firehose.doppler.util.random.RandomCollection;

import java.util.List;
import java.util.Map;

/**
 * @author Vinicius Carvalho
 */
public abstract class BaseSimulator implements EventSimulator{

    protected RandomCollection<RandomCollection<Resource>> internalResources;

    public BaseSimulator(List<String> resourceNames,  Map<String, RandomCollection<Resource>> availableResources){
       this.internalResources = new RandomCollection<>();
        configureResources(resourceNames,availableResources);
    }

    private void configureResources(List<String> resourceNames, Map<String, RandomCollection<Resource>> availableResources){
        for(String resource : resourceNames){
            internalResources.add(1.0, availableResources.get(resource));
        }
    }
}
