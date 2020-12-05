package com.lizhihai.miaosha.config.tomcat;

import org.apache.catalina.core.AprLifecycleListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AprConfig {


    @Bean
    public ConfigurableWebServerFactory serverFactory() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.setProtocol("org.apache.coyote.http11.Http11AprProtocol");
        tomcat.addContextLifecycleListeners(new AprLifecycleListener());
        return tomcat;

    }
}
