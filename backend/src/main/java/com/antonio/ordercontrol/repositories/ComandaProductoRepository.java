package com.antonio.ordercontrol.repositories;

import com.antonio.ordercontrol.models.Comandaproducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComandaProductoRepository extends JpaRepository<Comandaproducto, Long> {
    List<Comandaproducto> findByComandaId(Long idComanda);
}
