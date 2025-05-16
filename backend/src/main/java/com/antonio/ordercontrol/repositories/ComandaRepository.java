package com.antonio.ordercontrol.repositories;

import com.antonio.ordercontrol.models.Comanda;
import com.antonio.ordercontrol.models.EstadoComanda;
import com.antonio.ordercontrol.models.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComandaRepository extends JpaRepository<Comanda, Long> {
    List<Comanda> findByEstadoIgnoreCase(EstadoComanda estado);
    List<Comanda> findByIdMesa(Long mesa);
    List<Comanda> findByIdUsuario(Long idUsuario);
    List<Comanda> findByIdMesaYEstado(Mesa idMesa, EstadoComanda estado);
    Optional<Comanda> findByIdMesaYEstado(Long idMesa, EstadoComanda estado);
}
