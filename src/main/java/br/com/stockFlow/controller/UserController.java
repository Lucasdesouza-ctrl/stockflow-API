package br.com.stockFlow.controller;

import br.com.stockFlow.Model.User;
import br.com.stockFlow.dto.UserDTO;
import br.com.stockFlow.mapper.UserMapper;
import br.com.stockFlow.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;
    UserMapper userMapper;

    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid UserDTO userDTO) {
       User entity =  userMapper.toEntity(userDTO);
        userService.createUser(entity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping()
    public List<UserDTO> findAll() {
       List<User> users =  userService.getAllUsers();

        return userMapper.toUserDTOs(users);
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable UUID id) {
        return userMapper.toUserDTO(userService.getUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable UUID id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

}
