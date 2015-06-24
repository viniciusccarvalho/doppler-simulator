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

import org.cloudfoundry.dropsonde.events.EventFactory;
import org.cloudfoundry.dropsonde.events.HttpFactory;
import org.cloudfoundry.dropsonde.events.UuidFactory;
import org.springframework.bus.firehose.doppler.config.HttpStartStopDefinition;
import org.springframework.bus.firehose.doppler.config.RangedMetricDefinition;
import org.springframework.bus.firehose.doppler.config.WeightedItem;
import org.springframework.bus.firehose.doppler.util.random.RandomCollection;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

/**
 * @author Vinicius Carvalho
 */
public class HttpStartStopSimulator extends BaseSimulator {

    private RangedMetric responseTime;
    private RangedMetric contentLenght;
    Random random = new Random();
    private RandomCollection<UuidFactory.UUID> applicationIds;
    private RandomCollection<HttpFactory.PeerType> peerTypes;
    private RandomCollection<HttpFactory.Method> methods;
    private RandomCollection<String> uris;
    private RandomCollection<String> agents;
    private RandomCollection<Integer> statusCodes;

    final HashMap<String,HttpFactory.PeerType> peerMap = new HashMap<>();
    final HashMap<String,HttpFactory.Method> methodMap = new HashMap<>();

    public HttpStartStopSimulator(HttpStartStopDefinition definition, Map<String,RandomCollection<Resource>> availableResources){
        super(definition.getResources(), availableResources);
        peerMap.put("CLIENT", HttpFactory.PeerType.Client);
        peerMap.put("SERVER", HttpFactory.PeerType.Server);
        methodMap.put("GET", HttpFactory.Method.GET);
        methodMap.put("POST", HttpFactory.Method.POST);
        methodMap.put("PUT", HttpFactory.Method.PUT);
        methodMap.put("DELETE", HttpFactory.Method.DELETE);
        methodMap.put("HEAD", HttpFactory.Method.HEAD);
        configure(definition);

    }



    @Override
    public Stream<EventFactory.Envelope> data() {
        Resource resource = internalResources.next().next();
        LinkedList<EventFactory.Envelope> events = new LinkedList<>();
        Long now = System.currentTimeMillis();
        Long responseTimeMillis = responseTime.value().longValue();
        Long stop = now + responseTimeMillis;
        UuidFactory.UUID applicationId = applicationIds.next();
        UuidFactory.UUID requestId = randomUUID();
        HttpFactory.PeerType peerType = peerTypes.next();
        HttpFactory.Method method = methods.next();
        String uri = uris.next();
        String agent = agents.next();
        int statuscode = statusCodes.next();
        long contentLen = contentLenght.value().longValue();
        String remoteAddress = randomAddress();

        EventFactory.Envelope httpStart = baseEnvelope(EventFactory.Envelope.EventType.HttpStart, resource)
                                            .setTimestamp(now)
                                            .setHttpStart(HttpFactory.HttpStart.newBuilder()
                                                            .setTimestamp(now)
                                                            .setRequestId(requestId)
                                                            .setApplicationId(applicationId)
                                                            .setPeerType(peerType)
                                                            .setMethod(method)
                                                            .setUri(uri)
                                                            .setUserAgent(agent)
                                                            .setRemoteAddress(remoteAddress)
                                                            .build()
                                            )
                .build();
        EventFactory.Envelope httpStop = baseEnvelope(EventFactory.Envelope.EventType.HttpStop, resource)
                                            .setTimestamp(stop)
                                            .setHttpStop(HttpFactory.HttpStop.newBuilder()
                                                    .setTimestamp(stop)
                                                    .setUri(uri)
                                                    .setApplicationId(applicationId)
                                                    .setPeerType(peerType)
                                                    .setStatusCode(statuscode)
                                                    .setContentLength(contentLen)
                                                    .setRequestId(requestId)
                                                    .build())
                .build();

        EventFactory.Envelope httpStartStop = baseEnvelope(EventFactory.Envelope.EventType.HttpStartStop,resource)
                                            .setTimestamp(stop)
                                            .setHttpStartStop(HttpFactory.HttpStartStop.newBuilder()
                                                    .setStartTimestamp(now)
                                                    .setStopTimestamp(stop)
                                                    .setApplicationId(applicationId)
                                                    .setRequestId(requestId)
                                                    .setContentLength(contentLen)
                                                    .setMethod(method)
                                                    .setPeerType(peerType)
                                                    .setStatusCode(statuscode)
                                                    .setUri(uri)
                                                    .setUserAgent(agent)
                                                    .setRemoteAddress(remoteAddress)
                                            .build())

                .build();



        events.add(httpStart);
        events.add(httpStop);
        events.add(httpStartStop);
        return events.stream();
    }


