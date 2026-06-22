package br.com.stockFlow.integration.controller;


import br.com.stockFlow.Model.User;
import br.com.stockFlow.dto.UserDTO;
import br.com.stockFlow.enums.Constants;
import br.com.stockFlow.enums.RolesEnum;
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

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

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
    UserRepository userRepository;

    User user;

    User user2;

    UserDTO dto;

    UUID uuid;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;

        user = new User();
        user.setName("TestName");
        user.setPassword("TestPassword");
        user.setRole(RolesEnum.ROLE_USER);

        user2 = new User();
        user2.setName("TestName2");
        user2.setPassword("456");
        user2.setRole(RolesEnum.ROLE_ADMIN);

        dto = new UserDTO(
                "TestDto",
                "1234",
                RolesEnum.ROLE_USER
        );

        uuid = UUID.randomUUID();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Nested
    class CreateUser {

        @Test
        @DisplayName("Should create user with success")
        void shouldCreateUserWithSuccess() {


            given()
                    .contentType(ContentType.JSON)
                    .body(dto)
                    .when()
                    .post("/user")
                    .then()
                    .statusCode(201);

            assertEquals(1, userRepository.count());
            User savedUser = userRepository.findAll().get(0);
            assertEquals("TestDto", savedUser.getName());
            assertEquals("1234", savedUser.getPassword());
            assertEquals(RolesEnum.ROLE_USER, savedUser.getRole());
        }

        @Test
        @DisplayName("Should return bad request when request is empty")
        void shouldReturnBadRequestWhenRequestIsEmpty() {
            UserDTO dto = UserDTO.builder().build();

            given()
                    .contentType(ContentType.JSON)
                    .body(dto)
                    .when()
                    .post("/user")
                    .then()
                    .statusCode(400)
                    .body("password", equalTo("Password is required"))
                    .body("name", equalTo("Name is required"))
                    .body("role", equalTo("Role is required"));
        }
    }

    @Nested
    class FindAll{

        @Test
        @DisplayName("Should return all users")
        void shouldReturnAllUsers() {

            userRepository.save(user);
            userRepository.save(user2);

            given()
                    .contentType(ContentType.JSON)
                    .when()
                    .get("/user")
                    .then()
                    .statusCode(200)
                    .body("size()", equalTo(2))
                    .body("[0].name", equalTo("TestName"))
                    .body("[1].name", equalTo("TestName2"));
        }
    }

    @Nested
    class FindById{
        @Test
        @DisplayName("Should return user by id")
        void shouldReturnUserById() {
            User savedUser = userRepository.save(user);

            given()
                    .contentType(ContentType.JSON)
                    .when()
                    .get("/user/{id}", savedUser.getId())
                    .then()
                    .statusCode(200)
                    .body("name", equalTo("TestName"))
                    .body("password", equalTo("TestPassword"))
                    .body("role", equalTo("ROLE_USER"));

            assertEquals(1, userRepository.count());
        }

        @Test
        @DisplayName("Should return bad request when user not found")
        void shouldReturnBadRequestWhenUserNotFound() {

            given()
                    .contentType(ContentType.JSON)
                    .get("/user/{id}", uuid)
                    .then()
                    .statusCode(404)
                    .body("message", equalTo(Constants.MSG_NOT_FOUND.getValue()));
        }
    }

    @Nested
    class DeleteUser{

        @Test
        @DisplayName("Should delete user with success")
        void shouldDeleteUserWithSuccess() {
           User savedUser = userRepository.save(user);

            given()
                    .contentType(ContentType.JSON)
                    .delete("/user/{id}", savedUser.getId())
                    .then()
                    .statusCode(204);

        }

        @Test
        @DisplayName("Should return bad request when user not found")
        void shouldReturnBadRequestWhenUserNotFound() {
            userRepository.save(user);
            user2.setId(UUID.randomUUID());

            given()
                    .contentType(ContentType.JSON)
                    .delete("/user/{id}", uuid)
                    .then()
                    .statusCode(404)
                    .body("message", equalTo(Constants.MSG_NOT_FOUND.getValue()));

        }
    }
}

