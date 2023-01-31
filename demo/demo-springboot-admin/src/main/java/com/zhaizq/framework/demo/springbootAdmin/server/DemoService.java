package com.zhaizq.framework.demo.springbootAdmin.server;

import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.services.InstanceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DemoService {
    @Autowired
    private InstanceRegistry registry;

    @PostConstruct
    public void action() {
        Registration registration = Registration.builder().name("demo")
                .serviceUrl("http://192.168.2.114:8081")
                .healthUrl("http://192.168.2.114:8081/actuator/health")
                .managementUrl("http://192.168.2.114:8081/actuator")
                .metadata("user.name", "client")
                .metadata("user.password", "123456")
                .metadata("startup", "2022-01-01").build();
        Registration withSource = Registration.copyOf(registration).source("http-api").build();
        this.registry.register(withSource).block();
    }
}
