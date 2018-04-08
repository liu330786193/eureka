
package com.lyl.eureka.plugin.springmvc.define;

public class RestControllerInstrumentation extends AbstractControllerInstrumentation {

    public static final String ENHANCE_ANNOTATION = "org.springframework.web.bind.annotation.RestController";

    @Override protected String[] getEnhanceAnnotations() {
        return new String[] {ENHANCE_ANNOTATION};
    }
}
