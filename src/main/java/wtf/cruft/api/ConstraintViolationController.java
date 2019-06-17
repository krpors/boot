package wtf.cruft.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@SuppressWarnings("unused")
@ControllerAdvice
public class ConstraintViolationController {

    /**
     * Controller advice for javax.validation's {@link ConstraintViolationException}.
     *
     * @param ex The exception to map.
     * @return The ResponseEntity as a HTTP Bad Request.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(final ConstraintViolationException ex) {
        System.out.println("Handling!");

        ApiError err = new ApiError();
        err.setCode("ConstraintViolation");
        err.setDesc("There was an error somewhere! FIX IT!");
        ex.getConstraintViolations().forEach(violation -> {
            ApiError e = new ApiError();
            e.setTarget(violation.getPropertyPath().toString());
            e.setDesc(String.format("%s: %s. Actual value is: '%s'",
                    violation.getPropertyPath().toString(),
                    violation.getMessage(),
                    violation.getInvalidValue()));
            err.getInnerError().add(e);
        });
        return ResponseEntity.badRequest().body(err);
    }
}
