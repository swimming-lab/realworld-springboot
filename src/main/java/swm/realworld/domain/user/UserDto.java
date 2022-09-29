package swm.realworld.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import swm.realworld.domain.common.BaseEntity;

@Data
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
