package com.coderforfun.forohub.domain.curso;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    Page<Curso> findByActivoTrue(Pageable paginacion);

    Boolean existsByNombre (String nombre);

    Curso findByNombre(String nombre);
}
