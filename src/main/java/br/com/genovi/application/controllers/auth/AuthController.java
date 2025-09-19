package br.com.genovi.application.controllers.auth;

import br.com.genovi.infrastructure.security.CustomUserDetailsService;
import br.com.genovi.infrastructure.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthController(
            AuthenticationManager authenticationManager,
            CustomUserDetailsService userDetailsService,
            JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> authRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.get("email"),
                        authRequest.get("senha")
                )
        );

        UserDetails user = userDetailsService.loadUserByUsername(authRequest.get("email"));
        String token = jwtService.generateToken(Map.of(), user.getUsername());

        return ResponseEntity.ok(Map.of("token", token));
    }
}
