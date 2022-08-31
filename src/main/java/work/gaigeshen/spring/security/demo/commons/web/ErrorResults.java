package work.gaigeshen.spring.security.demo.commons.web;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.PropertyBatchUpdateException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import work.gaigeshen.spring.security.demo.security.PasswordInvalidException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.Set;

/**
 * @author gaigeshen
 */
public abstract class ErrorResults {

    private ErrorResults() { }

    public static Result<?> createResult(Throwable exception, int httpStatus) {
        if (Objects.isNull(exception)) {
            switch (httpStatus) {
                case 401:
                    return Results.create(HttpStatusErrorResultCode.UNAUTHORIZED);
                case 403:
                    return Results.create(HttpStatusErrorResultCode.FORBIDDEN);
            }
            return Results.create(HttpStatusErrorResultCode.INTERNAL_SERVER_ERROR);
        }
        return createResult(exception);
    }

    public static Result<?> createResult(Throwable ex) {
        if (ex instanceof AuthenticationException) {
            return createResult((AuthenticationException) ex);
        }
        else if (ex instanceof HttpRequestMethodNotSupportedException) {
            return Results.create(HttpStatusErrorResultCode.METHOD_NOT_ALLOWED);
        }
        else if (ex instanceof HttpMessageNotReadableException) {
            return Results.create(HttpStatusErrorResultCode.UNSUPPORTED_MEDIA_TYPE);
        }
        else if (ex instanceof HttpMediaTypeNotSupportedException) {
            return Results.create(HttpStatusErrorResultCode.UNSUPPORTED_MEDIA_TYPE);
        }
        else if (ex instanceof AccessDeniedException) {
            return Results.create(HttpStatusErrorResultCode.UNAUTHORIZED);
        }
        else if (ex instanceof MissingServletRequestParameterException) {
            return Results.create(HttpStatusErrorResultCode.BAD_REQUEST);
        }
        else if (ex instanceof MissingServletRequestPartException) {
            return Results.create(HttpStatusErrorResultCode.BAD_REQUEST);
        }
        else if (ex instanceof MethodArgumentTypeMismatchException) {
            return Results.create(HttpStatusErrorResultCode.BAD_REQUEST);
        }
        else if (ex instanceof PropertyBatchUpdateException) {
            return Results.create(HttpStatusErrorResultCode.BAD_REQUEST);
        }
        else if (ex instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
            return Results.create(HttpStatusErrorResultCode.BAD_REQUEST, bindingResultDetail(bindingResult));
        }
        else if (ex instanceof BindException) {
            BindingResult bindingResult = ((BindException) ex).getBindingResult();
            return Results.create(HttpStatusErrorResultCode.BAD_REQUEST, bindingResultDetail(bindingResult));
        }
        else if (ex instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) ex).getConstraintViolations();
            return Results.create(HttpStatusErrorResultCode.BAD_REQUEST, constraintViolationsDetail(violations));
        }
        return Results.create(HttpStatusErrorResultCode.INTERNAL_SERVER_ERROR);
    }

    public static Result<?> createResult(AuthenticationException ex) {
        if (ex instanceof UsernameNotFoundException) {
            return Results.create(AuthenticationErrorResultCode.AUTHENTICATION_TOKEN_INVALID);
        }
        else if (ex instanceof PasswordInvalidException) {
            return Results.create(AuthenticationErrorResultCode.AUTHENTICATION_TOKEN_INVALID);
        }
        else if (ex instanceof DisabledException) {
            return Results.create(AuthenticationErrorResultCode.ACCOUNT_DISABLED);
        }
        else if (ex instanceof LockedException) {
            return Results.create(AuthenticationErrorResultCode.ACCOUNT_LOCKED);
        }
        else if (ex instanceof AccountExpiredException) {
            return Results.create(AuthenticationErrorResultCode.ACCOUNT_EXPIRED);
        }
        return Results.create(AuthenticationErrorResultCode.AUTHENTICATE_FAILED);
    }

    private static ValidationError bindingResultDetail(BindingResult bindingResult) {
        ValidationError validationError = new ValidationError();
        for (FieldError error : bindingResult.getFieldErrors()) {
            validationError.addViolation(error.getField(), error.getDefaultMessage());
        }
        return validationError;
    }

    private static ValidationError constraintViolationsDetail(Set<ConstraintViolation<?>> violations) {
        ValidationError validationError = new ValidationError();
        for (ConstraintViolation<?> violation : violations) {
            PathImpl propertyPath = (PathImpl) violation.getPropertyPath();
            validationError.addViolation(propertyPath.getLeafNode().getName(), violation.getMessage());
        }
        return validationError;
    }
}
