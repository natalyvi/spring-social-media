package com.example.bitter;

import com.example.bitter.entity.Credentials;
import com.example.bitter.entity.Profile;
import com.example.bitter.entity.User;
import com.example.bitter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        User user1 = new User();
        Credentials credentials1 = new Credentials();
        credentials1.setUsername("user1");
        credentials1.setPassword("bebes");
        user1.setCredentials(credentials1);
        user1.setJoined(new Timestamp(System.currentTimeMillis()));
        user1.setDeleted(false);
        Profile profile1 = new Profile();
        profile1.setFirstName("jj");
        profile1.setLastName("fff");
        profile1.setEmail("jj.ff@hotmail.com");
        profile1.setPhone("(888)555-8888");
        user1.setProfile(profile1);

        userRepository.save(user1);

        User user2 = new User();
        Credentials credentials2 = new Credentials();
        credentials2.setUsername("user2");
        credentials2.setPassword("bebs");
        user2.setCredentials(credentials2);
//        user2.setJoined(new Timestamp(System.currentTimeMillis()));
        user2.setDeleted(false);
        Profile profile2 = new Profile();
        profile2.setFirstName("mmn");
        profile2.setLastName("ghgh");
        profile2.setEmail("mm.ghgh@hotmail.com");
        profile2.setPhone("(888)666-8888");
        user2.setProfile(profile2);

        userRepository.save(user2);
    }
}
