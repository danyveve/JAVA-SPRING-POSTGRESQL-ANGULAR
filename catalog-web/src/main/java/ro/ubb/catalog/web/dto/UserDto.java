package ro.ubb.catalog.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto extends BaseDto {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String role;

    @Override
    public String toString() {
        return "UserDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username=" + username +
                ", password=" + password +
                ", role=" + role +
                "} " + super.toString();
    }
}
