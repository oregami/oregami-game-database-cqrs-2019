package org.oregami.gamingEnvironments.readmodel.withTitles;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.oregami.common.BaseEntityUUID;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by sebastian on 03.11.16.
 */
@Entity
@NoArgsConstructor
@Getter
public class RHardwarePlatform extends BaseEntityUUID {

    @Column
    String workingTitle;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private Set<RHardwareModel> hardwareModelSet = new TreeSet<>();

    @Column
    private LocalDateTime changeTime;

    @Column
    private String hardwarePlatformType;

    @Column
    private String region;

    public RHardwarePlatform(String id, String workingTitle, String hardwarePlatformType) {
        super(id);
        this.workingTitle = workingTitle;
        this.hardwarePlatformType = hardwarePlatformType;
    }

    public void setChangeTime(LocalDateTime changeTime) {
        this.changeTime = changeTime;
    }

    public LocalDateTime getChangeTime() {
        return changeTime;
    }

    public String getWorkingTitle() {
        return workingTitle;
    }

    public Set<RHardwareModel> getHardwareModelSet() {
        return hardwareModelSet;
    }

    public void setHardwareModelSet(Set<RHardwareModel> hardwareModelSet) {
        this.hardwareModelSet = hardwareModelSet;
    }

    public String getHardwarePlatformType() {
        return hardwarePlatformType;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
