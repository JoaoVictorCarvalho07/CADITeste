package br.org.cadi.academic;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/turmas")
@RequiredArgsConstructor
@Tag(name = "Turmas", description = "Endpoints for managing classes (turmas)")
public class TurmaController {

    private final TurmaService service;

    @Operation(summary = "List all classes", description = "Returns a list of all classes in the system")
    @GetMapping
    public List<Turma> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Create a new class", description = "Creates a new class (turma). Requires SECRETARIA or ADMIN role")
    @PreAuthorize("hasAnyRole('SECRETARIA', 'ADMIN')")
    @PostMapping
    public ResponseEntity<Turma> create(@RequestBody Turma turma) {
        return ResponseEntity.ok(service.save(turma));
    }

    @Operation(summary = "Get class by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Turma> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
