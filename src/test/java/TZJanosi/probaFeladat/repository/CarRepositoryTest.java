package TZJanosi.probaFeladat.repository;

import TZJanosi.probaFeladat.model.Car;
import TZJanosi.probaFeladat.model.CarCondition;
import TZJanosi.probaFeladat.model.KilometerState;
import org.assertj.core.groups.Tuple;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class CarRepositoryTest {
    @Autowired
    CarRepository carRepository;

    @BeforeEach
    void init(){
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:mariadb://localhost/probaFeladat_test", "probaFeladatUser", "probaFeladatPass")
                .cleanDisabled(false)
                .load();
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void saveCarTest(){
        Car car=new Car();
        car.setCondition(CarCondition.POOR);
        car.setBrand("Suzuki");
        car.setModel("Swift");
        car.setAgeInYears(1);
        carRepository.save(car);
        Car carFromDb=carRepository.findById(car.getId()).get();
        assertThat(carFromDb.getModel()).isEqualTo("Swift");
    }

    @Test
    void findAllTest(){
        Car car=new Car();
        car.setCondition(CarCondition.POOR);
        car.setBrand("Suzuki");
        car.setModel("Swift");
        car.setAgeInYears(1);
        carRepository.save(car);

        List<Car> cars=carRepository.findAll();
        assertThat(cars)
                .hasSize(5)
                .extracting(Car::getModel,Car::getAgeInYears)
                .contains(tuple("Corolla", 25),tuple("Corolla", 15),tuple("X3", 19),tuple("Auris", 5),tuple("Swift", 1));
    }
    @Test
    void findByIdWithKmStatesTest(){
        Car car=new Car();
        car.setCondition(CarCondition.POOR);
        car.setBrand("Suzuki");
        car.setModel("Swift");
        car.setAgeInYears(1);

        car.addKilometerState(15_555);
        carRepository.save(car);

        Car carFromDb=carRepository.findByIdWithKmStates(car.getId()).get();
        KilometerState kilometerState=carFromDb.getKilometerStates().get(0);
        assertThat(kilometerState)
                .hasFieldOrPropertyWithValue("actualValue",15_555)
                .hasFieldOrPropertyWithValue("date",LocalDate.now());
    }
    @Test
    void findAllByCriteriaTest(){
        List<Car> cars;
        cars=carRepository.findAllByCriteria("Toyota",null,null,null,0);
        assertThat(cars)
                .hasSize(3)
                .extracting(Car::getAgeInYears)
                .contains(5,15,25);

        cars=carRepository.findAllByCriteria("Toyota",null,16,null,0);
        assertThat(cars)
                .hasSize(2)
                .extracting(Car::getAgeInYears)
                .contains(5,15);

        cars=carRepository.findAllByCriteria(null,null,null,null,2);
        assertThat(cars)
                .hasSize(3)
                .extracting(Car::getAgeInYears)
                .contains(5,15,19);

        cars=carRepository.findAllByCriteria(null,null,null,null,2);
        assertThat(cars)
                .hasSize(3)
                .extracting(Car::getAgeInYears)
                .contains(5,15,19);

        cars=carRepository.findAllByCriteria(null,null,null,300_000,2);
        System.out.println(cars);
        assertThat(cars)
                .hasSize(3)
                .extracting(Car::getAgeInYears)
                .contains(5,15,19);

        cars=carRepository.findAllByCriteria(null,null,null,220_000,2);
        System.out.println(cars);
        assertThat(cars)
                .hasSize(2)
                .extracting(Car::getAgeInYears)
                .contains(15,19);

        cars=carRepository.findAllByCriteria("BMW",null,null,220_000,2);
        System.out.println(cars);
        assertThat(cars)
                .hasSize(1)
                .extracting(Car::getAgeInYears)
                .contains(19);

    }

}