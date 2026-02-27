package br.org.cadi.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        List<String> roles = List.of("ROLE_ADMIN", "ROLE_PROFESSOR", "ROLE_ALUNO", "ROLE_SECRETARIA", "ROLE_FINANCEIRO", "ROLE_PSICOLOGO");

        roles.forEach(roleName -> {
            if (roleRepository.findByName(roleName).isEmpty()) {
                roleRepository.save(new Role(null, roleName));
            }
        });
    }
}
