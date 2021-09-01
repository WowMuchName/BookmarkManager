package net.pbforge.bookmarkmanager.advice;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.webflux.advice.ProblemHandling;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@ControllerAdvice
class ExceptionHandling implements ProblemHandling {
}
