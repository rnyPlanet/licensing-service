package com.grin.licensingservice;

import com.grin.licensingservice.events.model.OrganizationChangeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@SpringBootApplication
@RefreshScope
@EnableDiscoveryClient
@EnableFeignClients
@EnableBinding(Sink.class)
public class LicensingServiceApplication {

    private static final Logger logger = LoggerFactory.getLogger(LicensingServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LicensingServiceApplication.class, args);
    }

    @StreamListener(Sink.INPUT)
    public void loggerSink(OrganizationChangeModel orgChange) {
        logger.debug("Received {} event for the organization id {}", orgChange.getAction(), orgChange.getOrganizationId());
    }

}
