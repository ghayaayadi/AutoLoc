package tn.esprit.autoloc.web.exception;

import java.time.LocalDateTime;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

/** Format unique de toutes les reponses d'erreur de l'API. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> fieldErrors) {

    public static ApiError of(HttpStatus status, String message, String path) {
        return new ApiError(LocalDateTime.now(), status.value(), status.getReasonPhrase(), message, path, null);
    }

    public static ApiError of(HttpStatus status, String message, String path, Map<String, String> fieldErrors) {
        return new ApiError(LocalDateTime.now(), status.value(), status.getReasonPhrase(), message, path, fieldErrors);
    }
}
