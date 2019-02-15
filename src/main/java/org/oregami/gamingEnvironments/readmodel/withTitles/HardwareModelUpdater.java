package org.oregami.gamingEnvironments.readmodel.withTitles;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.oregami.gamingEnvironments.event.HardwareModelAddedToHardwarePlatformEvent;
import org.oregami.gamingEnvironments.event.HardwareModelCreatedEvent;
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
@ProcessingGroup("HardwareModelUpdater")
public class HardwareModelUpdater {

    HardwareModelRepository hardwareModelRepository;

    @Autowired
    public HardwareModelUpdater(HardwareModelRepository hardwareModelRepository) {
        this.hardwareModelRepository = hardwareModelRepository;
    }

    @EventHandler
    public void on(HardwareModelCreatedEvent event) {
        RHardwareModel r = new RHardwareModel(event.getNewId(), event.getWorkingTitle(), event.getHardwareModelType()==null?null:event.getHardwareModelType().name());
        r.setChangeTime(LocalDateTime.now());
        hardwareModelRepository.save(r);
    }


}
