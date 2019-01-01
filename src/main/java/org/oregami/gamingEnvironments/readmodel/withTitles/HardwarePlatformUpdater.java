package org.oregami.gamingEnvironments.readmodel.withTitles;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.oregami.gamingEnvironments.event.HardwarePlatformCreatedEvent;
import org.oregami.gamingEnvironments.model.HardwarePlatformRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by sebastian on 19.02.17.
 */
@Component
@ProcessingGroup("HardwarePlatformUpdater")
public class HardwarePlatformUpdater {


    HardwarePlatformRepository repository;

    @Autowired
    public HardwarePlatformUpdater(HardwarePlatformRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(HardwarePlatformCreatedEvent event) {
        RHardwarePlatform g = new RHardwarePlatform(event.getNewId(), event.getWorkingTitle());
        g.setChangeTime(LocalDateTime.now());
        repository.save(g);
    }

}
