package com.antonio.ordercontrol.repositories;

import com.antonio.ordercontrol.models.Comanda;
import com.antonio.ordercontrol.models.EstadoComanda;
import com.antonio.ordercontrol.models.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComandaRepository extends JpaRepository<Comanda, Long> {
    List<Comanda> findByEstado(EstadoComanda estado);
    List<Comanda> findByIdMesa_Id(Long mesa);
    List<Comanda> findByIdUsuario_Id(Long idUsuario);
    List<Comanda> findByIdMesaAndEstado(Mesa idMesa, EstadoComanda estado);
    Optional<Comanda> findByIdMesa_IdAndEstado(Long idMesa, EstadoComanda estado);
}
