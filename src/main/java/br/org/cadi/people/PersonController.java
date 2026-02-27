package br.org.cadi.people;

import br.org.cadi.auth.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/people")
@RequiredArgsConstructor
@Tag(name = "People", description = "Endpoints for managing all types of people (Students, Professors, Donors, etc.)")
public class PersonController {

    private final PersonService service;

    @Operation(summary = "List people by type", description = "Returns a list of people filtered by their type (STUDENT, PROFESSOR, etc.)")
    @GetMapping("/type/{type}")
    public List<Person> findByType(@PathVariable PersonType type) {
        return service.findByType(type);
    }

    @Operation(summary = "Register a person", description = "Creates a new person record. Requires SECRETARIA or ADMIN role")
    @PreAuthorize("hasAnyRole('SECRETARIA', 'ADMIN')")
    @PostMapping
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return ResponseEntity.ok(service.save(person));
    }

    @Operation(summary = "Get person by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update person data")
    @PreAuthorize("hasAnyRole('SECRETARIA', 'ADMIN') or @personSecurity.isSelf(#id)")
    @PutMapping("/{id}")
    public ResponseEntity<Person> update(@PathVariable Long id, @RequestBody Person personDetails) {
        return service.update(id, personDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
