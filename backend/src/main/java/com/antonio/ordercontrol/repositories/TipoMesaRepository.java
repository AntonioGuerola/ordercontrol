package com.antonio.ordercontrol.repositories;

import com.antonio.ordercontrol.models.TipoMesa;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoMesaRepository extends JpaRepository<TipoMesa, Long> {
    Optional<TipoMesa> findByNombreIgnoreCase(String nombre);
}
