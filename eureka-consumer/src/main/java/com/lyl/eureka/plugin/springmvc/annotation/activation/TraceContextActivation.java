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

package com.lyl.eureka.plugin.springmvc.annotation.activation;

import com.lyl.skywalking.plugin.interceptor.StaticMethodsInterceptPoint;
import com.lyl.skywalking.plugin.interceptor.enhance.ClassStaticMethodsEnhancePluginDefine;
import com.lyl.skywalking.plugin.match.ClassMatch;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static com.lyl.skywalking.plugin.match.NameMatch.byName;
import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * Active the toolkit class "org.skywalking.apm.toolkit.trace.TraceContext".
 * Should not dependency or import any class in "skywalking-toolkit-trace-context" module.
 * Activation's classloader is diff from "org.skywalking.apm.toolkit.trace.TraceContext",
 * using direct will trigger classloader issue.
 * <p>
 * Created by xin on 2016/12/15.
 */
public class TraceContextActivation extends ClassStaticMethodsEnhancePluginDefine {
    /**
     * @return the target class, which needs active.
     */
    @Override
    protected ClassMatch enhanceClass() {
        return byName("com.lyl.eureka.plugin.springmvc.annotation.TraceContext");
    }

    /**
     * @return the collection of {@link StaticMethodsInterceptPoint}, represent the intercepted methods and their
     * interceptors.
     */
    @Override
    protected StaticMethodsInterceptPoint[] getStaticMethodsInterceptPoints() {
        return new StaticMethodsInterceptPoint[] {
            new StaticMethodsInterceptPoint() {
                @Override
                public ElementMatcher<MethodDescription> getMethodsMatcher() {
                    return named("traceId");
                }

                @Override
                public String getMethodsInterceptor() {
                    return "com.lyl.eureka.plugin.springmvc.annotation.activation.TraceContextInterceptor";
                }

                @Override public boolean isOverrideArgs() {
                    return false;
                }
            }
        };
    }
}
