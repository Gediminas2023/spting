package lt.codeacademy.javau7.codeacademy.controller;

import jakarta.validation.Valid;
import lt.codeacademy.javau7.codeacademy.entities.User;
import lt.codeacademy.javau7.codeacademy.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    public String updateUserById(@PathVariable Long userId, @RequestBody User updateUser){
        return userService.updateUserById(userId, updateUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }

}
