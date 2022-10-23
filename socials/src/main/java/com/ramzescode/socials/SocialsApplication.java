package com.ramzescode.socials;

import com.ramzescode.socials.domain.AppUser;
import com.ramzescode.socials.domain.CV;
import com.ramzescode.socials.domain.Post;
import com.ramzescode.socials.repository.CVRepository;
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
    CommandLineRunner commandLineRunner(UserRepository userRepository, CVRepository cvRepository) {
        return args -> {
            userRepository.deleteAll();
            cvRepository.deleteAll();
            String name = "Ramzes Mosiej";
            String username = "ramzesracer";
            String password = bCryptPasswordEncoder.encode("password");
            String email = "ramzes@gmail.com";
            AppUser appUser = new AppUser(name, username, email, password);
            Post post1 = new Post("post1", LocalDateTime.now(), "content1", 0);
            appUser.addPost(post1);
            appUser.addPost(new Post("post2", LocalDateTime.now(), "content2", 0));
            appUser.addPost(new Post("post3", LocalDateTime.now(), "content3", 0));
            appUser.setCv(new CV(appUser, 22, "email", "bio"));
            appUser.likePost(post1);
            userRepository.save(appUser);


            userRepository.findAll(Sort.by(Sort.Direction.ASC, "name").and(Sort.by(Sort.Direction.ASC, "password")))
                    .forEach(student -> System.out.println(student.getName() + student.getPassword()));
            PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "name"));
            Page<AppUser> page = userRepository.findAll(pageRequest);
            System.out.println(page);
        };
    }
}
