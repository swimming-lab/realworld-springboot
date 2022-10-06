package swm.realworld.application.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import swm.realworld.domain.user.UserDto;
import swm.realworld.domain.user.UserService;
import swm.realworld.infrastructure.security.JwtUtils;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/users")
    public UserDto postUser(@Valid @RequestBody UserDto.SignUp param) {
        final var userSaved = userService.signUp(param);
        return UserDto.fromUserAndToken(userSaved, jwtUtils.encode(userSaved.getUsername()));
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserDto> loginUser(@Valid @RequestBody UserDto.Login param) {
        return ResponseEntity.of(userService
                .login(param)
                .map(user -> UserDto.fromUserAndToken(user, jwtUtils.encode(user.getUsername()))));
    }

    @GetMapping("/user")
    public ResponseEntity<UserDto> getUser(@AuthenticationPrincipal UserDto.Auth authDto) {
        return ResponseEntity.of(userService
                .getUserById(authDto.getId())
                .map(user -> UserDto.fromUserAndToken(user, getCurrentCredential())));
    }

    @PutMapping("/user")
    public UserDto putUser(
            @AuthenticationPrincipal UserDto.Auth authDto, @Valid @RequestBody UserDto.Update param) {
        final var userUpdated = userService.update(authDto.getId(), param);
        return UserDto.fromUserAndToken(userUpdated, getCurrentCredential());
    }

    private static String getCurrentCredential() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials()
                .toString();
    }
}
