package ph.mahvine.karappatan.repository;

import ph.mahvine.karappatan.domain.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Module entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    @Query(value = "select distinct krptn_module from Module krptn_module left join fetch krptn_module.questions left join fetch krptn_module.annexes left join fetch krptn_module.recommendations",
        countQuery = "select count(distinct krptn_module) from Module krptn_module")
    Page<Module> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct krptn_module from Module krptn_module left join fetch krptn_module.questions left join fetch krptn_module.annexes left join fetch krptn_module.recommendations")
    List<Module> findAllWithEagerRelationships();

    @Query("select krptn_module from Module krptn_module left join fetch krptn_module.questions left join fetch krptn_module.annexes left join fetch krptn_module.recommendations where krptn_module.id =:id")
    Optional<Module> findOneWithEagerRelationships(@Param("id") Long id);

}
