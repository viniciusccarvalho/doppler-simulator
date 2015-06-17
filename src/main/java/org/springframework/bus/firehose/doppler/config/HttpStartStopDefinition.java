package org.springframework.bus.firehose.doppler.config;

/**
 * @author Vinicius Carvalho
 */
public class HttpStartStopDefinition extends AbstractEvent{
    @Override
    public String getType() {
        return "HttpStartStop";
    }
}
