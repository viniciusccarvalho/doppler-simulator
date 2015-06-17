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

package org.springframework.bus.firehose.doppler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.bus.firehose.doppler.simulation.DopplerSimulator;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Vinicius Carvalho
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DopplerSimulatorApplication.class})
@WebIntegrationTest
public class DopplerSimulationApplicationTest {
    AtomicInteger count = new AtomicInteger(0);
    @Autowired
    private DopplerSimulator simulator;

    @Test
    public void simulatorLoad() throws Exception{
        new Thread(() -> {while (true){
            simulator.data();
            count.incrementAndGet();
        }}).start();
        Thread.sleep(5000L);
        System.out.println("Total Events: " + count);
    }

    @Test
    public void websocketLoad() throws Exception{
        StandardWebSocketClient wsClient = new StandardWebSocketClient();
        MessageCounterHandler handler = new MessageCounterHandler();
        WebSocketSession session = wsClient.doHandshake(handler, "ws://localhost:9090/firehose/firehose-a").get();
        Thread.sleep(5000L);
        System.out.println("Total Events: " + handler.getCounter());
    }

    class MessageCounterHandler extends BinaryWebSocketHandler {

        private AtomicInteger counter = new AtomicInteger(0);
        @Override
        protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
            counter.incrementAndGet();
        }

        public AtomicInteger getCounter() {
            return counter;
        }
    }

}
