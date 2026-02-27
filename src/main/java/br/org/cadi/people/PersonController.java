package br.org.cadi.people;

import br.org.cadi.people.dto.PersonRequest;
import br.org.cadi.people.dto.PersonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/type/{type}")
    public List<PersonResponse> findByType(@PathVariable PersonType type) {
        return service.findByType(type);
    }

    @Operation(summary = "Register a person", description = "Creates a new person record. Requires SECRETARIA or ADMIN role")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Person successfully created"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasAnyRole('SECRETARIA', 'ADMIN')")
    @PostMapping
    public ResponseEntity<PersonResponse> create(@Valid @RequestBody PersonRequest person) {
        return ResponseEntity.ok(service.save(person));
    }

    @Operation(summary = "Get person by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved person"),
        @ApiResponse(responseCode = "404", description = "Person not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PersonResponse> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update person data", description = "Updates an existing person's information. Accessible by staff or the user themselves.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Person successfully updated"),
        @ApiResponse(responseCode = "403", description = "Forbidden - Not authorized to update this person"),
        @ApiResponse(responseCode = "404", description = "Person not found")
    })
    @PreAuthorize("hasAnyRole('SECRETARIA', 'ADMIN') or @personSecurity.isSelf(#id)")
    @PutMapping("/{id}")
    public ResponseEntity<PersonResponse> update(@PathVariable Long id, @Valid @RequestBody PersonRequest personDetails) {
        return service.update(id, personDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
