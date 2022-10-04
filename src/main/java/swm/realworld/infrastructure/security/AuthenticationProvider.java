package swm.realworld.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import swm.realworld.domain.user.User;
import swm.realworld.domain.user.UserDto;

@Component
@RequiredArgsConstructor
public class AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    public Authentication getAuthentication(String username) {
        UserDetails userDetail = userDetailsService.loadUserByUsername(username);
        if (userDetail == null) return null;
        User user = (User) userDetail;
        UserDto.Auth authenticatedUser = UserDto.Auth.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .bio(user.getBio())
                .image(user.getImage())
                .build();
        return new UsernamePasswordAuthenticationToken(authenticatedUser, "", userDetail.getAuthorities());
    }
}
