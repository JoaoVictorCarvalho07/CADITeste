package br.org.cadi.communication;

import br.org.cadi.academic.TurmaRepository;
import br.org.cadi.auth.Role;
import br.org.cadi.auth.RoleRepository;
import br.org.cadi.communication.dto.MuralPostRequest;
import br.org.cadi.communication.dto.MuralPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MuralService {

    private final MuralPostRepository repository;
    private final RoleRepository roleRepository;
    private final TurmaRepository turmaRepository;

    @Transactional
    public MuralPostResponse createPost(MuralPostRequest request) {
        Set<Role> roles = new HashSet<>();
        if (request.getTargetRoles() != null) {
            request.getTargetRoles().forEach(name -> roleRepository.findByName(name).ifPresent(roles::add));
        }

        MuralPost post = MuralPost.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .createdAt(LocalDateTime.now())
                .targetRoles(roles)
                .global(request.isGlobal())
                .build();

        if (request.getTargetTurmaIds() != null) {
            post.setTargetTurmas(new HashSet<>(turmaRepository.findAllById(request.getTargetTurmaIds())));
        }

        return mapToResponse(repository.save(post));
    }

    public List<MuralPostResponse> getVisiblePosts(List<String> roleNames, List<Long> turmaIds) {
        return repository.findVisiblePosts(roleNames, turmaIds).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private MuralPostResponse mapToResponse(MuralPost post) {
        return MuralPostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .createdAt(post.getCreatedAt())
                .authorName(post.getAuthor() != null ? post.getAuthor().getUsername() : "Sistema")
                .build();
    }
}
