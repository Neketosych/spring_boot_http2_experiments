package com.home.jetty_server;

import org.eclipse.jetty.http2.server.HTTP2CServerConnectionFactory;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlets.PushCacheFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JettyServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JettyServerApplication.class, args);
	}

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory(ServerProperties serverProperties) {
        JettyServletWebServerFactory factory = new JettyServletWebServerFactory();
        factory.addServerCustomizers(new JettyServerCustomizer() {
            @Override
            public void customize(Server server) {
                ServerConnector sc = (ServerConnector) server.getConnectors()[0];
                sc.addConnectionFactory(new HTTP2CServerConnectionFactory(new HttpConfiguration()));
            }
        });
	    return factory;
    }

    @Bean
    public PushCacheFilter pushCacheFilter() {
        return new PushCacheFilter();
    }
}