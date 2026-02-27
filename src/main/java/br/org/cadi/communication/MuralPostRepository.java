package br.org.cadi.communication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MuralPostRepository extends JpaRepository<MuralPost, Long> {

    @Query("SELECT DISTINCT p FROM MuralPost p LEFT JOIN p.targetRoles r LEFT JOIN p.targetTurmas t " +
           "WHERE p.global = true OR r.name IN :roleNames OR t.id IN :turmaIds")
    List<MuralPost> findVisiblePosts(List<String> roleNames, List<Long> turmaIds);
}
