package swm.realworld.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signup(UserDto userDto) {
        userRepository.findByUsernameOrEmail(userDto.getUsername(), userDto.getEmail())
                        .ifPresent(user -> {throw new RuntimeException("Duplicate User");});

        final var user = new User(userDto.getUsername(), userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User login(UserDto userDto) {
        return userRepository.findFirstByEmail(userDto.getEmail())
                .filter(user -> passwordEncoder.matches(userDto.getPassword(), user.getPassword()))
                .orElseThrow(() -> new RuntimeException("LOGIN_INFO_INVALID"));
    }

    @Transactional
    public User update(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new RuntimeException("Not exist User.");
        });

        if (userDto.getPassword() != null) {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        return user.update(userDto);
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }
}
