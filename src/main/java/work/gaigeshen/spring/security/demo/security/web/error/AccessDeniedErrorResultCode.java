package work.gaigeshen.spring.security.demo.security.web.error;

import work.gaigeshen.spring.security.demo.commons.web.ResultCode;

/**
 * @author gaigeshen
 */
public enum AccessDeniedErrorResultCode implements ResultCode {

    ACCESS_DENIED(7000, "Access Denied");

    private final int code;

    private final String message;

    AccessDeniedErrorResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
