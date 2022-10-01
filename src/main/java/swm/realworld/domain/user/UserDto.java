package swm.realworld.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swm.realworld.domain.common.BaseEntity;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends BaseEntity {

    private String username;
    private String email;
    private String password;
    private String image;
    private String bio;

    protected User toUser() {
        return new User(username, email, password);
    }
}
