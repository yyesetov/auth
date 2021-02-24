package sc.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import sc.auth.entity.UserEntity;
import sc.auth.repository.UserEntityRepository;
import sc.auth.service.UserService;


@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    private UserEntityRepository userEntityRepository;
    private SecurityConfig securityConfig;

    @Autowired
    public CustomUserDetailsService(UserEntityRepository userEntityRepository){
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userService.findByLogin(username);
        
        return CustomUserDetails.fromUserEntityToCustomUserDetails(userEntity);
    }
}
