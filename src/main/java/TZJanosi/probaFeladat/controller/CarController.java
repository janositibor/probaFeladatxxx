package TZJanosi.probaFeladat.controller;

import TZJanosi.probaFeladat.dto.CarDto;
import TZJanosi.probaFeladat.dto.CreateCarCommand;
import TZJanosi.probaFeladat.dto.Criteria;
import TZJanosi.probaFeladat.service.CarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    private CarService service;

    public CarController(CarService service) {
        this.service = service;
    }



    @GetMapping("/brands")
    public List<String> getBrands(){
        return service.getBrands();
    }

    @GetMapping("/all")
    public List<CarDto> findAllCars(){
        return service.findAllCars();
    }
    @GetMapping
    public List<CarDto> findFilteredCars(Criteria criteria){
        return service.findFilteredCars(criteria);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDto addNewCar(@Valid @RequestBody CreateCarCommand command){
        return service.addNewCar(command);
    }
    @GetMapping("/{id}")
    public CarDto findCarById(@PathVariable("id") long id){
        return service.findCarDtoById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCarById(@PathVariable("id") long id){
        service.deleteCarById(id);
    }
    @PostMapping("/{id}/kilometerstates")
    public CarDto addKmState(@PathVariable("id") long id, @RequestBody int km){
        return service.addKmState(id,km);
    }
}
