package org.oregami.gamingEnvironments.readmodel.withTitles;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.oregami.gamingEnvironments.event.GamingEnvironmentCreatedEvent;
import org.oregami.gamingEnvironments.model.GamingEnvironmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by sebastian on 19.02.17.
 */
@Component
@ProcessingGroup("GamingEnvironmentUpdater")
public class GamingEnvironmentUpdater {


    GamingEnvironmentRepository repository;

    @Autowired
    public GamingEnvironmentUpdater(GamingEnvironmentRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(GamingEnvironmentCreatedEvent event) {
        RGamingEnvironment g = new RGamingEnvironment(event.getNewId(), event.getWorkingTitle());
        g.setChangeTime(LocalDateTime.now());
        repository.save(g);
    }

}
