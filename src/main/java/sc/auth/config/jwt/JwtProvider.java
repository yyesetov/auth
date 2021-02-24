package sc.auth.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sc.auth.entity.UserEntity;
import sc.auth.repository.CasesRolesEntityRepository;
import sc.auth.repository.UserEntityRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Log
public class JwtProvider {

    @Value("${security.jwt.token.secret-key}")
    private String jwtSecret;
    private UserEntityRepository userEntityRepository;
    private CasesRolesEntityRepository casesRolesEntityRepository;

    @Autowired
    public JwtProvider(UserEntityRepository userEntityRepository, CasesRolesEntityRepository casesRolesEntityRepository){
        this.userEntityRepository = userEntityRepository;
        this.casesRolesEntityRepository = casesRolesEntityRepository;
    }

    public String generateToken(String login) {
        Map<String, Object> tokenData = new HashMap<>();
        List<UserEntity> userEntity = userEntityRepository.findAllByLogin(login);
        List<String> cases = casesRolesEntityRepository.findAllCasesByRoleId(userEntity.get(0).getRoleEntity().getId());
        tokenData.put("role", userEntity.get(0).getRoleEntity().getCode());
        tokenData.put("roleId", userEntity.get(0).getRoleEntity().getId());
        tokenData.put("login", userEntity.get(0).getLogin());
        tokenData.put("password", userEntity.get(0).getPassword());
        tokenData.put("cases", cases);
        Date date = Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(login)
                .setClaims(tokenData)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.severe("invalid token");
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
