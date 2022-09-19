package work.gaigeshen.spring.security.demo.commons.web;

import work.gaigeshen.spring.security.demo.commons.exception.BusinessErrorException;

/**
 *
 * @author gaigeshen
 */
public abstract class BusinessErrorResults {

    private BusinessErrorResults() { }

    public static Result<?> createResult(BusinessErrorException ex) {
        return Results.create(DefaultResultCode.create(BusinessErrorResultCode.BUSINESS_ERROR, ex.getMessage()), ex.getData());
    }
}
