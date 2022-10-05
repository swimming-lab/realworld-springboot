package swm.realworld.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserService userService;

    @Transactional(readOnly = true)
    public ProfileDto getProfile(Long viewerId, String usernameToView) {
        final var viewer = userService.getUserById(viewerId).orElseThrow();
        return userService
                .getUserByUsername(usernameToView)
                .map(viewer::viewProfile)
                .orElseThrow();
    }

    @Transactional
    public ProfileDto followUser(Long followerId, String followeeString) {
        final var followee = userService.getUserByUsername(followeeString).orElseThrow();
        return userService
                .getUserById(followerId)
                .map(follower -> follower.followUser(followee))
                .map(follower -> follower.viewProfile(followee))
                .orElseThrow();
    }

    @Transactional
    public ProfileDto unfollowUser(Long followerId, String followeeString) {
        final var followee = userService.getUserByUsername(followeeString).orElseThrow(NoSuchElementException::new);
        return userService
                .getUserById(followerId)
                .map(follower -> follower.unfollowUser(followee))
                .map(follower -> follower.viewProfile(followee))
                .orElseThrow();
    }
}
