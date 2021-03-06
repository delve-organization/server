package com.github.delve.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.MultipartConfigElement;
import java.lang.reflect.Method;

@Configuration
public class WebMvcConfig {

    private final String apiBasePath;

    @Autowired
    public WebMvcConfig(@Value("${api.base-path}") final String apiBasePath) {
        this.apiBasePath = apiBasePath;
    }

    @Bean
    public ServletRegistrationBean dispatcherRegistration(final WebApplicationContext ctx) {
        final ServletRegistrationBean servletRegistration = new ServletRegistrationBean<>(new LoggableDispatcherServlet(ctx, apiBasePath));
        servletRegistration.setMultipartConfig(new MultipartConfigElement("/"));

        return servletRegistration;
    }

    @Bean
    public WebMvcRegistrations webMvcRegistrationsHandlerMapping() {
        return new WebMvcRegistrations() {
            @Override
            public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
                return new RequestMappingHandlerMapping() {
                    @Override
                    protected void registerHandlerMethod(final Object handler, final Method method, RequestMappingInfo mapping) {
                        final Class<?> beanType = method.getDeclaringClass();
                        if (AnnotationUtils.findAnnotation(beanType, RestApiController.class) != null) {
                            final PatternsRequestCondition apiPattern = new PatternsRequestCondition(apiBasePath)
                                    .combine(mapping.getPatternsCondition());

                            mapping = new RequestMappingInfo(mapping.getName(), apiPattern,
                                    mapping.getMethodsCondition(), mapping.getParamsCondition(),
                                    mapping.getHeadersCondition(), mapping.getConsumesCondition(),
                                    mapping.getProducesCondition(), mapping.getCustomCondition());
                        }

                        super.registerHandlerMethod(handler, method, mapping);
                    }
                };
            }
        };
    }

}
