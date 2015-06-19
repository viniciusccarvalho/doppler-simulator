/*
 *
 *  Copyright 2015 original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package org.springframework.bus.firehose.doppler.simulation;

import com.google.protobuf.ByteString;
import org.cloudfoundry.dropsonde.events.EventFactory;
import org.cloudfoundry.dropsonde.events.LogFactory;
import org.springframework.bus.firehose.doppler.config.LogMessageDefinition;
import org.springframework.bus.firehose.doppler.util.random.RandomCollection;

import java.util.Map;
import java.util.UUID;

/**
 * @author Vinicius Carvalho
 */
public class LogMessageSimulator extends BaseSimulator{
    private RandomCollection<String> applicationIds;
    private RandomCollection<LogFactory.LogMessage.MessageType> messageTypes;
    private RandomCollection<String> messages;
    public LogMessageSimulator(LogMessageDefinition definition, Map<String,RandomCollection<Resource>> resources){
        super(definition.getResources(),resources);
        this.applicationIds = new RandomCollection<>();
        this.messageTypes = new RandomCollection<>();
        this.messages = new RandomCollection<>();
        configure(definition);
    }



    @Override
    public EventFactory.Envelope data() {
        Resource resource = internalResources.next().next();
        EventFactory.Envelope envelope = EventFactory.Envelope.newBuilder()
                .setEventType(EventFactory.Envelope.EventType.LogMessage)
                .setOrigin(resource.getName())
                .setTimestamp(System.currentTimeMillis())
                .setIndex(resource.getIndex().toString())
                .setIp(resource.getIp())
                .setLogMessage(LogFactory.LogMessage.newBuilder()
                        .setTimestamp(System.currentTimeMillis())
                        .setAppId(applicationIds.next())
                        .setMessageType(messageTypes.next())
                        .setMessage(ByteString.copyFrom(messages.next().getBytes()))
                .build())
                .build();
        return envelope;
    }

    private void configure(LogMessageDefinition definition) {
        messages.add(1.0,definition.getMessage());
        for(int i =0; i<definition.getAppId().getSize();i++){
            applicationIds.add(1.0, UUID.randomUUID().toString());
        }
        for(String message : definition.getMessageType()){
            LogFactory.LogMessage.MessageType messageType = LogFactory.LogMessage.MessageType.valueOf(message);
            if(messageType != null){
                messageTypes.add(1.0, messageType);
            }
        }
    }
}
