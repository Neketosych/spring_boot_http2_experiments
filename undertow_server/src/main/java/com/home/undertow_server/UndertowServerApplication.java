package com.home.undertow_server;

import io.undertow.server.HandlerWrapper;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.LearningPushHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class UndertowServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UndertowServerApplication.class, args);
	}

    @Bean
    public UndertowServletWebServerFactory embeddedServletContainerFactory() {
        UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();

        factory.addDeploymentInfoCustomizers(deploymentInfo ->
                deploymentInfo.addInitialHandlerChainWrapper(new HandlerWrapper() {
                    @Override
                    public HttpHandler wrap(HttpHandler handler) {
                        return new LearningPushHandler(1000,
                                (int) TimeUnit.MINUTES.toMillis(5), handler);
                    }
                })
        );
        return factory;
    }
}
