package TZJanosi.probaFeladat.exception;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerForCars {

    @ExceptionHandler(AbstractExceptionForCars.class)
    public ProblemDetail handle(AbstractExceptionForCars actualException) {

        ProblemDetail problem =
                ProblemDetail.forStatus(actualException.getStatus());

        problem.setTitle(actualException.getTitle());
        problem.setType(actualException.getType());
        problem.setDetail(actualException.getMessage());

        return problem;
    }
}
