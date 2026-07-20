package TZJanosi.probaFeladat.exception;

import org.springframework.http.HttpStatus;

import java.net.URI;

public class AbstractExceptionForCars extends RuntimeException{
    private final HttpStatus status;
    private final URI type;
    private final String title;

    protected AbstractExceptionForCars(HttpStatus status,
                           URI type,
                           String title,
                           String detail) {
        super(detail);
        this.status = status;
        this.type = type;
        this.title = title;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public URI getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }
}
