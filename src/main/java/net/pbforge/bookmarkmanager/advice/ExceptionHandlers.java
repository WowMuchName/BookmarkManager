package net.pbforge.bookmarkmanager.advice;

import lombok.SneakyThrows;
import org.springframework.core.codec.DecodingException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.StatusType;

import java.net.URI;

import static org.zalando.problem.Status.BAD_REQUEST;
import static org.zalando.problem.Status.CONFLICT;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(value = DecodingException.class)
    public ResponseEntity<Problem> jacksonError(DecodingException exception) {
        String message = exception.getMessage();
        if (message != null) {
            // Make the verbose message from jackson a bit better
            var betterMessage = message.substring(message.indexOf(':')+1, message.indexOf(';')-1).trim();
            if (!betterMessage.isBlank()) {
                return makeProblem("contraint-violation", BAD_REQUEST, betterMessage);
            }
        }
        return makeProblem("contraint-violation", BAD_REQUEST, message);
    }

    @ExceptionHandler(value = DuplicateKeyException.class)
    public ResponseEntity<Problem> duplicateKey(DuplicateKeyException exception) {
        // Note: We can extract the name of the duplicate key field. But that will be mongo specific
        // and will stop working when we use another reactive-repo like cassandra.
        return makeProblem("duplicate-key", CONFLICT, "Duplicate key");
    }

    @SneakyThrows
    private ResponseEntity<Problem> makeProblem(
            String title,
            StatusType status,
            String message) {
        return ResponseEntity.status(status.getStatusCode())
                .contentType(MediaType.valueOf("application/problem+json"))
                .body(Problem.builder()
                .withType(new URI("urn::net.pbforge.bookmark-manager." + title))
                .withDetail(message)
                .withTitle(title)
                .withStatus(status)
                .build());
    }
}
