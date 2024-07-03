package com.boda.boda;

import com.boda.boda.entity.Role;
import com.boda.boda.entity.User;
import com.boda.boda.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
//@ComponentScan(basePackages = {"com.boda.boda"})
public class BoDaApplication implements CommandLineRunner {

    @Autowired
    private final UserRepository userRepository;

    public BoDaApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public static void main(String[] args) {
        SpringApplication.run(BoDaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User acount = userRepository.findByRole(Role.ADMIN);
        if ( null == acount){
            User user = new User();
            user.setEmail("buithong@gmail.com");
            user.setFirstname("Bui");
            user.setSecondname("Thong");
            user.setRole(Role.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
        }
    }
}
