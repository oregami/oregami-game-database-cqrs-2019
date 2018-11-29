package org.oregami.common;

public class ValidationException extends Exception {

    private final CommonResult<?> result;

    public ValidationException(CommonResult<?> result) {
        this.result = result;
    }


    public CommonResult<?> getResult() {
        return result;
    }

}
