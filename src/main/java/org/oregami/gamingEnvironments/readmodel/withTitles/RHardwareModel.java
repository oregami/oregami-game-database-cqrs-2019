package org.oregami.gamingEnvironments.readmodel.withTitles;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.oregami.common.BaseEntityUUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class RHardwareModel extends BaseEntityUUID {

    @Column
    String workingTitle;

    @Column
    private LocalDateTime changeTime;

    @Column
    private String hardwareModelType;

    public RHardwareModel(String id, String workingTitle, String hardwareModelType) {
        super(id);
        this.workingTitle = workingTitle;
        this.hardwareModelType = hardwareModelType;
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

    public String getHardwareModelType() {
        return hardwareModelType;
    }

}
