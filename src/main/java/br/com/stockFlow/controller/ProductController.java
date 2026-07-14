package br.com.stockFlow.controller;

import br.com.stockFlow.Model.Product;
import br.com.stockFlow.dto.ProductDTO;
import br.com.stockFlow.mapper.ProductMapper;
import br.com.stockFlow.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/product")
public class  ProductController {

    ProductService productService;
    ProductMapper mapper;

    @PostMapping
    public UUID createProdut(@RequestBody @Valid ProductDTO productDTO) {
        Product product = mapper.toProduct(productDTO);
        return productService.insertProduct(product);
    }

    @PutMapping("/{id}")
    public ProductDTO update(@RequestBody ProductDTO productDTO, @PathVariable UUID id) {
        Product product = productService.findById(id);
        mapper.updateEntity(productDTO, product);
       return mapper.toProductDTO(productService.updateProduct(product));
    }

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return mapper.toProductList(productService.findAll());
    }

    @GetMapping("/{sku}")
    public ProductDTO getProductById(@PathVariable String sku) {
        return mapper.toProductDTO(productService.findBySku(sku));
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable UUID id) {
        productService.deleteById(id);
    }


}
