package br.com.stockFlow.unit.service;

import br.com.stockFlow.exception.NotFoundException;
import br.com.stockFlow.Model.Product;
import br.com.stockFlow.enums.Constants;
import br.com.stockFlow.repository.ProductRepository;
import br.com.stockFlow.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    Product product;

    @BeforeEach
    void setUp() {
        product = new Product(
                UUID.randomUUID(),
                "Notebook",
                "SKU123",
                "123456789",
                10,
                "Eletronicos"
        );
    }

    @Nested
    class InsertProduct {

        @Test
        @DisplayName("Should insert product with success")
        void shouldInsertProductWithSuccess() {

            when(productRepository.save(any(Product.class)))
                    .thenReturn(product);

            UUID result = productService.insertProduct(product);

            assertEquals(product.getId(), result);

            verify(productRepository)
                    .save(product);
        }
    }

    @Nested
    class FindBySku {

        @Test
        @DisplayName("Should find product by sku")
        void shouldFindProductBySku() {
            when(productRepository.findBySku(product.getSku()))
                    .thenReturn(Optional.ofNullable(product));

            Product result = productService.findBySku(product.getSku());

            assertEquals(product, result);
            verify(productRepository).findBySku(product.getSku());
        }

        @Test
        @DisplayName("Should throw exception when product doesn't exists")
        void shouldThrowExceptionWhenProductDoesntExists() {

            when(productRepository.findBySku(product.getSku()))
                    .thenReturn(Optional.empty());

            NotFoundException exception = assertThrows(
                    NotFoundException.class,
                    () -> productService.findBySku(product.getSku())
            );

            assertEquals(Constants.MSG_NOT_FOUND.getValue(), exception.getMessage());
        }
    }

    @Nested
    class FindAll {

        @Test
        @DisplayName("Should return all products")
        void shouldReturnAllProducts() {

            when(productRepository.findAll())
                    .thenReturn(List.of(product));

            List<Product> products =
                    productService.findAll();

            assertNotNull(products);
            assertEquals(1, products.size());

            verify(productRepository)
                    .findAll();
        }
    }

    @Nested
    class DeleteById {

        @Test
        @DisplayName("Should delete product with success")
        void shouldDeleteInventaryWithSuccess() {

            when(productRepository.existsById(any(UUID.class)))
                    .thenReturn(true);

            productService.deleteById(product.getId());

            verify(productRepository, times(1))
                    .deleteById(any(UUID.class));
        }

        @Test
        @DisplayName("Should throw exception when doesn't exists")
        void shouldThrowExceptionWhenProductDoesntExists() {

            when(productRepository.existsById(any(UUID.class)))
                    .thenReturn(false);

            NotFoundException exception = assertThrows(
                    NotFoundException.class,
                    () -> productService.deleteById(product.getId())
            );

            assertEquals(Constants.MSG_NOT_FOUND.getValue(), exception.getMessage());
            verify(productRepository, times(1)).existsById(any(UUID.class));
        }
    }

    @Nested
    class FindById {

        @Test
        @DisplayName("Should find product by id successfully")
        void shouldFindProductByIdSuccessfully() {

            when(productRepository.findById(product.getId()))
                    .thenReturn(Optional.of(product));

            Product result = productService.findById(product.getId());

            assertEquals(product, result);

            verify(productRepository).findById(product.getId());
        }

        @Test
        @DisplayName("Should throw exception when product not found by id")
        void shouldThrowExceptionWhenProductNotFoundById() {

            when(productRepository.findById(any(UUID.class)))
                    .thenReturn(Optional.empty());

            NotFoundException exception = assertThrows(
                    NotFoundException.class,
                    () -> productService.findById(product.getId())
            );

            assertEquals(Constants.MSG_NOT_FOUND.getValue(), exception.getMessage());

            verify(productRepository).findById(any(UUID.class));
        }
    }

    @Nested
    class UpdateProduct {

        @Test
        @DisplayName("Should update product successfully")
        void shouldUpdateProductSuccessfully() {

            when(productRepository.save(any(Product.class)))
                    .thenReturn(product);

            productService.updateProduct(product);

            verify(productRepository, times(1)).save(product);
        }
    }
}