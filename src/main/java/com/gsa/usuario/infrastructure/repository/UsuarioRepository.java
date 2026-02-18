package com.gsa.usuario.infrastructure.repository;

import com.Gsa.aprendendostring.infrastructure.entity.usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<usuario,Long> {

    boolean existsByEmail(String email);

    Optional<usuario> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);

}
