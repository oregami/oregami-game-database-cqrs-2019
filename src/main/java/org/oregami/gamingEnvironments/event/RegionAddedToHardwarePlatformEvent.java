package org.oregami.gamingEnvironments.event;

import org.oregami.gamingEnvironments.model.types.Region;

/**
 * Created by sebastian on 24.02.17.
 */
public class RegionAddedToHardwarePlatformEvent implements EventWithId<RegionAddedToHardwarePlatformEvent> {

    private String id;
    private Region region;

    public RegionAddedToHardwarePlatformEvent(String id, Region region) {
        this.id = id;
        this.region = region;
    }

    public String getId() {
        return id;
    }

    public Region getRegion() {
        return region;
    }

    @Override
    public RegionAddedToHardwarePlatformEvent withId(String id) {
        this.id = id;
        return this;
    }
}
