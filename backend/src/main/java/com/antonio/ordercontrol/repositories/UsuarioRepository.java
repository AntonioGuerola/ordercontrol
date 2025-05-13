package com.antonio.ordercontrol.repositories;

import com.antonio.ordercontrol.models.RolUsuario;
import com.antonio.ordercontrol.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    List<Usuario> findByRol(RolUsuario rol);
}
