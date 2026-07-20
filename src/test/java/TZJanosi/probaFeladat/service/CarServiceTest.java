package TZJanosi.probaFeladat.service;

import TZJanosi.probaFeladat.dto.CarDto;
import TZJanosi.probaFeladat.dto.Criteria;
import TZJanosi.probaFeladat.dto.SortDirection;
import TZJanosi.probaFeladat.model.Car;
import TZJanosi.probaFeladat.model.CarCondition;
import TZJanosi.probaFeladat.model.KilometerState;
import TZJanosi.probaFeladat.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {
    private ModelMapper modelMapper;

    @Mock
    private CarRepository repository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        carService = new CarService(modelMapper,repository,null,null); // constructor injection
    }

    @Test
    void findFilteredCars() {
        Car car1=new Car();
        car1.setBrand("Toyota");
        car1.setModel("Corolla");
        car1.setAgeInYears(1);
        car1.setCondition(CarCondition.EXCELLENT);
        car1.setKilometerStates(List.of(new KilometerState(12_500, LocalDate.now())));
        Car car2=new Car();
        car2.setBrand("Suzuki");
        car2.setModel("Swift");
        car2.setAgeInYears(11);
        car2.setCondition(CarCondition.NORMAL);
        car2.setKilometerStates(List.of(new KilometerState(125_000, LocalDate.now())));
        Car car3=new Car();
        car3.setBrand("Kia");
        car3.setModel("Ceed");
        car3.setAgeInYears(17);
        car3.setCondition(CarCondition.POOR);
        car3.setKilometerStates(List.of(new KilometerState(1_000, LocalDate.now().minusMonths(4)),new KilometerState(205_000, LocalDate.now())));
        Car car4=new Car();
        car4.setBrand("Lexus");
        car4.setModel("Lx3");
        car4.setAgeInYears(4);
        car4.setCondition(CarCondition.GOOD);
        car4.setKilometerStates(List.of(new KilometerState(25_000, LocalDate.now())));

        lenient().when(repository.findAllByCriteria(any(),any(),any(),any(),anyInt())).thenReturn(List.of(car1,car2,car3,car4));
        lenient().when(repository.findAll()).thenReturn(List.of(car1,car2,car3,car4));

        List<CarDto> cars;
        cars= carService.findFilteredCars(new Criteria());
        assertThat(cars)
                .hasSize(4)
                .extracting(CarDto::getBrand)
                .containsExactly("Toyota","Lexus","Suzuki","Kia");

        Criteria criteria;
        criteria=new Criteria();
        criteria.setSortDirection(SortDirection.DESC);
        cars= carService.findFilteredCars(criteria);
        assertThat(cars)
                .hasSize(4)
                .extracting(CarDto::getBrand)
                .containsExactly("Kia","Suzuki","Lexus","Toyota");
    }
}