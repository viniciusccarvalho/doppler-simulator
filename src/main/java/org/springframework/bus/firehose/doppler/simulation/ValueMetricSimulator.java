package org.springframework.bus.firehose.doppler.simulation;

import org.cloudfoundry.dropsonde.events.EventFactory;
import org.cloudfoundry.dropsonde.events.MetricFactory;
import org.springframework.bus.firehose.doppler.config.RangedMetricDefinition;
import org.springframework.bus.firehose.doppler.config.ValueMetricDefinition;
import org.springframework.bus.firehose.doppler.util.random.RandomCollection;

import java.util.Map;

/**
 * @author Vinicius Carvalho
 */
public class ValueMetricSimulator extends BaseSimulator {

    private RandomCollection<RangedMetric> metrics;

    public ValueMetricSimulator(ValueMetricDefinition definition, Map<String, RandomCollection<Resource>> availableResources){
        super(definition.getResources(),availableResources);
        metrics = new RandomCollection<>();
        configure(definition);
    }

    @Override
    public EventFactory.Envelope data() {
        Resource resource = internalResources.next().next();
        RangedMetric metric = metrics.next();
        EventFactory.Envelope envelope = EventFactory.Envelope.newBuilder()
                                                    .setEventType(EventFactory.Envelope.EventType.ValueMetric)
                                                    .setOrigin(resource.getName())
                                                    .setTimestamp(System.currentTimeMillis())
                                                    .setIndex(resource.getIndex().toString())
                                                    .setIp(resource.getIp())
                                                    .setValueMetric(MetricFactory.ValueMetric.newBuilder()
                                                                    .setName(metric.getName())
                                                                    .setValue(metric.value())
                                                                    .setUnit(metric.getUnit())
                                                            .build()
                                                    )

                                                    .build();

        return envelope;
    }

    private void configure(ValueMetricDefinition config){

        for(RangedMetricDefinition definition : config.getMetrics()){
            metrics.add(definition.getWeight(),new RangedMetric(definition));
        }

    }
}
