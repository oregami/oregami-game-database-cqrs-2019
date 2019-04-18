package org.oregami.config;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.correlation.CorrelationDataProvider;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sebastian on 26.06.17.
 */
@Configuration
@Import({AxonAutoConfiguration.class})
public class AxonConfiguration {

    @Bean
    public CorrelationDataProvider correlationDataProvider() {
        return new CorrelationDataProvider() {
            @Override
            public Map<String, ?> correlationDataFor(Message<?> message) {
                Map<String, Object> m = new HashMap<>();
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication!=null && authentication.isAuthenticated()) {
                    m.put("userId", authentication.getPrincipal());
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
    public CommandBus commandBus() {
        return SimpleCommandBus.builder().build();
    }


}
