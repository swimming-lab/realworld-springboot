package swm.realworld.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void initializeUserService() {
        this.userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void when_signUp_expect_password_encoded(@Mock UserDto.SignUp userDto) {
        given(userDto.getPassword()).willReturn("raw-password");

        userService.signUp(userDto);

        then(passwordEncoder).should(times(1)).encode("raw-password");
    }

    @Test
    void when_duplicate_signUp_expect_throw_exception(@Mock UserDto.SignUp userDto, @Mock User user) {
        when(userRepository.findByUsernameOrEmail(userDto.getUsername(), userDto.getEmail())).thenReturn(of(user));

        try {
            userService.signUp(userDto);
            fail();
        } catch (RuntimeException e) {
            assertEquals("Duplicate User.", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void when_valid_login_then_return_user() {
        UserDto.Login userDto = UserDto.Login.builder()
                .email("test@test.com")
                .password("raw-password")
                .build();

        var user = new User(
                "test-man",
                "test@test.com",
                passwordEncoder.encode("raw-password"));

        when(userRepository.findFirstByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(eq(userDto.getPassword()), eq(user.getPassword()))).thenReturn(true);

        User actual = userService.login(userDto);

        assertEquals(userDto.getEmail(), actual.getEmail());
    }
}