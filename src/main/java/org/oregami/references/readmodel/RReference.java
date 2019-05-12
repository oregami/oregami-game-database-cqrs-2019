package org.oregami.references.readmodel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.oregami.common.BaseEntityUUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class RReference extends BaseEntityUUID {

    @Column
    String referenceType;

    @Column
    String url;

    @Column
    String description;

    @Column
    private LocalDateTime changeTime;

    @ElementCollection(fetch= FetchType.EAGER)
    private List<String> eventIdList = new ArrayList<>();


    public RReference(String id, String referenceType) {
        super(id);
        this.referenceType = referenceType;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public void setChangeTime(LocalDateTime changeTime) {
        this.changeTime = changeTime;
    }

    public LocalDateTime getChangeTime() {
        return changeTime;
    }

    public void addEventId(String eventId) {
        eventIdList.add(eventId);
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
