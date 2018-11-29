package org.oregami.common;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 *
 */
public class CommonResult<T> {

    private List<CommonError> errors;

    private boolean success;

    private T result;

    public CommonResult(List<CommonError> errors) {
        this.errors = errors;
        this.success = errors==null || errors.isEmpty();
    }

    public List<CommonError> getErrors() {
        return errors;
    }

    public void setErrors(List<CommonError> errors) {
        this.errors = errors;
    }

    public boolean wasSuccessful() {
        return errors != null && errors.size() == 0;
    }

    public boolean hasErrors() {
        return !wasSuccessful();
    }

    public void addMessage(CommonErrorContext context, String message) {
        errors.add(new CommonError(context, message));
    }
    
    public boolean containsError(CommonError searchError) {
    	for (CommonError error : errors) {
			if (error.equals(searchError)) {
				return true;
			}
		}
    	return false;
    }

    public boolean fieldHasError(String fieldname) {
        for (CommonError error : errors) {
            if (error.getContext().getField().equals(fieldname)) {
                return true;
            }
        }
        return false;
    }

    public T get() {
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
