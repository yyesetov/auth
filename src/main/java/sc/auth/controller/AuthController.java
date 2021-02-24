package sc.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import sc.auth.config.CustomUserDetails;
import sc.auth.config.jwt.JwtProvider;
import sc.auth.dto.AuthRequest;
import sc.auth.dto.AuthResponse;
import sc.auth.dto.RegistrationRequest;
import sc.auth.entity.UserEntity;
import sc.auth.repository.UserEntityRepository;
import sc.auth.service.UserService;

import javax.validation.Valid;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserEntityRepository userEntityRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(registrationRequest.getPassword());
        userEntity.setLogin(registrationRequest.getLogin());
        userService.saveUser(userEntity);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest request) {
        UserEntity userEntity = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        AuthResponse authResponse = new AuthResponse();
        String token = jwtProvider.generateToken(userEntity.getLogin());
        authResponse.setToken(token);
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/users/current")
    public ResponseEntity<UserEntity> getUserByUsername(@AuthenticationPrincipal CustomUserDetails userDetails){
        String username = userDetails.getUsername();
        UserEntity users = userEntityRepository.findByLogin(username);
        if (users == null){
            throw new UsernameNotFoundException("username " + users + " not found!");
        }
        return ResponseEntity.ok(users);
    }
}
