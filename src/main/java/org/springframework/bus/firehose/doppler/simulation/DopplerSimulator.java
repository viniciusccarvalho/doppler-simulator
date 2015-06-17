package org.springframework.bus.firehose.doppler.simulation;

import org.cloudfoundry.dropsonde.events.EventFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.bus.firehose.doppler.config.ResourceDefinition;
import org.springframework.bus.firehose.doppler.config.SimulatorConfig;
import org.springframework.bus.firehose.doppler.util.random.RandomCollection;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vinicius Carvalho
 */
@Component
public class DopplerSimulator implements EventSimulator{

    private SimulatorConfig config;
    private Map<String, RandomCollection<Resource>> resources;
    private RandomCollection<EventSimulator> eventSimulators;
    private String baseIp = "10.0.0.2";

    @Autowired
    public DopplerSimulator(SimulatorConfig config){
        this.config = config;
        this.resources = new HashMap<>();
        this.eventSimulators = new RandomCollection<>();
    }


    public EventFactory.Envelope data(){
        return eventSimulators.next().data();
    }


    @PostConstruct
    public void setup(){
        createResources();
        createSimulators();
    }


    private void createResources(){
        for(ResourceDefinition def : config.getResources()){
            RandomCollection<Resource> resourceList = new RandomCollection<>();
            resources.put(def.getName(),resourceList);
            for(int i=0;i<def.getSize();i++){
                Resource resource = new Resource(def.getName(),1.0,i,baseIp);
                resourceList.add(resource.getWeight(),resource);
                baseIp = getNextIPV4Address(baseIp);
            }
        }
    }

    private void createSimulators(){
        if(config.getValueMetric() != null){
            eventSimulators.add(config.getValueMetric().getWeight(),new ValueMetricSimulator(config.getValueMetric(),resources));
        }
        if(config.getCounterEvent() != null){
            eventSimulators.add(config.getCounterEvent().getWeight(),new CounterEventSimulator(config.getCounterEvent(),resources));
        }
    }


    public SimulatorConfig getConfig() {
        return config;
    }

    private String getNextIPV4Address(String ip) {
        String[] nums = ip.split("\\.");
        int i = (Integer.parseInt(nums[0]) << 24 | Integer.parseInt(nums[2]) << 8
                |  Integer.parseInt(nums[1]) << 16 | Integer.parseInt(nums[3])) + 1;

        // If you wish to skip over .255 addresses.
        if ((byte) i == -1) i++;

        return String.format("%d.%d.%d.%d", i >>> 24 & 0xFF, i >> 16 & 0xFF,
                i >>   8 & 0xFF, i >>  0 & 0xFF);
    }

}
