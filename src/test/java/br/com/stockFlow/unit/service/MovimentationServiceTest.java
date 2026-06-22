package br.com.stockFlow.unit.service;

import br.com.stockFlow.Model.Movimentation;
import br.com.stockFlow.Model.Product;
import br.com.stockFlow.enums.Constants;
import br.com.stockFlow.exception.InsufficientStockException;
import br.com.stockFlow.exception.NotFoundException;
import br.com.stockFlow.repository.MovimentationRepository;
import br.com.stockFlow.service.MovimentationService;
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
class MovimentationServiceTest {

    @Mock
    MovimentationRepository movimentationRepository;

    @Mock
    ProductService productService;

    @InjectMocks
    MovimentationService movimentationService;

    Movimentation movimentation;
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

        movimentation = new Movimentation();
        movimentation.setMovimentationId(UUID.randomUUID());
        movimentation.setQuantity(5);
        movimentation.setProduct(product);
    }

    @Nested
    class FindById {

        @Test
        @DisplayName("Should return movimentation by id")
        void shouldReturnMovimentationById() {

            when(movimentationRepository.findById(movimentation.getMovimentationId()))
                    .thenReturn(Optional.of(movimentation));

            Movimentation result = movimentationService.findById(movimentation.getMovimentationId());

            assertEquals(movimentation, result);
            verify(movimentationRepository).findById(movimentation.getMovimentationId());
        }

        @Test
        @DisplayName("Should throw NotFoundException when movimentation not found")
        void shouldThrowNotFoundException() {

            when(movimentationRepository.findById(any(UUID.class)))
                    .thenReturn(Optional.empty());

            NotFoundException exception = assertThrows(
                    NotFoundException.class,
                    () -> movimentationService.findById(UUID.randomUUID())
            );

            assertEquals(Constants.MSG_NOT_FOUND.getValue(), exception.getMessage());
        }
    }

    @Nested
    class FindAll {

        @Test
        @DisplayName("Should return all movimentations")
        void shouldReturnAllMovimentations() {

            when(movimentationRepository.findAll())
                    .thenReturn(List.of(movimentation));

            List<Movimentation> result = movimentationService.findAll();

            assertNotNull(result);
            assertEquals(1, result.size());

            verify(movimentationRepository).findAll();
        }
    }

    @Nested
    class Save {

        @Test
        @DisplayName("Should save movimentation and update product quantity")
        void shouldSaveMovimentation() {

            when(productService.findById(product.getId()))
                    .thenReturn(product);

            when(movimentationRepository.save(any(Movimentation.class)))
                    .thenReturn(movimentation);

            Movimentation result = movimentationService.save(movimentation);

            assertEquals(movimentation, result);

            assertEquals(15, product.getQuantity());

            verify(productService).findById(product.getId());
            verify(movimentationRepository).save(movimentation);
        }

        @Test
        @DisplayName("Should throw exception when insufficient stock")
        void shouldThrowInsufficientStock() {

            product.setQuantity(0);
            movimentation.setQuantity(-1);

            when(productService.findById(product.getId()))
                    .thenReturn(product);

            InsufficientStockException exception = assertThrows(
                    InsufficientStockException.class,
                    () -> movimentationService.save(movimentation)
            );

            assertEquals(Constants.MSG_INSUFFICIENT_STOCK.getValue(), exception.getMessage());

            verify(productService).findById(product.getId());
            verify(movimentationRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should decrease product quantity on normal outbound movement")
        void shouldDecreaseProductQuantityOnOutboundMovement() {

            product.setQuantity(10);
            movimentation.setQuantity(-3);

            when(productService.findById(product.getId()))
                    .thenReturn(product);

            when(movimentationRepository.save(any()))
                    .thenReturn(movimentation);

            Movimentation result = movimentationService.save(movimentation);

            assertEquals(7, product.getQuantity());
            assertEquals(movimentation, result);

            verify(productService).findById(product.getId());
            verify(movimentationRepository).save(movimentation);
        }

        @Test
        @DisplayName("Should throw exception when movement exceeds stock")
        void shouldThrowWhenMovementExceedsStock() {

            product.setQuantity(5);
            movimentation.setQuantity(-10);

            when(productService.findById(product.getId()))
                    .thenReturn(product);

            InsufficientStockException exception = assertThrows(
                    InsufficientStockException.class,
                    () -> movimentationService.save(movimentation)
            );

            assertEquals(Constants.MSG_INSUFFICIENT_STOCK.getValue(), exception.getMessage());

            verify(movimentationRepository, never()).save(any());
        }
    }
}