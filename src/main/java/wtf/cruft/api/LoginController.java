package wtf.cruft.api;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;


@RestController
@RequestMapping(path = "/api/login")
public class LoginController {

    public static final String secretKey = "secret";

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody LoginRequest request) {
        if ("user".equals(request.getUser()) && "pass".equals(request.getPassword())) {
            LoginResponse r = new LoginResponse();

            Instant i = Instant.now().plus(30, ChronoUnit.MINUTES);
            Date d = Date.from(i);

            String token = Jwts.builder()
                    .setSubject("user")
                    .signWith(SignatureAlgorithm.HS512, secretKey)
                    .setExpiration(d)
                    .compact();
            r.setToken(token);
            return ResponseEntity.ok(r);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
