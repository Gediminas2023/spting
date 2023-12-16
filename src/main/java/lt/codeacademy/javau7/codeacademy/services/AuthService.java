package lt.codeacademy.javau7.codeacademy.services;

import lt.codeacademy.javau7.codeacademy.config.JwtUtils;
import lt.codeacademy.javau7.codeacademy.config.UserDetail;
import lt.codeacademy.javau7.codeacademy.entities.ERole;
import lt.codeacademy.javau7.codeacademy.entities.Role;
import lt.codeacademy.javau7.codeacademy.entities.User;
import lt.codeacademy.javau7.codeacademy.payload.request.LoginRequest;
import lt.codeacademy.javau7.codeacademy.payload.request.SignupRequest;
import lt.codeacademy.javau7.codeacademy.payload.response.JwtResponse;
import lt.codeacademy.javau7.codeacademy.payload.response.MessageResponse;
import lt.codeacademy.javau7.codeacademy.repositories.RoleRepository;
import lt.codeacademy.javau7.codeacademy.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public AuthService(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetail userDetails = (UserDetail) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    public ResponseEntity<?> registerUser(SignupRequest signUpRequest){

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(roleName -> {
                ERole eRole = ERole.valueOf(roleName);
                Role roleEntity = roleRepository.findByName(eRole)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(roleEntity);
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


}
