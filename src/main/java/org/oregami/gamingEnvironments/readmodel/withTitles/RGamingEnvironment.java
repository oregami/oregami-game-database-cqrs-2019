package org.oregami.gamingEnvironments.readmodel.withTitles;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.oregami.common.BaseEntityUUID;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Year;

/**
 * Created by sebastian on 03.11.16.
 */
@Entity
@NoArgsConstructor
@Getter
public class RGamingEnvironment extends BaseEntityUUID {

    @Column
    String workingTitle;

    @Column
    private Year yearOfFirstRelease;

    @Column
    private LocalDateTime changeTime;

    @OneToOne //(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private RHardwarePlatform hardwarePlatform;

    public RGamingEnvironment(String id, String workingTitle) {
        super(id);
        this.workingTitle = workingTitle;
    }

    public void setChangeTime(LocalDateTime changeTime) {
        this.changeTime = changeTime;
    }

    public LocalDateTime getChangeTime() {
        return changeTime;
    }

    public void setYearOfFirstRelease(Year yearOfFirstRelease) {
        this.yearOfFirstRelease = yearOfFirstRelease;
    }

    public Year getYearOfFirstRelease() {
        return yearOfFirstRelease;
    }

    public String getWorkingTitle() {
        return workingTitle;
    }

    public RHardwarePlatform getHardwarePlatform() {
        return hardwarePlatform;
    }

    public void setHardwarePlatform(RHardwarePlatform hardwarePlatform) {
        this.hardwarePlatform = hardwarePlatform;
    }
}
