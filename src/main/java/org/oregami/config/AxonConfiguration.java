package org.oregami.config;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventhandling.tokenstore.inmemory.InMemoryTokenStore;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.correlation.CorrelationDataProvider;
import org.axonframework.messaging.interceptors.CorrelationDataInterceptor;
import org.axonframework.monitoring.NoOpMessageMonitor;
import org.keycloak.KeycloakPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sebastian on 26.06.17.
 */
@Configuration
public class AxonConfiguration {

    @Bean
    public CorrelationDataProvider correlationDataProvider() {
        return new CorrelationDataProvider() {
            @Override
            public Map<String, ?> correlationDataFor(Message<?> message) {
                Map<String, Object> m = new HashMap<>();
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication!=null && authentication.isAuthenticated()) {
                    try {
                        KeycloakPrincipal principal = (KeycloakPrincipal) authentication.getPrincipal();
                        m.put("userId", principal.getName());
                        m.put("username", principal.getKeycloakSecurityContext().getToken().getPreferredUsername());
                    }
                    catch(ClassCastException e) {
                        m.put("userId", authentication.getPrincipal());
                    }
                } else {
                    m.put("userId", "getAuthentication-null");
                }
                return m;
            }
        };
    }


    @Bean
    public EventStore eventStore() {
        InMemoryEventStorageEngine storageEngine = new InMemoryEventStorageEngine();
        return new EmbeddedEventStore.Builder()
                .storageEngine(storageEngine)
                .cachedEvents(100)
        .build();
    }


    @Bean
    public SimpleCommandBus commandBus(TransactionManager txManager) {
        SimpleCommandBus commandBus =
                SimpleCommandBus.builder()
                        .transactionManager(txManager)
                        .build();
        commandBus.registerHandlerInterceptor(
                new CorrelationDataInterceptor<>(correlationDataProvider())
        );
        return commandBus;
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }


    @Autowired
    public void configure(EventProcessingConfigurer config) {

        boolean synchronousProcessing = false;
        //true => process all events synchronous, which prevents replaying of events!
        //false => process all events asynchronous in extra threads, which makes replaying of events possible

        if (synchronousProcessing) {
            config.usingSubscribingEventProcessors();
        }
    }




}
