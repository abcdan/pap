package nl.duckstudios.pintandpillage.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class ResearchConditionsNotMetException extends RuntimeException {
    public ResearchConditionsNotMetException(String message) {
        super(message);
    }
}
