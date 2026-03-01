package br.org.cadi.config;

import br.org.cadi.academic.*;
import br.org.cadi.auth.*;
import br.org.cadi.communication.*;
import br.org.cadi.finance.*;
import br.org.cadi.people.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final TurmaRepository turmaRepository;
    private final MatriculaRepository matriculaRepository;
    private final ProjectRepository projectRepository;
    private final MuralPostRepository muralPostRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        if (roleRepository.count() > 0) {
            log.info("Data already initialized. Skipping...");
            return;
        }

        log.info("Initializing CADI system data...");

        // 1. Roles
        Role adminRole = createRole("ROLE_ADMIN");
        Role profRole = createRole("ROLE_PROFESSOR");
        Role studentRole = createRole("ROLE_ALUNO");
        Role secretRole = createRole("ROLE_SECRETARIA");
        Role financeRole = createRole("ROLE_FINANCEIRO");
        Role psychRole = createRole("ROLE_PSICOLOGO");

        // 2. Admin User
        User adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .email("admin@cadi.org.br")
                .role(adminRole)
                .build();
        userRepository.save(adminUser);

        Person adminPerson = Person.builder()
                .name("Administrador do Sistema")
                .email(adminUser.getEmail())
                .type(PersonType.STAFF)
                .user(adminUser)
                .build();
        personRepository.save(adminPerson);

        // 3. Professor
        User profUser = User.builder()
                .username("prof.ricardo")
                .password(passwordEncoder.encode("prof123"))
                .email("ricardo@cadi.org.br")
                .role(profRole)
                .build();
        userRepository.save(profUser);

        Person professor = Person.builder()
                .name("Ricardo Almeida")
                .email(profUser.getEmail())
                .phone("+55 11 98888-7777")
                .type(PersonType.PROFESSOR)
                .user(profUser)
                .build();
        personRepository.save(professor);

        // 4. Student
        User studentUser = User.builder()
                .username("aluno.gabriel")
                .password(passwordEncoder.encode("aluno123"))
                .email("gabriel@example.com")
                .role(studentRole)
                .build();
        userRepository.save(studentUser);

        Person student = Person.builder()
                .name("Gabriel Santos")
                .email(studentUser.getEmail())
                .birthDate(LocalDate.of(2010, 8, 20))
                .type(PersonType.STUDENT)
                .user(studentUser)
                .build();
        personRepository.save(student);

        // 5. Academic Data
        Turma turmaMusica = Turma.builder()
                .name("Oficina de Violão")
                .description("Iniciação musical através do violão para jovens")
                .schedule("Terça e Quinta, 15:00-17:00")
                .professor(professor)
                .active(true)
                .build();
        turmaRepository.save(turmaMusica);

        Matricula matricula = Matricula.builder()
                .student(student)
                .turma(turmaMusica)
                .enrollmentDate(LocalDate.now())
                .status("ACTIVE")
                .build();
        matriculaRepository.save(matricula);

        // 6. Finance Data
        Project project = Project.builder()
                .name("Inclusão Cultural")
                .description("Projeto para levar arte e cultura para periferias")
                .financialGoal(new BigDecimal("25000.00"))
                .active(true)
                .build();
        projectRepository.save(project);

        // 7. Communication Data
        MuralPost globalPost = MuralPost.builder()
                .title("Boas-vindas ao CADI!")
                .content("Seja bem-vindo ao novo portal do CADI. Aqui você encontrará todas as informações sobre suas atividades.")
                .createdAt(LocalDateTime.now())
                .global(true)
                .build();
        muralPostRepository.save(globalPost);

        MuralPost profPost = MuralPost.builder()
                .title("Reunião Pedagógica")
                .content("Lembramos a todos os professores que a reunião mensal será na próxima segunda-feira.")
                .createdAt(LocalDateTime.now())
                .targetRoles(Set.of(profRole))
                .global(false)
                .build();
        muralPostRepository.save(profPost);

        log.info("System data successfully initialized.");
    }

    private Role createRole(String name) {
        return roleRepository.save(new Role(null, name));
    }
}
