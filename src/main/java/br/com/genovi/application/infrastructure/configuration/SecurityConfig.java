package br.com.genovi.application.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //Bean apenas para facilitar os testes via requisição API
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Desativa proteção CSRF (opcional para API REST)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 🔥 Libera todos os endpoints
                )
                .formLogin().disable() // Desativa tela de login
                .httpBasic().disable(); // Desativa autenticação básica

        return http.build();
    }
}
