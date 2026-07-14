package br.com.stockFlow.mapper;

import br.com.stockFlow.Model.Movimentation;
import br.com.stockFlow.dto.PostMovimentationDTO;
import br.com.stockFlow.dto.ResponseMovimentationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovimentationMapper {

    Movimentation toEntity(ResponseMovimentationDTO movimentationDTO);

    @Mapping(target = "userName", source = "user.name")
    ResponseMovimentationDTO toDTO(Movimentation movimentation);

    @Mappings({
            @Mapping(target = "user.id", source = "userId"),
            @Mapping(target = "product.id", source = "productId")
    })
    Movimentation PostDTOtoEntity(PostMovimentationDTO movimentationDTO);

    List<ResponseMovimentationDTO> toDTOList(List<Movimentation> movimentations);
}
