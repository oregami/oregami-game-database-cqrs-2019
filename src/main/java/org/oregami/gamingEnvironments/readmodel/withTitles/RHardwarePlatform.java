package org.oregami.gamingEnvironments.readmodel.withTitles;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.oregami.common.BaseEntityUUID;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

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

    public RHardwarePlatform(String id, String workingTitle) {
        super(id);
        this.workingTitle = workingTitle;
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
}
