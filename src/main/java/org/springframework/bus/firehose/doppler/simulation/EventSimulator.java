package org.springframework.bus.firehose.doppler.simulation;

import org.cloudfoundry.dropsonde.events.EventFactory;

/**
 * @author Vinicius Carvalho
 */
public interface EventSimulator {
    EventFactory.Envelope data();
}
