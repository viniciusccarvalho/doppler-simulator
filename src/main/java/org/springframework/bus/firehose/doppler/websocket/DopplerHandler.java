/*
 *
 *   Copyright 2015 original author or authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package org.springframework.bus.firehose.doppler.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.bus.firehose.doppler.simulation.DopplerSimulator;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Vinicius Carvalho
 */
@Component
public class DopplerHandler extends AbstractWebSocketHandler{

    @Autowired
    private DopplerSimulator simulator;



    private Logger logger = LoggerFactory.getLogger(DopplerHandler.class);

    private Map<String, MessageProducer> subscriptions = new ConcurrentHashMap<>();
    private ExecutorService pool = Executors.newFixedThreadPool(4);



    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Creating subscription for {}", session.getId());
        MessageProducer producer = new MessageProducer(session,simulator.getConfig().getEventsPerSecond(),simulator);
        subscriptions.put(session.getId(),producer);
        pool.submit(producer);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        MessageProducer producer = subscriptions.get(session.getId());
        logger.info("Closing subscription for {}", session.getId());
        if(producer != null){
            producer.setRunning(false);
        }
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {

    }
}
