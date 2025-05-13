package com.antonio.ordercontrol.repositories;

import com.antonio.ordercontrol.models.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MesaRepository extends JpaRepository<Mesa, Long> {
    List<Mesa> findByEstadoIgnoreCase(String estado);
    List<Mesa> findByTipoIgnoreCase(String tipo);
    List<Mesa> findByNumMesa(int numMesa);
}
