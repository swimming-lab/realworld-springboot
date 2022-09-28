package swm.realworld.domain.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EnableJpaAuditing
class UserRepositoryTest {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Test
    void when_save_user_expect_saved() {
        var userToSave = new User(
                "user@email.com",
                "username",
                passwordEncoder.encode("rawPassword"));

        User save = userRepository.save(userToSave);
        assertThat(save).hasNoNullFieldsOrProperties();
    }
}