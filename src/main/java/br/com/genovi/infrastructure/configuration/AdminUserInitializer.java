package br.com.genovi.infrastructure.configuration;

import br.com.genovi.domain.enums.Role;
import br.com.genovi.domain.models.Usuario;
import br.com.genovi.infrastructure.repositories.UsuarioRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AdminUserInitializer {

    @Bean
    public ApplicationRunner adminInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setSenha(passwordEncoder.encode("1234"));
                admin.setRoles(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER));

                usuarioRepository.save(admin);
                System.out.println("✔️ Admin user created with username 'admin' and password '1234'");
            }
        };
    }
}
