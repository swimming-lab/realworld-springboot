package swm.realworld.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import swm.realworld.domain.common.BaseEntity;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "image")
    private String image;

    @Column(name = "bio")
    private String bio;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User update(UserDto userDto) {
        if (userDto.getEmail() != null) {
            this.email = userDto.getEmail();
        }
        if (userDto.getUsername() != null) {
            this.username = userDto.getUsername();
        }
        if (userDto.getPassword() != null) {
            this.password = userDto.getPassword();
        }
        if (userDto.getImage() != null) {
            this.image = userDto.getImage();
        }
        if (userDto.getBio() != null) {
            this.bio = userDto.getBio();
        }
        return this;
    }
}
