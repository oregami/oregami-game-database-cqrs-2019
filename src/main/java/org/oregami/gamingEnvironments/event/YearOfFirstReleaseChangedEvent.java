package org.oregami.gamingEnvironments.event;

import java.time.Year;

/**
 * Created by sebastian on 24.02.17.
 */
public class YearOfFirstReleaseChangedEvent {

    private final String id;
    private Year yearOfFirstRelease;

    public YearOfFirstReleaseChangedEvent(String id, Year yearOfFirstRelease) {
        this.id = id;
        this.yearOfFirstRelease = yearOfFirstRelease;
    }

    public String getId() {
        return id;
    }

    public Year getYearOfFirstRelease() {
        return yearOfFirstRelease;
    }
}
