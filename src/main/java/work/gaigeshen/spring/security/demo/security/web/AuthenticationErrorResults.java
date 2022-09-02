package work.gaigeshen.spring.security.demo.security.web;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import work.gaigeshen.spring.security.demo.commons.web.Result;
import work.gaigeshen.spring.security.demo.commons.web.Results;
import work.gaigeshen.spring.security.demo.security.AuthenticationTokenMissingException;
import work.gaigeshen.spring.security.demo.security.AuthorizationNotFoundException;

/**
 *
 * @author gaigeshen
 */
public abstract class AuthenticationErrorResults {

    private AuthenticationErrorResults() { }

    public static Result<?> createResult(AuthenticationException ex) {
        if (ex instanceof AuthenticationTokenMissingException) {
            return Results.create(AuthenticationErrorResultCode.AUTHENTICATION_TOKEN_INVALID);
        }
        if (ex instanceof AuthorizationNotFoundException) {
            return Results.create(AuthenticationErrorResultCode.AUTHENTICATION_TOKEN_INVALID);
        }
        if (ex instanceof DisabledException) {
            return Results.create(AuthenticationErrorResultCode.ACCOUNT_DISABLED);
        }
        if (ex instanceof LockedException) {
            return Results.create(AuthenticationErrorResultCode.ACCOUNT_LOCKED);
        }
        if (ex instanceof AccountExpiredException) {
            return Results.create(AuthenticationErrorResultCode.ACCOUNT_EXPIRED);
        }
        return Results.create(AuthenticationErrorResultCode.AUTHENTICATE_FAILED);
    }
}
