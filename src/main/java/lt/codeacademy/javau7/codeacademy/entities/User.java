package lt.codeacademy.javau7.codeacademy.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @NotBlank
    @Size(max = 20)
    private String username;

    @Getter
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @Getter
    @NotBlank
    @Size( max = 120)
    private String password;
    boolean isBlocked = false;

    @Getter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Events> events;

    @Getter
    @OneToMany(mappedBy = "date", cascade = CascadeType.ALL)
    private List<Date> dates;


    @Getter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(Long id, String username, String email, boolean isBlocked,  Set<Role> roles, List<Date> dates) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.isBlocked = isBlocked;
        this.roles = roles;
        this.dates = dates;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public User(String username, String email, String password,Set<Role> roles,boolean isBlocked) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.isBlocked = isBlocked;
    }

    public User() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public void setEvents(List<Events> events) {
        this.events = events;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
