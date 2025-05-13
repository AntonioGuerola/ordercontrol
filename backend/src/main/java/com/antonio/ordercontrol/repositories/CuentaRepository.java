package com.antonio.ordercontrol.repositories;

import com.antonio.ordercontrol.models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta,Long> {
    List<Cuenta> findByIdMesa(Long idMesa);
}