    private EventFactory.Envelope.Builder baseEnvelope(EventFactory.Envelope.EventType type, Resource resource){
        return EventFactory.Envelope.newBuilder().setEventType(type)
                .setIp(resource.getIp())
                .setOrigin(resource.getName())
                .setIndex(resource.getIndex().toString());


    }


    private String randomAddress(){
        return String.format("%d.%d.%d.%d", random.nextInt(256), random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    private UuidFactory.UUID randomUUID(){
        return UuidFactory.UUID.newBuilder().setLow(random.nextLong()).setHigh(random.nextLong()).build();
    }

    private void configure(HttpStartStopDefinition definition) {
        this.contentLenght = new RangedMetric(definition.getContentLenght());
        this.responseTime = new RangedMetric(definition.getResponseTime());
        this.applicationIds = new RandomCollection<>();
        this.uris = new RandomCollection<>();
        this.agents = new RandomCollection<>();
        for(int i=0;i<definition.getApplicationId().getSize();i++){
            applicationIds.add(1.0,randomUUID());
        }
        configurePeerType(definition);
        configureMethod(definition);
        configureStatusCode(definition);
        configureUserAgent(definition);
        for(String uri : definition.getUri()){
            uris.add(1.0,uri);
        }

    }

    private void configureUserAgent(HttpStartStopDefinition definition) {
        String[] agentArray = definition.getUserAgent() == null ? new String[]{"Mozilla/5.0"} : definition.getUserAgent();
        for(String agent : agentArray){
            agents.add(1.0,agent);
        }
    }


    private void configureStatusCode(HttpStartStopDefinition definition) {
        this.statusCodes = new RandomCollection<>();
        if(definition.getStatusCode() == null || definition.getStatusCode().isEmpty()){
            statusCodes.add(1.0,200);
        }
        else{
            for(WeightedItem item : definition.getStatusCode()){
                statusCodes.add(item.getWeight(),Integer.valueOf(item.getName()));
            }
        }
    }

    private void configureMethod(HttpStartStopDefinition definition){
        this.methods = new RandomCollection<>();
        if(definition.getMethod() == null || definition.getMethod().isEmpty()){
            for(HttpFactory.Method m : methodMap.values()){
                methods.add(1.0,m);
            }
        }
        else{
            for(WeightedItem item : definition.getMethod()){
                HttpFactory.Method method = methodMap.get(item.getName().toUpperCase());
                if(method != null){
                    methods.add(item.getWeight(),method);
                }
            }
        }
    }

    private void configurePeerType(HttpStartStopDefinition definition){
        this.peerTypes = new RandomCollection<>();
        if(definition.getPeerType() == null || definition.getPeerType().isEmpty()){
           for(HttpFactory.PeerType pt : peerMap.values()){
               peerTypes.add(1.0,pt);
           }
        }
        else{
            for(WeightedItem item : definition.getPeerType()){
                HttpFactory.PeerType pt = peerMap.get(item.getName().toUpperCase());
                if(pt != null){
                    peerTypes.add(item.getWeight(), pt);
                }

            }
        }
    }
}
