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

    public RHardwareModel(String id, String workingTitle) {
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
