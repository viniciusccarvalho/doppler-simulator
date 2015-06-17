package org.springframework.bus.firehose.doppler.simulation;

/**
 * @author Vinicius Carvalho
 */
public interface Metric<T> {
    public T value();
}
