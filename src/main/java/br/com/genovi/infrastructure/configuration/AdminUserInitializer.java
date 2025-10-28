package br.com.genovi.infrastructure.configuration;

import br.com.genovi.domain.enums.EnumRole;
import br.com.genovi.domain.models.Usuario;
import br.com.genovi.infrastructure.repository.UsuarioRepository;
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
            if (usuarioRepository.findByEmail("admin@admin.com").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setEmail("admin@admin.com");
                admin.setSenha(passwordEncoder.encode("1234"));
                admin.setEnumRoles(Set.of(EnumRole.ROLE_ADMIN, EnumRole.ROLE_USER));

                usuarioRepository.save(admin);
                System.out.println("✔️ Admin user created with email 'admin@admin.com' and password '1234'");
            }
        };
    }
}