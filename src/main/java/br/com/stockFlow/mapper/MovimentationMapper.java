package br.com.stockFlow.mapper;

import br.com.stockFlow.Model.Movimentation;
import br.com.stockFlow.dto.MovimentationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovimentationMapper {

    Movimentation toEntity(MovimentationDTO movimentationDTO);
    MovimentationDTO toDTO(Movimentation movimentation);
}
