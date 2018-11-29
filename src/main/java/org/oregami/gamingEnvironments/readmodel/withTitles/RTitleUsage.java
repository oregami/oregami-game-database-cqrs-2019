package org.oregami.gamingEnvironments.readmodel.withTitles;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.model.EntityId;
import org.oregami.common.BaseEntityUUID;
import org.oregami.gamingEnvironments.model.Region;
import org.oregami.gamingEnvironments.model.TitleType;

import javax.persistence.Entity;

/**
 * Created by sebastian on 28.02.17.
 */
@Entity
@NoArgsConstructor
@Getter
public class RTitleUsage extends BaseEntityUUID {

    private Region region;
    private TitleType titleType;

    public RTitleUsage(String id, Region region, TitleType titleType) {
        super(id);
        this.region = region;
        this.titleType = titleType;
    }

}
