package org.oregami.gamingEnvironments.readmodel.withTitles;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.oregami.common.BaseEntityUUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * Created by sebastian on 03.11.16.
 */
@Entity
@NoArgsConstructor
@Getter
public class RHardwarePlatform extends BaseEntityUUID {

    @Column
    String workingTitle;

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
}
