package nod.auth.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nod.auth.domain.user.entity.User;
import nod.auth.domain.user.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user1 = User.create("test1", passwordEncoder.encode("password1"));
        User user2 = User.create("test2", passwordEncoder.encode("password2"));

        userRepository.save(user1);
        userRepository.save(user2);
    }
}
