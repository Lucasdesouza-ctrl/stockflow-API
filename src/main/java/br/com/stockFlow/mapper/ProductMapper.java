package br.com.stockFlow.mapper;

import br.com.stockFlow.Model.Product;
import br.com.stockFlow.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(ProductDTO productDTO);
    ProductDTO toProductDTO(Product product);
    List<ProductDTO> toProductList(List<Product> products);

    @Mapping(target = "id", ignore = true)
    void updateEntity(ProductDTO productDTO, @MappingTarget Product product);

}
