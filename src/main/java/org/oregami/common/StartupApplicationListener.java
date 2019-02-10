package org.oregami.common;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupApplicationListener implements
        ApplicationListener<ContextRefreshedEvent> {
 
    private static final Logger LOG = LoggerFactory.getLogger(StartupApplicationListener.class);

    @Autowired
    CommandBus commandBus;

    @Autowired
    ValidationInterceptor validationInterceptor;

    public static int counter;
 
    @Override public void onApplicationEvent(ContextRefreshedEvent event) {
        LOG.info("Increment counter");
        counter++;


        if (commandBus instanceof SimpleCommandBus) {
            LOG.info("ValidationInterceptor registered to SimpleCommandBus");
            ((SimpleCommandBus)commandBus).registerHandlerInterceptor(validationInterceptor);
        }
    }
}