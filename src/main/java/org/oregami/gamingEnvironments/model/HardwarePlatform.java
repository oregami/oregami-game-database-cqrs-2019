package org.oregami.gamingEnvironments.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.oregami.gamingEnvironments.command.AddHardwareModelToHardwarePlatformCommand;
import org.oregami.gamingEnvironments.command.AddRegionToHardwarePlatformCommand;
import org.oregami.gamingEnvironments.command.CreateHardwarePlatformCommand;
import org.oregami.gamingEnvironments.event.HardwareModelAddedToHardwarePlatformEvent;
import org.oregami.gamingEnvironments.event.HardwarePlatformCreatedEvent;
import org.oregami.gamingEnvironments.event.RegionAddedToHardwarePlatformEvent;
import org.oregami.gamingEnvironments.model.types.HardwarePlatformType;
import org.oregami.gamingEnvironments.model.types.Region;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebastian on 03.11.16.
 */
@NoArgsConstructor
@Aggregate
@EqualsAndHashCode
public class HardwarePlatform {


    @AggregateIdentifier
    private String id;

    private String workingTitle;

    private HardwarePlatformType hardwarePlatformType;

    List<String> hardwareModelIdList = new ArrayList<>();

    private Region region = null;



    @CommandHandler
    public HardwarePlatform(CreateHardwarePlatformCommand command) {
        AggregateLifecycle.apply(new HardwarePlatformCreatedEvent(command.getNewId(), command.getWorkingTitle(), command.getHardwarePlatformType()));
    }

    @EventSourcingHandler
    public void in(HardwarePlatformCreatedEvent event) {
        this.id = event.getNewId();
    }


    @CommandHandler
    public void in(AddHardwareModelToHardwarePlatformCommand command) {
        AggregateLifecycle.apply(new HardwareModelAddedToHardwarePlatformEvent(command.getHardwarePlatformId(), command.getHardwareModelId()));
    }

    @EventSourcingHandler
    public void in(HardwareModelAddedToHardwarePlatformEvent event) {
        this.hardwareModelIdList.add(event.getHardwareModelId());
    }

    @CommandHandler
    public void in(AddRegionToHardwarePlatformCommand command) {
        AggregateLifecycle.apply(new RegionAddedToHardwarePlatformEvent(command.getHardwarePlatformId(), command.getRegion()));
    }

    @EventSourcingHandler
    public void in(RegionAddedToHardwarePlatformEvent event) {
        this.region = event.getRegion();
    }

}
