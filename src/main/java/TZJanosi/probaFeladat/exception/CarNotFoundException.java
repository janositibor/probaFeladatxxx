package TZJanosi.probaFeladat.exception;
//
//import org.zalando.problem.AbstractThrowableProblem;
//import org.zalando.problem.Status;
//
//import java.net.URI;
//
//public class CarNotFoundException extends AbstractThrowableProblem {
//    public CarNotFoundException(Long id){
//        super(URI.create("/api/cars/NOT_FOUND"),
//                "Not found",
//                Status.NOT_FOUND,
//                String.format("Car with id: %d, not found",id));
//
//    }
//}

import org.springframework.http.HttpStatus;

import java.net.URI;

public class CarNotFoundException extends AbstractExceptionForCars {
    public CarNotFoundException(Long id) {
        super(
                HttpStatus.NOT_FOUND,
                URI.create("/api/cars/NOT_FOUND"),
                "Not found",
                String.format("Car with id: %d, not found",id)
        );
    }
}
