package ar.edu.itba.paw.webapp.dto;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConstraintViolationDTO {
    private ErrorDTO[] errors;

    public ConstraintViolationDTO() {
        // Empty constructor needed by JAX-RS
    }

    public <T> ConstraintViolationDTO(Set<ConstraintViolation<T>> violations) {
        List<ErrorDTO> errorsList = new ArrayList<>(violations.size());
        this.errors =  new ErrorDTO[violations.size()];
        for(ConstraintViolation<T> violation : violations) {
            errorsList.add(new ErrorDTO(violation.getMessage(), violation.getPropertyPath().toString()));
        }
        errorsList.toArray(errors);
    }

    public ErrorDTO[] getErrors() {
        return errors;
    }

    public void setErrors(ErrorDTO[] errors) {
        this.errors = errors;
    }
}
