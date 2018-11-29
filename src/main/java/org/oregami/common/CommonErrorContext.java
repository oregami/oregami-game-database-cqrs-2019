package org.oregami.common;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Context af this error.
 * e.g. the field name of the web form
 */
public class CommonErrorContext {

	/**
	 * Field name in web form e.g. "user.username"
	 */
	private String field;

    /**
     * The id of the object (if needed)
     */
    private String id;

	public CommonErrorContext(String field) {
		this.field = field;
	}

    public CommonErrorContext(String field, String id) {
        this.field = field;
        this.id = id;
    }
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
