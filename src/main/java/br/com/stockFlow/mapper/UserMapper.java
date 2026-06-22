package br.com.stockFlow.mapper;

import br.com.stockFlow.Model.User;
import br.com.stockFlow.dto.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDTO(User user);
    User toEntity(UserDTO userDTO);
    List<UserDTO> toUserDTOs(List<User> users);

}
