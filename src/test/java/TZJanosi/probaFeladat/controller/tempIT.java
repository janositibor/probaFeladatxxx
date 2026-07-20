package TZJanosi.probaFeladat.controller;

import TZJanosi.probaFeladat.dto.CreateCarCommand;
import TZJanosi.probaFeladat.model.CarCondition;
import TZJanosi.probaFeladat.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class tempIT {
    @Autowired
    CarService carService;

    @Autowired
    WebTestClient webTestClient;
    @Test
    void insertCarTest(){

        carService.deleteAllCar();
        CreateCarCommand cc=new CreateCarCommand("Toyota","Corolla",25, CarCondition.POOR,295000);
        webTestClient
                .post()
                .uri("/api/cars")
                .bodyValue(cc)
                .exchange()
                .expectStatus()
                .isCreated();
    }
}
