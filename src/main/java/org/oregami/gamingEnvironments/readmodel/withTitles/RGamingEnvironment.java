package org.oregami.gamingEnvironments.readmodel.withTitles;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.oregami.common.BaseEntityUUID;
import org.springframework.core.OrderComparator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Set;
import java.util.TreeSet;

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
}
