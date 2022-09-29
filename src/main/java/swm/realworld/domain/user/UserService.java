package swm.realworld.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signup(UserDto userDto) {
        userRepository.findByUsernameOrEmail(userDto.getUsername(), userDto.getEmail())
                        .ifPresent(user -> {throw new RuntimeException("Duplicate User");});

        final var user = new User(userDto.getUsername(), userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    public User login(UserDto userDto) {
        return userRepository.findFirstByEmail(userDto.getEmail())
                .filter(user -> passwordEncoder.matches(userDto.getPassword(), user.getPassword()))
                .orElseThrow(() -> new RuntimeException("LOGIN_INFO_INVALID"));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }
}
