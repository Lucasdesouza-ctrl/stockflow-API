package br.com.stockFlow.service;

import br.com.stockFlow.exception.NotFoundException;
import br.com.stockFlow.Model.Product;
import br.com.stockFlow.enums.Constants;
import br.com.stockFlow.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ProductService {

    private ProductRepository productRepository;

    @Transactional
    public UUID insertProduct(Product product) {
        productRepository.save(product);
        return product.getId();
    }

    @Transactional(readOnly = true)
    public Product findBySku(String sku) {
        return productRepository.findBySku(sku).orElseThrow(
                () -> new NotFoundException(Constants.MSG_NOT_FOUND.getValue())
        );
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional
    public void deleteById(UUID id) {
        if (!productRepository.existsById(id))
            throw new NotFoundException(Constants.MSG_NOT_FOUND.getValue());
        productRepository.deleteById(id);
    }

    @Transactional
    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Product findById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(
                        () ->new NotFoundException(Constants.MSG_NOT_FOUND.getValue()));
    }
}
