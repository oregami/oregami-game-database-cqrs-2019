package org.oregami.gamingEnvironments.readmodel.withTitles;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.oregami.common.BaseEntityUUID;
import org.springframework.core.OrderComparator;

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
public class RGamingEnvironment extends BaseEntityUUID {

    @Column
    String workingTitle;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch= FetchType.EAGER)
    @JoinColumn
    @OrderBy(value = "id")
    Set<RTitle> gametitles = new TreeSet<>(new OrderComparator());

    @Column
    private LocalDateTime changeTime;

    public RGamingEnvironment(String id, String workingTitle) {
        super(id);
        this.workingTitle = workingTitle;
    }

    public void setChangeTime(LocalDateTime changeTime) {
        this.changeTime = changeTime;
    }

    public void addTitle(RTitle t) {
        gametitles.add(t);
    }

    public Set<RTitle> getGametitles() {
        return gametitles;
    }
}
