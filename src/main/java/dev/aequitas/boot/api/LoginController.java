package dev.aequitas.boot.api;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@RestController
@RequestMapping(path = "/api/login")
public class LoginController {

    public static final String secretKey = "secret";

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
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
