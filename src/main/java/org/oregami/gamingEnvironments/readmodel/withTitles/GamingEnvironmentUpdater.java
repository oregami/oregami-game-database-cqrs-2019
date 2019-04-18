package org.oregami.gamingEnvironments.readmodel.withTitles;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.oregami.gamingEnvironments.event.GamingEnvironmentCreatedEvent;
import org.oregami.gamingEnvironments.event.HardwarePlatformAddedToGamingEnvironmentEvent;
import org.oregami.gamingEnvironments.event.YearOfFirstReleaseAddedEvent;
import org.oregami.gamingEnvironments.event.YearOfFirstReleaseChangedEvent;
import org.oregami.gamingEnvironments.model.GamingEnvironmentRepository;
import org.oregami.gamingEnvironments.model.HardwarePlatformRepository;
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

    HardwarePlatformRepository hardwarePlatformRepository;

    @Autowired
    public GamingEnvironmentUpdater(GamingEnvironmentRepository repository, HardwarePlatformRepository hardwarePlatformRepository) {
        this.repository = repository;
        this.hardwarePlatformRepository = hardwarePlatformRepository;
    }

    @EventHandler
    public void on(GamingEnvironmentCreatedEvent event) {
        RGamingEnvironment g = new RGamingEnvironment(event.getNewId(), event.getWorkingTitle());
        g.setChangeTime(LocalDateTime.now());
        repository.save(g);
    }

    @EventHandler
    public void on(YearOfFirstReleaseAddedEvent event) {
        RGamingEnvironment g = repository.getOne(event.getId());
        g.setYearOfFirstRelease(event.getYearOfFirstRelease());
        g.setChangeTime(LocalDateTime.now());
        repository.save(g);
    }

    @EventHandler
    public void on(YearOfFirstReleaseChangedEvent event) {
        RGamingEnvironment g = repository.getOne(event.getId());
        g.setYearOfFirstRelease(event.getYearOfFirstRelease());
        g.setChangeTime(LocalDateTime.now());
        repository.save(g);
    }


    @EventHandler
    public void on(HardwarePlatformAddedToGamingEnvironmentEvent event) {
        RGamingEnvironment g = repository.getOne(event.getGamingEnvironmentId());
        RHardwarePlatform hardwarePlatform = hardwarePlatformRepository.findById(event.getHardwarePlatformId()).get();
        g.setHardwarePlatform(hardwarePlatform);
        g.setChangeTime(LocalDateTime.now());
        repository.save(g);
    }


}
