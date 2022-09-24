package work.gaigeshen.spring.security.demo.commons.web;

import work.gaigeshen.spring.security.demo.commons.exception.BusinessErrorException;

import static work.gaigeshen.spring.security.demo.commons.web.BusinessErrorResultCode.BUSINESS_ERROR;

/**
 *
 * @author gaigeshen
 */
public abstract class BusinessErrorResults {

    private BusinessErrorResults() { }

    public static Result<?> createResult(BusinessErrorException ex) {
        return Results.create(DefaultResultCode.create(BUSINESS_ERROR, ex.getMessage()), ex.getData());
    }
}
