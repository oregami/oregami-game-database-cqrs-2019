package org.oregami.gamingEnvironments.readmodel.withTitles;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.oregami.gamingEnvironments.event.HardwareModelAddedToHardwarePlatformEvent;
import org.oregami.gamingEnvironments.event.HardwarePlatformCreatedEvent;
import org.oregami.gamingEnvironments.model.HardwareModelRepository;
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

    HardwarePlatformRepository hardwarePlatformRepository;
    HardwareModelRepository hardwareModelRepository;

    @Autowired
    public HardwarePlatformUpdater(
            HardwarePlatformRepository hardwarePlatformRepository,
            HardwareModelRepository hardwareModelRepository
    ) {
        this.hardwarePlatformRepository = hardwarePlatformRepository;
        this.hardwareModelRepository = hardwareModelRepository;
    }

    @EventHandler
    public void on(HardwarePlatformCreatedEvent event) {
        RHardwarePlatform g = new RHardwarePlatform(event.getNewId(), event.getWorkingTitle(), event.getHardwarePlatformType().name());
        g.setChangeTime(LocalDateTime.now());
        hardwarePlatformRepository.save(g);
    }


    @EventHandler
    public void on(HardwareModelAddedToHardwarePlatformEvent event) {
        RHardwarePlatform hardwarePlatform = hardwarePlatformRepository.getOne(event.getHardwarePlatformId());
        RHardwareModel hardwareModel = hardwareModelRepository.findById(event.getHardwareModelId()).get();
        hardwarePlatform.getHardwareModelSet().add(hardwareModel);
        hardwarePlatform.setChangeTime(LocalDateTime.now());
        hardwarePlatformRepository.save(hardwarePlatform);
    }


}
