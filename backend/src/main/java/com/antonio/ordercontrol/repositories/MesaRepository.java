package com.antonio.ordercontrol.repositories;

import com.antonio.ordercontrol.models.Mesa;
import com.antonio.ordercontrol.models.TipoMesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MesaRepository extends JpaRepository<Mesa, Long> {
    List<Mesa> findByEstadoIgnoreCase(String estado);
    List<Mesa> findByTipo(TipoMesa tipo);
    List<Mesa> findByNumMesa(int numMesa);
}
