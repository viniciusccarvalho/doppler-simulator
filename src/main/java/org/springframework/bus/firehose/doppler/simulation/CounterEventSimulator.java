package org.springframework.bus.firehose.doppler.simulation;

import org.cloudfoundry.dropsonde.events.EventFactory;
import org.cloudfoundry.dropsonde.events.MetricFactory;
import org.springframework.bus.firehose.doppler.config.CounterEventDefinition;
import org.springframework.bus.firehose.doppler.config.RangedMetricDefinition;
import org.springframework.bus.firehose.doppler.util.random.RandomCollection;

import java.util.Map;

/**
 * @author Vinicius Carvalho
 */
public class CounterEventSimulator extends BaseSimulator {

    private RandomCollection<RangedMetric> metrics;

    public CounterEventSimulator(CounterEventDefinition definition, Map<String, RandomCollection<Resource>> availableResources) {
        super(definition.getResources(), availableResources);
        this.metrics = new RandomCollection<>();
        configure(definition);

    }

    @Override
    public EventFactory.Envelope data() {
        Resource resource = internalResources.next().next();
        RangedMetric metric = metrics.next();
        EventFactory.Envelope envelope = EventFactory.Envelope.newBuilder()
                .setEventType(EventFactory.Envelope.EventType.CounterEvent)
                .setOrigin(resource.getName())
                .setTimestamp(System.currentTimeMillis())
                .setIndex(resource.getIndex().toString())
                .setIp(resource.getIp())
                .setCounterEvent(MetricFactory.CounterEvent.newBuilder()
                        .setName(metric.getName())
                        .setDelta(metric.value().longValue())
                        .build())
                .build();

        return envelope;
    }

    private void configure(CounterEventDefinition definition) {
        for (RangedMetricDefinition def : definition.getMetrics()) {
            metrics.add(definition.getWeight(), new RangedMetric(def));
        }
    }

}
