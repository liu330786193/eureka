/*
 * Copyright 2017, OpenSkywalking Organization All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project repository: https://github.com/OpenSkywalking/skywalking
 */

package com.lyl.eureka.plugin.feign;

import com.lyl.skywalking.component.ComponentsDefine;
import com.lyl.skywalking.context.CarrierItem;
import com.lyl.skywalking.context.ContextCarrier;
import com.lyl.skywalking.context.ContextManager;
import com.lyl.skywalking.context.tag.Tags;
import com.lyl.skywalking.context.trace.AbstractSpan;
import com.lyl.skywalking.context.trace.SpanLayer;
import com.lyl.skywalking.plugin.interceptor.enhance.EnhancedInstance;
import com.lyl.skywalking.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import com.lyl.skywalking.plugin.interceptor.enhance.MethodInterceptResult;
import feign.Request;
import feign.Response;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;

/**
 * {@link DefaultHttpClientInterceptor} intercept the default implementation of http calls by the Feign.
 *
 * @author peng-yongsheng
 */
public class DefaultHttpClientInterceptor implements InstanceMethodsAroundInterceptor {

    private static final String COMPONENT_NAME = "FeignDefaultHttp";

    /**
     * Get the {@link Request} from {@link EnhancedInstance}, then create {@link AbstractSpan} and set host,
     * port, kind, component, url from {@link Request}.
     * Through the reflection of the way, set the http header of context data into {@link Request#headers}.
     *
     * @param method
     * @param result change this result, if you want to truncate the method.
     * @throws Throwable
     */
    @Override public void beforeMethod(EnhancedInstance objInst, Method method, Object[] allArguments,
                                       Class<?>[] argumentsTypes, MethodInterceptResult result) throws Throwable {
        Request request = (Request)allArguments[0];

        URL url = new URL(request.url());
        ContextCarrier contextCarrier = new ContextCarrier();
        String remotePeer = url.getHost() + ":" + url.getPort();
        AbstractSpan span = ContextManager.createExitSpan(request.url(), contextCarrier, remotePeer);
        span.setComponent(ComponentsDefine.FEIGN);
        Tags.HTTP.METHOD.set(span, request.method());
        Tags.URL.set(span, url.getPath());
        SpanLayer.asHttp(span);

        Field headersField = Request.class.getDeclaredField("headers");
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(headersField, headersField.getModifiers() & ~Modifier.FINAL);

        headersField.setAccessible(true);
        Map<String, Collection<String>> headers = new LinkedHashMap<String, Collection<String>>();
        CarrierItem next = contextCarrier.items();
        while (next.hasNext()) {
            next = next.next();
            List<String> contextCollection = new LinkedList<String>();
            contextCollection.add(next.getHeadValue());
            headers.put(next.getHeadKey(), contextCollection);
        }
        headers.putAll(request.headers());

        headersField.set(request, Collections.unmodifiableMap(headers));
    }

    /**
     * Get the status code from {@link Response}, when status code greater than 400, it means there was some errors in
     * the server.
     * Finish the {@link AbstractSpan}.
     *
     * @param method
     * @param ret the method's original return value.
     * @return
     * @throws Throwable
     */
    @Override public Object afterMethod(EnhancedInstance objInst, Method method, Object[] allArguments,
        Class<?>[] argumentsTypes, Object ret) throws Throwable {
        Response response = (Response)ret;
        if (response != null) {
            int statusCode = response.status();

            AbstractSpan span = ContextManager.activeSpan();
            if (statusCode >= 400) {
                span.errorOccurred();
                Tags.STATUS_CODE.set(span, statusCode + "");
            }
        }

        ContextManager.stopSpan();

        return ret;
    }

    @Override public void handleMethodException(EnhancedInstance objInst, Method method, Object[] allArguments,
        Class<?>[] argumentsTypes, Throwable t) {
        AbstractSpan activeSpan = ContextManager.activeSpan();
        activeSpan.log(t);
        activeSpan.errorOccurred();
    }
}
