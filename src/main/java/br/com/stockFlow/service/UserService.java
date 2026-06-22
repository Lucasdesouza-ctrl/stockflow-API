package br.com.stockFlow.service;

import br.com.stockFlow.Model.User;
import br.com.stockFlow.enums.Constants;
import br.com.stockFlow.exception.NotFoundException;
import br.com.stockFlow.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService {
    UserRepository userRepository;

    public UUID createUser(User user) {
        if (userRepository.existsByName(user.getName()))
            throw new NotFoundException("Username already exists");
        userRepository.save(user);
        return user.getId();
    }

    public User getUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new NotFoundException(Constants.MSG_NOT_FOUND.getValue());
        return user.get();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(UUID id) {
        if (!userRepository.existsById(id))
            throw new NotFoundException(Constants.MSG_NOT_FOUND.getValue());
        userRepository.deleteById(id);
    }

}
