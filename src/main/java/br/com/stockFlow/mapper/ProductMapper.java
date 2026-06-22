package br.com.stockFlow.mapper;

import br.com.stockFlow.Model.Product;
import br.com.stockFlow.dto.ProductDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(ProductDTO productDTO);
    ProductDTO toProductDTO(Product product);
    List<ProductDTO> toProductList(List<Product> products);

}
