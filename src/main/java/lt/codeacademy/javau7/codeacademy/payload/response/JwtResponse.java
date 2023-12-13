package lt.codeacademy.javau7.codeacademy.payload.response;

import lombok.Data;
import lombok.Getter;

import java.util.List;
@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    @Getter
    private Long id;
    @Getter
    private String username;
    private String email;
    @Getter
    private final List<String> roles;

    public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}

