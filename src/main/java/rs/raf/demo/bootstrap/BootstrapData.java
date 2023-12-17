package rs.raf.demo.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rs.raf.demo.model.*;
import rs.raf.demo.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class BootstrapData implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public BootstrapData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Loading Data...");

//        User user1 = new User();
//        user1.setEmail("pera01@gmail.com");
//        user1.setPassword(this.passwordEncoder.encode("123"));
//        user1.setFirstName("Pera");
//        user1.setLastName("Peric");
//        user1.setPermissions("can_create_users;can_read_users;can_update_users;can_delete_users");
//        this.userRepository.save(user1);

//        User user2 = new User();
//        user2.setEmail("aleksa.prokic888@gmail.com");
//        user2.setPassword(this.passwordEncoder.encode("123"));
//        user2.setFirstName("Aleksa");
//        user2.setLastName("Prokic");
//        user2.setPermissions("can_create_users;can_read_users;can_update_users;can_delete_users");
//        this.userRepository.save(user2);
//
//        User user3 = new User();
//        user3.setEmail("cantDeleteUser@gmail.com");
//        user3.setPassword(this.passwordEncoder.encode("del"));
//        user3.setFirstName("Cantus");
//        user3.setLastName("Deletus");
//        user3.setPermissions("can_create_users;can_read_users;can_update_users");
//        this.userRepository.save(user3);

        User user4 = new User();
        user4.setEmail("cantReadUser@gmail.com");
        user4.setPassword(this.passwordEncoder.encode("read"));
        user4.setFirstName("Cantus");
        user4.setLastName("Readus");
        user4.setPermissions("can_create_users;can_update_users;can_delete_users");
        this.userRepository.save(user4);

        System.out.println("Data loaded!");
    }
}
