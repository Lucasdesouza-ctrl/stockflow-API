package br.com.stockFlow.controller;

import br.com.stockFlow.Model.User;
import br.com.stockFlow.dto.UserDTO;
import br.com.stockFlow.mapper.UserMapper;
import br.com.stockFlow.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;
    UserMapper userMapper;

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
       User entity =  userMapper.toEntity(userDTO);


        userService.createUser(entity);
        return userDTO;
    }

}
