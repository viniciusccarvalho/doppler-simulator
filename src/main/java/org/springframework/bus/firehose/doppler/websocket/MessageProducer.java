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

package org.springframework.bus.firehose.doppler.websocket;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.bus.firehose.doppler.simulation.DopplerSimulator;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * @author Vinicius Carvalho
 */
public class MessageProducer implements Runnable {

    private WebSocketSession session;

    private volatile boolean running;

    private DopplerSimulator simulator;

    public void setRunning(boolean running) {
        this.running = running;
    }

    RateLimiter limiter;

    public MessageProducer(WebSocketSession session, Double requestsPerSecond, DopplerSimulator simulator){
        this.session = session;
        this.running = true;
        this.simulator = simulator;
        limiter = RateLimiter.create(requestsPerSecond);
    }

    @Override
    public void run() {
        while (running){
            limiter.acquire();
            try {
                session.sendMessage(new BinaryMessage(simulator.data().toByteArray()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
