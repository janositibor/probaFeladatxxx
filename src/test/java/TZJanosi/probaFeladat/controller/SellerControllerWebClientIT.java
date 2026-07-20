package TZJanosi.probaFeladat.controller;

import TZJanosi.probaFeladat.dto.*;
import TZJanosi.probaFeladat.model.CarCondition;
import TZJanosi.probaFeladat.service.SellerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SellerControllerWebClientIT {
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    SellerService sellerService;

    @BeforeEach
    void init(){
        sellerService.deleteAll();
        webTestClient
                .post()
                .uri("/api/sellers")
                .bodyValue(new CreateSellerCommand("Seftes"))
                .exchange()
                .expectStatus()
                .isCreated();
        webTestClient
                .post()
                .uri("/api/sellers")
                .bodyValue(new CreateSellerCommand("Józsi"))
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void addSellerTest() {
        webTestClient
                .get()
                .uri("api/sellers/all")
                .exchange()
                .expectBodyList(SellerDto.class)
                .hasSize(2)
                .contains(new SellerDto(1L,"Seftes"),
                        new SellerDto(2L,"Józsi"));
    }
    @Test
    void withOutPrefixTest() {
        webTestClient
                .get()
                .uri("api/sellers")
                .exchange()
                .expectBodyList(SellerDto.class)
                .hasSize(2)
                .contains(new SellerDto(1L,"Seftes"),
                        new SellerDto(2L,"Józsi"));
    }
    @Test
    void addSellerWithCarsTest() {
        CreateCarCommand createCarCommand1=new CreateCarCommand("Barkas","Touring",55,CarCondition.GOOD,35400);
        CreateCarCommand createCarCommand2=new CreateCarCommand("Skoda","120",50,CarCondition.NORMAL,135400);

        CreateSellerCommand createSellerCommand=new CreateSellerCommand("AAAAuto",List.of(createCarCommand1,createCarCommand2));

        webTestClient
                .post()
                .uri("/api/sellers")
                .bodyValue(createSellerCommand)
                .exchange()
                .expectStatus()
                .isCreated();

        webTestClient
                .get()
                .uri("api/sellers/all")
                .exchange()
                .expectBodyList(SellerDto.class)
                .hasSize(3)
                .contains(new SellerDto(3L,"AAAAuto"));
    }
    @Test
    void withPrefixTest() {
        webTestClient
                .get()
                .uri("api/sellers?prefix=Jó")
                .exchange()
                .expectBodyList(SellerDto.class)
                .hasSize(1)
                .contains(new SellerDto(2L,"Józsi"));
    }
    @Test
    void withPrefixCheckCarsTest() {
        CreateCarCommand createCarCommand1=new CreateCarCommand("Barkas","Touring",55,CarCondition.GOOD,35400);
        CreateCarCommand createCarCommand2=new CreateCarCommand("Skoda","120",50,CarCondition.NORMAL,135400);

        CreateSellerCommand createSellerCommand=new CreateSellerCommand("AAAAuto",List.of(createCarCommand1,createCarCommand2));
        webTestClient
                .post()
                .uri("/api/sellers")
                .bodyValue(createSellerCommand)
                .exchange()
                .expectStatus()
                .isCreated();

        EntityExchangeResult<List<SellerDto>> response=webTestClient
                .get()
                .uri("api/sellers?prefix=AAA")
                .exchange()
                .expectBodyList(SellerDto.class)
                .hasSize(1)
                .contains(new SellerDto(3L,"AAAAuto"))
                .returnResult();

        List<SellerDto> result=response.getResponseBody();
        System.out.println(result.get(0).getCars());
        assertThat(result.get(0).getCars())
                .hasSize(2)
                .extracting(c->c.getKilometerStates().get(0).getActualValue())
                .contains(35400,135400);
    }
    @Test
    void findSellerById(){
        CreateCarCommand createCarCommand1=new CreateCarCommand("Barkas","Touring",55,CarCondition.GOOD,35400);
        CreateCarCommand createCarCommand2=new CreateCarCommand("Skoda","120",50,CarCondition.NORMAL,135400);

        CreateSellerCommand createSellerCommand=new CreateSellerCommand("AAAAuto",List.of(createCarCommand1,createCarCommand2));
        webTestClient
                .post()
                .uri("/api/sellers")
                .bodyValue(createSellerCommand)
                .exchange()
                .expectStatus()
                .isCreated();

        EntityExchangeResult<SellerDto> response=webTestClient
                .get()
                .uri("api/sellers/3")
                .exchange()
                .expectBody(SellerDto.class)
                .returnResult();

        SellerDto result=response.getResponseBody();
        assertThat(result.getName())
                .isEqualTo("AAAAuto");
        assertThat(result.getCars())
                .hasSize(2)
                .extracting(c->c.getKilometerStates().get(0).getActualValue())
                .contains(35400,135400);
    }
    @Test
    void addCarToSeller(){
        CreateCarCommand createCarCommand1=new CreateCarCommand("Barkas","Touring",55,CarCondition.GOOD,35400);
        CreateCarCommand createCarCommand2=new CreateCarCommand("Skoda","120",50,CarCondition.NORMAL,135400);

        CreateSellerCommand createSellerCommand=new CreateSellerCommand("AAAAuto",List.of(createCarCommand1,createCarCommand2));
        webTestClient
                .post()
                .uri("/api/sellers")
                .bodyValue(createSellerCommand)
                .exchange()
                .expectStatus()
                .isCreated();

        EntityExchangeResult<SellerDto> response=webTestClient
                .get()
                .uri("api/sellers/3")
                .exchange()
                .expectBody(SellerDto.class)
                .returnResult();

        SellerDto result=response.getResponseBody();
        assertThat(result.getName())
                .isEqualTo("AAAAuto");
        assertThat(result.getCars())
                .hasSize(2)
                .extracting(c->c.getKilometerStates().get(0).getActualValue())
                .contains(35400,135400);

        EntityExchangeResult<SellerDto> response2=webTestClient
                .post()
                .uri("api/sellers/3")
                .bodyValue(new CreateCarCommand("Trabant","601",45,CarCondition.NORMAL,535400))
                .exchange()
                .expectBody(SellerDto.class)
                .returnResult();

        SellerDto result2=response2.getResponseBody();
        assertThat(result2.getName())
                .isEqualTo("AAAAuto");
        assertThat(result2.getCars())
                .hasSize(3)
                .extracting(c->c.getKilometerStates().get(0).getActualValue())
                .contains(35400,135400,535400);


    }

    @Test
    void updateSeller(){
        webTestClient
                .put()
                .uri("api/sellers/1")
                .bodyValue(new CreateSellerCommand("CsontiCar"))
                .exchange()
                .expectBody(SellerDto.class)
                .value(t->{
                    assertThat(t.getName()).isEqualTo("CsontiCar");
                });
    }
    @Test
    void deleteTest(){
        webTestClient
                .get()
                .uri("api/sellers/all")
                .exchange()
                .expectBodyList(SellerDto.class)
                .hasSize(2)
                .contains(new SellerDto(1L,"Seftes"),
                        new SellerDto(2L,"Józsi"));
        webTestClient
                .delete()
                .uri("api/sellers/1")
                .exchange();
        webTestClient
                .get()
                .uri("api/sellers/all")
                .exchange()
                .expectBodyList(SellerDto.class)
                .hasSize(1)
                .contains(new SellerDto(2L,"Józsi"));

    }
}