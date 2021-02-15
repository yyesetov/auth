package sc.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sc.auth.config.jwt.JwtProvider;
import sc.auth.dto.AuthRequest;
import sc.auth.dto.AuthResponse;
import sc.auth.dto.RegistrationRequest;
import sc.auth.entity.UserEntity;
import sc.auth.service.UserService;

import javax.validation.Valid;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        UserEntity u = new UserEntity();
        u.setPassword(registrationRequest.getPassword());
        u.setLogin(registrationRequest.getLogin());
        userService.saveUser(u);
        return "OK";
    }

    @PostMapping("/login")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        UserEntity userEntity = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        if (userEntity==null){
            return new AuthResponse("Username not found");
        }
        String token = jwtProvider.generateToken(userEntity.getLogin());
        return new AuthResponse(token);
    }

}
