package swm.realworld.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;
import swm.realworld.domain.common.BaseEntity;

import javax.validation.constraints.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("user")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class UserDto extends BaseEntity {

    private String username;
    private String email;
    private String image;
    private String bio;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonTypeName("user")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class SignUp {

        @JsonProperty("username")
        @NotNull
        @Pattern(regexp = "[\\w\\d]{1,30}", message = "string contains alphabet or digit with length 1 to 30")
        private String username;

        @NotNull
        @Email
        private String email;

        @NotBlank
        @Size(min = 4, max = 16)
        private String password;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonTypeName("user")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class Login {

        @NotNull
        @Email
        private String email;

        @NotBlank
        @Size(min = 4, max = 16)
        private String password;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonTypeName("user")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class Update {

        private Long id;
        @Pattern(regexp = "[\\w\\d]{1,30}", message = "string contains alphabet or digit with length 1 to 30")
        private String username;
        private String email;
        @Size(min = 4, max = 16)
        private String password;
        private String bio;
        private String image;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Auth {

        private Long id;
        private String email;
        private String username;
        private String bio;
        private String image;
    }
}
