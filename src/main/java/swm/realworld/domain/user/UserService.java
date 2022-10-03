package swm.realworld.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signUp(UserDto.SignUp userDto) {
        userRepository.findByUsernameOrEmail(userDto.getUsername(), userDto.getEmail())
                        .ifPresent(user -> {throw new RuntimeException("Duplicate User.");});

        final var user = new User(userDto.getUsername(), userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> login(UserDto.Login userDto) {
        return userRepository.findFirstByEmail(userDto.getEmail())
                .filter(user -> passwordEncoder.matches(userDto.getPassword(), user.getPassword()));
    }

    @Transactional
    public User update(Long userId, UserDto.Update userDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new RuntimeException("Not exist User.");
        });

        if (userDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getUsername() != null) {
            user.setUsername(userDto.getUsername());
        }
        if (userDto.getImage() != null) {
            user.setImage(userDto.getImage());
        }
        if (userDto.getBio() != null) {
            user.setBio(userDto.getBio());
        }

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findFirstByEmail(email).orElse(null);
    }

}
