package com.antonio.ordercontrol.repositories;

import com.antonio.ordercontrol.models.RegistrosVenta;
import com.antonio.ordercontrol.models.TipoPeriodo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface RegistrosVentaRepository extends JpaRepository<RegistrosVenta,Long> {
    List<RegistrosVenta> findAllByTipoPeriodo(TipoPeriodo tipoPeriodo);
    RegistrosVenta findByFecha(Date fecha);
}
