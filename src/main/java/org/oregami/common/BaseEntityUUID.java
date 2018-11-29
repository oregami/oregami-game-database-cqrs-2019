/*******************************************************************************
 * Copyright (C) 2012  Oregami.org, Germany http://www.oregami.org
 *
 * 	This program is free software: you can redistribute it and/or modify
 * 	it under the terms of version 3 or any later version of the
 * 	GNU Affero General Public License as published by the Free Software
 * 	Foundation.
 *
 * 	This program is distributed in the hope that it will be useful,
 * 	but WITHOUT ANY WARRANTY; without even the implied warranty of
 * 	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * 	GNU Affero General Public License for more details.
 *
 * 	You should have received a copy of the GNU Affero General Public License
 * 	along with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package org.oregami.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;


@MappedSuperclass
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "changeTime"}) //see http://stackoverflow.com/questions/24994440/no-serializer-found-for-class-org-hibernate-proxy-pojo-javassist-javassist
public abstract class BaseEntityUUID implements Serializable
{
    /**
	 *
	 */
	private static final long serialVersionUID = 8608953068007538072L;

	@Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id = null;

    public BaseEntityUUID(String id) {
        this.id = id;
    }

    public BaseEntityUUID() {

    }

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof BaseEntityUUID))
        {
            return false;
        }
        final BaseEntityUUID other = (BaseEntityUUID) obj;
        if (this.id != null && other.id != null)
        {
            return this.getClass().equals(other.getClass()) && this.id == other.id;
        }
        return false;
    }

    public String getId()
    {
        return this.id;
    }

	@Override
	public String toString() {
        return ToStringBuilder.reflectionToString(this, RecursiveToStringStyle.JSON_STYLE);
    }



}
