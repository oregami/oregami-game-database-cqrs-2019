package org.oregami.gamingEnvironments.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.model.EntityId;

/**
 * Created by sebastian on 28.02.17.
 */
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class TitleUsage {

    @EntityId
    private String id;

    private Region region;

    public TitleUsage(String id, Region region) {
        this.id = id;
        this.region = region;
    }

}
