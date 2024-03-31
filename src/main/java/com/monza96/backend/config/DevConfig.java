package com.monza96.backend.config;

import com.monza96.backend.domain.User;
import com.monza96.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("dev")
public class DevConfig implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User u1 = new User(null, "user1@mail.com", "123456");
        User u2 = new User(null, "user2@mail.com", "123456");
        User u3 = new User(null, "user3@mail.com", "123456");

        userRepository.saveAll(Arrays.asList(u1, u2));
    }
}
