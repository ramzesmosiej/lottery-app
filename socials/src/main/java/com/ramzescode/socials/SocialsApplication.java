package com.ramzescode.socials;

import com.ramzescode.socials.domain.AppUser;
import com.ramzescode.socials.domain.CV;
import com.ramzescode.socials.domain.Post;
import com.ramzescode.socials.domain.Role;
import com.ramzescode.socials.repository.CVRepository;
import com.ramzescode.socials.repository.RoleRepository;
import com.ramzescode.socials.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SocialsApplication {

    public SocialsApplication(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(SocialsApplication.class, args);
    }

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, CVRepository cvRepository, RoleRepository roleRepository) {
        return args -> {
            Role adminRole = new Role("ROLE_ADMIN");
            roleRepository.save(adminRole);
            List<Role> adminRoles = new ArrayList<>();
            adminRoles.add(adminRole);
            AppUser adminUser = new AppUser("Ramzes Admin", "admin", "admin@gmail.com", bCryptPasswordEncoder.encode("admin"), adminRoles);
//            userRepository.save(adminUser);
//            userRepository.save(appUser);


            userRepository.findAll(Sort.by(Sort.Direction.ASC, "name").and(Sort.by(Sort.Direction.ASC, "password")))
                    .forEach(student -> System.out.println(student.getName() + student.getPassword()));
            PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "name"));
            Page<AppUser> page = userRepository.findAll(pageRequest);
            System.out.println(page.get());
        };
    }
}
