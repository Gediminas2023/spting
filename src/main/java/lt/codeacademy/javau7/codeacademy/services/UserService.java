package lt.codeacademy.javau7.codeacademy.services;

import lt.codeacademy.javau7.codeacademy.entities.User;
import lt.codeacademy.javau7.codeacademy.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    protected final UserRepository userRepository;
    protected final PasswordEncoder encoder;
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
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

    public String updateUserById(Long userId, User updateUser) {
        System.out.println("Received updateUser: " + updateUser.toString());
        Optional<User> existingUser = userRepository.findById(userId);
        if(existingUser.isPresent()){
            User optionalUser = existingUser.get();
            optionalUser.setUsername(updateUser.getUsername());
            optionalUser.setEmail(updateUser.getEmail());
            optionalUser.setRoles(updateUser.getRoles());
            optionalUser.setPassword(encoder.encode(updateUser.getPassword()));
            optionalUser.setBlocked(updateUser.isBlocked());
            userRepository.save(optionalUser);
            return "User updated successfully";
        }

        return "Not Found";
    }

    public void deleteDateByUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
