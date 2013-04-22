/*
 * Copyright 2012 Riccardo Sirchia
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.camel.component.disruptor;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 * @version
 */
public class DisruptorTimeoutDisabledTest extends CamelTestSupport {
    @Test
    public void testDisruptorNoTimeout() throws Exception {
        final Future<String> out = template.asyncRequestBody("disruptor:foo?timeout=0", "World", String.class);
        // use 5 sec failsafe in case something hangs
        assertEquals("Bye World", out.get(5, TimeUnit.SECONDS));
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("disruptor:foo").to("mock:before").delay(500).transform(body().prepend("Bye ")).to("mock:result");
            }
        };
    }
}
