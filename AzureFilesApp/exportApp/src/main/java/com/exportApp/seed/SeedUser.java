package com.exportApp.seed;

import com.exportApp.model.entity.User;
import com.exportApp.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SeedUser implements CommandLineRunner {
    private final UserRepository userRepository;
    @Transactional
    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findAll().isEmpty()) {
            User user = User.builder().username("admin").password("admin").build();
            userRepository.save(user);
        }
    }
}
