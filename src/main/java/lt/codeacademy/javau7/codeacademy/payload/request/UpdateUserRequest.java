package lt.codeacademy.javau7.codeacademy.payload.request;

import lombok.Data;

import java.util.Set;
@Data
public class UpdateUserRequest {
    private String username;
    private String email;
    private String password;
    private Set<String> roles;
    boolean isBlocked;
}
