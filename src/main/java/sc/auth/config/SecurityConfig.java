package sc.auth.config;

import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sc.auth.config.jwt.JwtFilter;
import sc.auth.entity.CasesRolesEntity;
import sc.auth.repository.CasesRolesEntityRepository;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private CasesRolesEntityRepository casesRolesEntityRepository;
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    public SecurityConfig(CasesRolesEntityRepository casesRolesEntityRepository){
        this.casesRolesEntityRepository = casesRolesEntityRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<CasesRolesEntity> casesRolesEntityList = casesRolesEntityRepository.findAll();
        for(CasesRolesEntity casesRolesEntity: casesRolesEntityList) {
            http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/register", "/login").permitAll()
                .antMatchers(casesRolesEntity.getCasesEntity().getUrl())
                        .hasAnyRole(casesRolesEntity.getRoleEntity().getCode().substring(5))//substr ROLE_
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
