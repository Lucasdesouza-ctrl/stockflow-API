package br.com.stockFlow.integration.controller;

import br.com.stockFlow.Model.Product;
import br.com.stockFlow.dto.ProductDTO;
import br.com.stockFlow.repository.ProductRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17");

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
    ProductRepository productRepository;

    Product product;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;

        product = new Product();
        product.setName("TestProduct");
        product.setQuantity(10);
        product.setBarcode("12346675421");
        product.setCategory("TestCategory");
        product.setSku("SKU-001");
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Nested
    class CreateProduct {

        @Test
        @DisplayName("Should create product and return its UUID")
        void shouldCreateProductAndReturnUUID() {
            ProductDTO dto = new ProductDTO("TestProduct", "SKU-002", "98459234853298", 2, "TestCategory");

            UUID id = given()
                    .contentType(ContentType.JSON)
                    .body(dto)
                    .post("/product")
                    .then()
                    .statusCode(200)
                    .extract().as(UUID.class);

            assertNotNull(id);
            assertEquals(1, productRepository.count());
        }

        @Test
        @DisplayName("Should return bad request when body is empty")
        void shouldReturnBadRequestWhenBodyIsEmpty() {
            given()
                    .contentType(ContentType.JSON)
                    .body("{}")
                    .post("/product")
                    .then()
                    .statusCode(400);
        }
    }

    @Nested
    class UpdateProduct {

        @Test
        @DisplayName("Should update product successfully")
        void shouldUpdateProductSuccessfully() {
            productRepository.save(product);
            ProductDTO dto = new ProductDTO("UpdatedProduct", "SKU-001", "09949302051", 4, "TestCategory");

            given()
                    .contentType(ContentType.JSON)
                    .body(dto)
                    .put("/product/" + product.getId())
                    .then()
                    .statusCode(200)
                    .body("name", equalTo("UpdatedProduct"));

            Product updated = productRepository.findBySku("SKU-001").orElseThrow();
            assertEquals("UpdatedProduct", updated.getName());
            assertEquals(4, updated.getQuantity());
        }

        @Test
        @DisplayName("Should return not found when updating a nonexistent SKU")
        void shouldReturnNotFoundWhenSkuDoesNotExist() {
            ProductDTO dto = new ProductDTO("UpdatedProduct", "SKU-INEXISTENTE", "09949302051", 4, "TestCategory");

            given()
                    .contentType(ContentType.JSON)
                    .body(dto)
                    .put("/product/" + UUID.randomUUID())
                    .then()
                    .statusCode(404);
        }
    }

    @Nested
    class FindAllProducts {

        @Test
        @DisplayName("Should return all products")
        void shouldReturnAllProducts() {
            productRepository.save(product);

            given()
                    .get("/product")
                    .then()
                    .statusCode(200)
                    .body("size()", org.hamcrest.Matchers.is(1))
                    .body("[0].sku", equalTo("SKU-001"));

            assertEquals(1, productRepository.count());
        }

        @Test
        @DisplayName("Should return empty list when there are no products")
        void shouldReturnEmptyListWhenThereAreNoProducts() {
            given()
                    .get("/product")
                    .then()
                    .statusCode(200)
                    .body("size()", org.hamcrest.Matchers.is(0));
        }
    }

    @Nested
    class FindProductBySku {

        @Test
        @DisplayName("Should return product by SKU")
        void shouldReturnProductBySku() {
            productRepository.save(product);

            given()
                    .get("/product/" + product.getSku())
                    .then()
                    .statusCode(200)
                    .body("sku", equalTo("SKU-001"))
                    .body("name", equalTo("TestProduct"));
        }

        @Test
        @DisplayName("Should return not found when SKU does not exist")
        void shouldReturnNotFoundWhenSkuDoesNotExist() {
            given()
                    .get("/product/SKU-INEXISTENTE")
                    .then()
                    .statusCode(404);
        }
    }

    @Nested
    class DeleteProduct {

        @Test
        @DisplayName("Should delete product by ID")
        void shouldDeleteProductById() {
            productRepository.save(product);

            given()
                    .delete("/product/" + product.getId())
                    .then()
                    .statusCode(200);

            assertEquals(0, productRepository.count());
        }

        @Test
        @DisplayName("Should return not found when product does not exist")
        void shouldReturnNotFoundWhenProductDoesNotExist() {
            given()
                    .delete("/product/" + UUID.randomUUID())
                    .then()
                    .statusCode(404);
        }
    }
}