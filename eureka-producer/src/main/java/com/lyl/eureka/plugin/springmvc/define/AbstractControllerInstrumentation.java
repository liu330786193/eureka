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

package com.lyl.eureka.plugin.springmvc.define;


import com.lyl.skywalking.plugin.interceptor.ConstructorInterceptPoint;
import com.lyl.skywalking.plugin.interceptor.InstanceMethodsInterceptPoint;
import com.lyl.skywalking.plugin.match.ClassMatch;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static com.lyl.eureka.plugin.springmvc.Constants.REQUEST_MAPPING_METHOD_INTERCEPTOR;
import static com.lyl.eureka.plugin.springmvc.Constants.REST_MAPPING_METHOD_INTERCEPTOR;
import static com.lyl.skywalking.plugin.match.ClassAnnotationMatch.byClassAnnotationMatch;
import static net.bytebuddy.matcher.ElementMatchers.any;
import static net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith;
import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * {@link ControllerInstrumentation} enhance all constructor and method annotated with
 * <code>org.springframework.web.bind.annotation.RequestMapping</code> that class has
 * <code>org.springframework.stereotype.Controller</code> annotation.
 *
 * <code>org.skywalking.apm.plugin.spring.mvc.v4.ControllerConstructorInterceptor</code> set the controller base path to
 * dynamic field before execute constructor.
 *
 * <code>org.skywalking.apm.plugin.spring.mvc.v4.RequestMappingMethodInterceptor</code> get the request path from
 * dynamic field first, if not found, <code>RequestMappingMethodInterceptor</code> generate request path  that
 * combine the path value of current annotation on current method and the base path and set the new path to the dynamic
 * filed
 *
 * @author zhangxin
 */
public abstract class AbstractControllerInstrumentation extends AbstractSpring4Instrumentation {
    @Override
    protected ConstructorInterceptPoint[] getConstructorsInterceptPoints() {
        return new ConstructorInterceptPoint[] {
            new ConstructorInterceptPoint() {
                @Override
                public ElementMatcher<MethodDescription> getConstructorMatcher() {
                    return any();
                }

                @Override
                public String getConstructorInterceptor() {
                    return "com.lyl.eureka.plugin.springmvc.ControllerConstructorInterceptor";
                }
            }
        };
    }

    @Override
    protected InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
        return new InstanceMethodsInterceptPoint[] {
            new InstanceMethodsInterceptPoint() {
                @Override
                public ElementMatcher<MethodDescription> getMethodsMatcher() {
                    return isAnnotatedWith(named("org.springframework.web.bind.annotation.RequestMapping"));
                }

                @Override
                public String getMethodsInterceptor() {
                    return REQUEST_MAPPING_METHOD_INTERCEPTOR;
                }

                @Override
                public boolean isOverrideArgs() {
                    return false;
                }
            },
            new InstanceMethodsInterceptPoint() {
                @Override
                public ElementMatcher<MethodDescription> getMethodsMatcher() {
                    return isAnnotatedWith(named("org.springframework.web.bind.annotation.GetMapping"))
                        .or(isAnnotatedWith(named("org.springframework.web.bind.annotation.PostMapping")))
                        .or(isAnnotatedWith(named("org.springframework.web.bind.annotation.PutMapping")))
                        .or(isAnnotatedWith(named("org.springframework.web.bind.annotation.DeleteMapping")))
                        .or(isAnnotatedWith(named("org.springframework.web.bind.annotation.PatchMapping")));
                }

                @Override
                public String getMethodsInterceptor() {
                    return REST_MAPPING_METHOD_INTERCEPTOR;
                }

                @Override
                public boolean isOverrideArgs() {
                    return false;
                }
            }
        };
    }

    @Override
    protected ClassMatch enhanceClass() {
        return byClassAnnotationMatch(getEnhanceAnnotations());
    }

    protected abstract String[] getEnhanceAnnotations();

}
