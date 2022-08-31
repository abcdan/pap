package nl.duckstudios.pintandpillage.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class AttackingConditionsNotMetException extends RuntimeException {
    public AttackingConditionsNotMetException(String message) {
        super(message);
    }
}
