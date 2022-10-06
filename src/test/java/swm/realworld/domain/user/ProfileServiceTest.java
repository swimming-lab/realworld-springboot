package swm.realworld.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    private ProfileService profileService;

    @Mock
    private UserService userService;

    @BeforeEach
    void initializeUserService() {
        this.profileService = new ProfileService(userService);
    }

    @Test
    void when_getProfile_with_viewer_not_exists_expect_NoSuchElementException() {
        when(userService.getUserById(1L)).thenReturn(empty());

        assertThatThrownBy(() -> profileService.getProfile(1L, "test-man")).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void when_getProfile_with_not_exists_username_expect_NoSuchElementException(
            @Mock User user) {
        when(userService.getUserById(1L)).thenReturn(of(user));
        when(userService.getUserByUsername(any())).thenReturn(empty());

        assertThatThrownBy(() -> profileService.getProfile(1L, "test-man")).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void when_getProfile_expect_viewer_view_found_user(
            @Mock User viewer, @Mock User userToView, @Mock ProfileDto profile) {
        given(userService.getUserById(1L)).willReturn(of(viewer));
        given(userService.getUserByUsername("test-man")).willReturn(of(userToView));
        given(viewer.viewProfile(userToView)).willReturn(profile);

        profileService.getProfile(1L, "test-man");

        then(viewer).should(times(1)).viewProfile(userToView);
    }

    @Test
    void when_followAndViewProfile_with_not_exists_followeeName_expect_NoSuchElementException() {
        when(userService.getUserByUsername("test-man")).thenReturn(empty());

        assertThatThrownBy(() -> profileService.followUser(1L, "test-man"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void when_followAndViewProfile_with_not_exists_followerId_expect_NoSuchElementException(
            @Mock User followee) {
        when(userService.getUserByUsername("test-man")).thenReturn(of(followee));
        when(userService.getUserById(anyLong())).thenReturn(empty());

        assertThatThrownBy(() -> profileService.followUser(1L, "test-man"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void when_followAndViewProfile_expect_follower_follows_followee(
            @Mock User follower, @Mock User followee, @Mock ProfileDto followeeProfile) {
        given(userService.getUserByUsername("test-man")).willReturn(of(followee));
        given(userService.getUserById(anyLong())).willReturn(of(follower));
        given(follower.followUser(followee)).willReturn(follower);
        given(follower.viewProfile(followee)).willReturn(followeeProfile);

        profileService.followUser(1L, "test-man");

        then(follower).should(times(1)).followUser(followee);
    }

    @Test
    void when_unfollowUser_with_not_exists_followeeName_expect_NoSuchElementException() {
        when(userService.getUserByUsername("test-man")).thenReturn(empty());

        assertThatThrownBy(() -> profileService.unfollowUser(1L, "test-man"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void when_unfollowUser_with_not_exists_followerId_expect_NoSuchElementException(
            @Mock User followee) {
        when(userService.getUserByUsername("test-man")).thenReturn(of(followee));
        when(userService.getUserById(anyLong())).thenReturn(empty());

        assertThatThrownBy(() -> profileService.unfollowUser(1L, "test-man"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void when_unfollowUser_expect_follower_unfollows_followee(
            @Mock User follower, @Mock User followee, @Mock ProfileDto followeeProfile) {
        given(userService.getUserByUsername("test-man")).willReturn(of(followee));
        given(userService.getUserById(anyLong())).willReturn(of(follower));
        given(follower.unfollowUser(followee)).willReturn(follower);
        given(follower.viewProfile(followee)).willReturn(followeeProfile);

        profileService.unfollowUser(1L, "test-man");

        then(follower).should(times(1)).unfollowUser(followee);
    }
}