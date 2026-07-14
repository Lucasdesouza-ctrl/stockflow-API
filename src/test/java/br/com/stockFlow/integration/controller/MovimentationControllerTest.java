package br.com.stockFlow.integration.controller;

import br.com.stockFlow.Model.Movimentation;
import br.com.stockFlow.Model.Product;
import br.com.stockFlow.Model.User;
import br.com.stockFlow.dto.PostMovimentationDTO;
import br.com.stockFlow.enums.RolesEnum;
import br.com.stockFlow.repository.MovimentationRepository;
import br.com.stockFlow.repository.ProductRepository;
import br.com.stockFlow.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDateTime;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovimentationControllerTest {

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:17"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    MovimentationRepository movimentationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    User user;
    Product product;
    PostMovimentationDTO dto;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;

        user = new User();
        user.setName("TestName");
        user.setPassword("TestPassword");
        user.setRole(RolesEnum.ROLE_USER);

        product = new Product();
        product.setName("TestProduct");
        product.setQuantity(1);
        product.setBarcode("12346675421");
        product.setCategory("TestCategory");
        product.setSku("1234351453");


    }

    @AfterEach
    void tearDown() {
        movimentationRepository.deleteAll();
    }

    @Nested
    class insertMovimentation {

        @DisplayName("Should create movimentation register")
        @Test
        void shouldCreateMovimentationRegister() {

            userRepository.save(user);
            productRepository.save(product);

            dto = new PostMovimentationDTO(
                    1,
                    LocalDateTime.now(),
                    product.getId(),
                    user.getId()
            );


            given()
                    .contentType(ContentType.JSON)
                    .body(dto)
                    .post("/mov")
                    .then()
                    .statusCode(201);

            assertEquals(1, movimentationRepository.count());
        }

        @Test
        @DisplayName("Should return bad request when stock is insufficient")
        void shouldReturnBadRequestWhenStockIsInsufficient() {
            userRepository.save(user);
            productRepository.save(product);

            dto = new PostMovimentationDTO(
                    -10,
                    LocalDateTime.now(),
                    product.getId(),
                    user.getId()
            );

            given()
                    .contentType(ContentType.JSON)
                    .body(dto)
                    .post("/mov")
                    .then()
                    .statusCode(400);
        }
    }

}