package br.com.stockFlow.service;

import br.com.stockFlow.Model.User;
import br.com.stockFlow.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService {
    UserRepository userRepository;

    public UUID createUser(User user) {
        try {
            userRepository.save(user);
        }catch(RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
        return user.getId();
    }

}
