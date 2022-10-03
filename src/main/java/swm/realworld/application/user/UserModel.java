package swm.realworld.application.user;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Value;
import swm.realworld.domain.user.User;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static java.lang.String.valueOf;

@Value
@JsonTypeName("user")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
public class UserModel {

    String email;
    String username;
    String image;
    String token;

    public static UserModel fromUserAndToken(User user, String token) {
        return new UserModel(valueOf(user.getEmail()), valueOf(user.getUsername()), valueOf(user.getImage()), token);
    }
}
