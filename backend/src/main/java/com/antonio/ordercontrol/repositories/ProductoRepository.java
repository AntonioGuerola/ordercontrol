package com.antonio.ordercontrol.repositories;

import com.antonio.ordercontrol.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByDisponibleTrue();
    List<Producto> findByTipoIgnoreCase(String tipo);
    List<Producto> findByCategoria_Id(Long idCategoria);
}
