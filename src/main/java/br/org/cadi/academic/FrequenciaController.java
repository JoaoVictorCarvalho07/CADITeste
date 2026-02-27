package br.org.cadi.academic;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/frequencia")
@RequiredArgsConstructor
@Tag(name = "Frequência", description = "Endpoints for recording and consulting student frequency")
public class FrequenciaController {

    private final FrequenciaService service;

    @Operation(summary = "Register class and attendance", description = "Creates a new class record and its student attendance. Requires PROFESSOR or ADMIN role")
    @PreAuthorize("hasAnyRole('PROFESSOR', 'ADMIN')")
    @PostMapping("/aula")
    public ResponseEntity<Aula> registerAula(@RequestBody Aula aula) {
        return ResponseEntity.ok(service.saveAula(aula));
    }

    @Operation(summary = "Record attendance", description = "Records attendance for a specific class. Requires PROFESSOR or ADMIN role")
    @PreAuthorize("hasAnyRole('PROFESSOR', 'ADMIN')")
    @PostMapping("/presenca")
    public ResponseEntity<List<Presenca>> recordAttendance(@RequestBody List<Presenca> presencas) {
        return ResponseEntity.ok(service.savePresencas(presencas));
    }

    @Operation(summary = "Get student frequency", description = "Returns attendance history for a student")
    @GetMapping("/aluno/{studentId}")
    public List<Presenca> getStudentFrequency(@PathVariable Long studentId) {
        return service.findByStudentId(studentId);
    }
}
