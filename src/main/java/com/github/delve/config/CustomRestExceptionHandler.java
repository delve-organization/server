package com.github.delve.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final List<FieldErrorMessage> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            final Object[] args = error.getArguments() != null ? error.getArguments() : new Object[]{};
            final Object[] relevantArgs = args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : new Object[]{};

            errors.add(new FieldErrorMessage(error.getField(), error.getDefaultMessage(), relevantArgs));
        }

        final ApiError apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.status, request);
    }

    private class ApiError {

        private final HttpStatus status;
        private final List<FieldErrorMessage> errors;

        private ApiError(final HttpStatus status, final List<FieldErrorMessage> errors) {
            super();
            this.status = status;
            this.errors = errors;
        }

        public HttpStatus getStatus() {
            return status;
        }

        public List<FieldErrorMessage> getErrors() {
            return errors;
        }
    }

    private class FieldErrorMessage {

        private final String field;
        private final String resourceKey;
        private final Object[] arguments;

        private FieldErrorMessage(final String field, final String resourceKey, final Object[] arguments) {
            this.field = field;
            this.resourceKey = resourceKey;
            this.arguments = arguments;
        }

        public String getField() {
            return field;
        }

        public String getResourceKey() {
            return resourceKey;
        }

        public Object[] getArguments() {
            return arguments;
        }
    }
}
