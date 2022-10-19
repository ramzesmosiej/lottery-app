package com.ramzescode.socials;

import com.github.javafaker.Faker;
import com.ramzescode.socials.model.CV;
import com.ramzescode.socials.model.Post;
import com.ramzescode.socials.model.User;
import com.ramzescode.socials.repository.CVRepository;
import com.ramzescode.socials.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SocialsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialsApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, CVRepository cvRepository) {
        return args -> {
            userRepository.deleteAll();
            cvRepository.deleteAll();
            Faker faker = new Faker();
            String name = faker.name().name();
            String surname = String.format("%son", name);
            User user = new User(name, surname, "ramzes@gmail.com", faker.funnyName().name());
            Post post1 = new Post("post1", LocalDateTime.now(), "content1", 0);
            user.addPost(post1);
            user.addPost(new Post("post2", LocalDateTime.now(), "content2", 0));
            user.addPost(new Post("post3", LocalDateTime.now(), "content3", 0));
            user.setCv(new CV(user, 22, "email", "bio"));
            user.likePost(post1);
            userRepository.save(user);


            userRepository.findAll(Sort.by(Sort.Direction.ASC, "name").and(Sort.by(Sort.Direction.ASC, "password")))
                    .forEach(student -> System.out.println(student.getName() + student.getPassword()));
            PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "name"));
            Page<User> page = userRepository.findAll(pageRequest);
            System.out.println(page);
        };
    }
}
