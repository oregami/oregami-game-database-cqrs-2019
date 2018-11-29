package org.oregami.common;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class that represents errors that occur during service layer calls. 
 * The messageName is a internal string used for translations.
 * The context defines in which field the error occurred (e.g. the web form field name).
 * 
 */
public class CommonError {

    private final String messageName;
    
	private CommonErrorContext context;

    public CommonError(CommonErrorContext context, String messageName) {
    	this.context = context;
        this.messageName = messageName;
    }    
    
	public CommonErrorContext getContext() {
		return context;
	}

	public void setContext(CommonErrorContext context) {
		this.context = context;
	}
	
    public String getMessageName() {
		return messageName;
	}


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
