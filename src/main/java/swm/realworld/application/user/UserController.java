package swm.realworld.application.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import swm.realworld.domain.user.User;
import swm.realworld.domain.user.UserDto;
import swm.realworld.domain.user.UserService;
import swm.realworld.infrastructure.jwt.JwtUtils;

import javax.validation.Valid;

import static java.util.Optional.of;
import static swm.realworld.application.user.UserModel.fromUserAndToken;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/users")
    public UserModel postUser(@Valid @RequestBody UserDto.SignUp param) {
        final var userSaved = userService.signUp(param);
        return fromUserAndToken(userSaved, jwtUtils.encode(userSaved.getUsername()));
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserModel> loginUser(@Valid @RequestBody UserDto.Login param) {
        return of(userService
                .login(param)
                .map(user -> fromUserAndToken(user, jwtUtils.encode(user.getUsername()))));
    }
}
