package TZJanosi.probaFeladat.exception;

//import org.zalando.problem.AbstractThrowableProblem;
//import org.zalando.problem.Status;
//
//import java.net.URI;
//
//public class KmStateNotValidException  extends AbstractThrowableProblem {
//    public KmStateNotValidException(long km){
//        super(URI.create("/api/cars/KM_NOT_VALID"),
//                "Km not valid",
//                Status.BAD_REQUEST,
//                String.format("Unexpected actual value of km counter! %d km",km));
//
//    }
//}

import org.springframework.http.HttpStatus;

import java.net.URI;

public class KmStateNotValidException  extends AbstractExceptionForCars{
    public KmStateNotValidException(long km) {
        super(
                HttpStatus.BAD_REQUEST,
                URI.create("/api/cars/KM_NOT_VALID"),
                "Km not valid",
                String.format("Unexpected actual value of km counter! %d km",km)
        );
    }
}