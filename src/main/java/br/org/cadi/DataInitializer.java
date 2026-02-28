package br.org.cadi;

import br.org.cadi.auth.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final RoleSeeder roleSeeder;

    @Override
    public void run(String... args) {

        HashSet<Role> roles = new HashSet<>();
        Role adm =  roleRepository.findByName("ADMIN").orElse(null);

        roles.add(adm);


        if (!userRepository.existsByEmail("admin@cadi.org")) {

            User admin = User.builder()
                    .username("admin")
                    .email("adm")
                    .phone("+5500000000000")
                    .password(passwordEncoder.encode("admin123"))
                    .roles(roles)
                    .build();

            userRepository.save(admin);

            System.out.println("ADMIN criado com sucesso.");
        }
    }
}