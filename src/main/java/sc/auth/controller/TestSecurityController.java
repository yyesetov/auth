package sc.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sc.auth.config.CustomUserDetails;
import sc.auth.entity.UserEntity;
import sc.auth.repository.UserEntityRepository;
import java.util.Optional;

@RestController
public class TestSecurityController {

    private UserEntityRepository userEntityRepository;

    @Autowired
    public TestSecurityController(UserEntityRepository userEntityRepository){
        this.userEntityRepository = userEntityRepository;
    }

    @GetMapping("/admin/get")
    public String getAdmin() {
        System.out.println("getAdmin");
        return "Assalaumagaleikum admin!";
    }

    @GetMapping("/user/get")
    public String getUser() {
        System.out.println("getUser");
        return "Assalaumagaleikum user!";
    }

    @GetMapping("/users/current")
    public UserEntity getUserByUsername(@AuthenticationPrincipal CustomUserDetails userDetails){
        String username = userDetails.getUsername();
        UserEntity users = userEntityRepository.findByLogin(username);
        if (users == null){
            throw new UsernameNotFoundException("Not logged in");
        }
        return users;
    }
}
