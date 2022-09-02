package work.gaigeshen.spring.security.demo.commons.web;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.PropertyBatchUpdateException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @author gaigeshen
 */
public abstract class ErrorResults {

    private ErrorResults() { }

    public static Result<?> createResult(Throwable ex, int httpStatus) {
        switch (httpStatus) {
            case 401:
                return Results.create(HttpStatusErrorResultCode.UNAUTHORIZED);
            case 402:
                return Results.create(HttpStatusErrorResultCode.PAYMENT_REQUIRED);
            case 403:
                return Results.create(HttpStatusErrorResultCode.FORBIDDEN);
            case 404:
                return Results.create(HttpStatusErrorResultCode.NOT_FOUND);
            case 405:
                return Results.create(HttpStatusErrorResultCode.METHOD_NOT_ALLOWED);
            case 406:
                return Results.create(HttpStatusErrorResultCode.NOT_ACCEPTABLE);
            case 415:
                return Results.create(HttpStatusErrorResultCode.UNSUPPORTED_MEDIA_TYPE);
            case 501:
                return Results.create(HttpStatusErrorResultCode.NOT_IMPLEMENTED);
            case 502:
                return Results.create(HttpStatusErrorResultCode.BAD_GATEWAY);
            case 503:
                return Results.create(HttpStatusErrorResultCode.SERVICE_UNAVAILABLE);
        }
        return createResult(ex);
    }

    public static Result<?> createResult(Throwable ex) {
        if (ex instanceof MissingServletRequestParameterException
            || ex instanceof MissingServletRequestPartException
            || ex instanceof MethodArgumentTypeMismatchException
            || ex instanceof PropertyBatchUpdateException) {
            return Results.create(HttpStatusErrorResultCode.BAD_REQUEST);
        }
        if (ex instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
            return Results.create(HttpStatusErrorResultCode.BAD_REQUEST, bindingResultDetail(bindingResult));
        }
        if (ex instanceof BindException) {
            BindingResult bindingResult = ((BindException) ex).getBindingResult();
            return Results.create(HttpStatusErrorResultCode.BAD_REQUEST, bindingResultDetail(bindingResult));
        }
        if (ex instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) ex).getConstraintViolations();
            return Results.create(HttpStatusErrorResultCode.BAD_REQUEST, constraintViolationsDetail(violations));
        }
        return Results.create(HttpStatusErrorResultCode.INTERNAL_SERVER_ERROR);
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
