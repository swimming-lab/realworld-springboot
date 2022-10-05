package swm.realworld.application.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import swm.realworld.domain.user.ProfileDto;
import swm.realworld.domain.user.ProfileService;
import swm.realworld.domain.user.UserDto;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{username}")
    public ProfileDto getProfile(@PathVariable("username") String username, @AuthenticationPrincipal UserDto.Auth authUser) {
        return profileService.getProfile(authUser.getId(), username);
    }

    @PostMapping("/{username}/follow")
    public ProfileDto followUser(@PathVariable("username") String username, @AuthenticationPrincipal UserDto.Auth authUser) {
        return profileService.followUser(authUser.getId(), username);
    }

    @DeleteMapping("/{username}/follow")
    public ProfileDto unfollowUser(@PathVariable("username") String username, @AuthenticationPrincipal UserDto.Auth authUser) {
        return profileService.unfollowUser(authUser.getId(), username);
    }
}
