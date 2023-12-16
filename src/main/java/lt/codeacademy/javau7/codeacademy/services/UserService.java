package lt.codeacademy.javau7.codeacademy.services;

import lt.codeacademy.javau7.codeacademy.entities.ERole;
import lt.codeacademy.javau7.codeacademy.entities.Role;
import lt.codeacademy.javau7.codeacademy.entities.User;
import lt.codeacademy.javau7.codeacademy.payload.request.UpdateUserRequest;
import lt.codeacademy.javau7.codeacademy.repositories.RoleRepository;
import lt.codeacademy.javau7.codeacademy.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    protected final UserRepository userRepository;
    protected final PasswordEncoder encoder;
    protected final RoleRepository roleRepository;
    public UserService(UserRepository userRepository, PasswordEncoder encoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

    public String updateUserById(Long userId, UpdateUserRequest updateUser) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()){
            User existingUser = optionalUser.get();
            existingUser.setUsername(updateUser.getUsername());
            existingUser.setEmail(updateUser.getEmail());
            existingUser.setPassword(encoder.encode(updateUser.getPassword()));
            existingUser.setBlocked(updateUser.isBlocked());

            Set<Role> updatedRoles = new HashSet<>();
            updateUser.getRoles().forEach(roleName -> {
                ERole eRole = ERole.valueOf(roleName);
                Role userRole = roleRepository.findByName(eRole)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                updatedRoles.add(userRole);
            });

            existingUser.setRoles(updatedRoles);

            userRepository.save(existingUser);

            return "User updated successfully";
        }

        return "Not Found";
    }

}
