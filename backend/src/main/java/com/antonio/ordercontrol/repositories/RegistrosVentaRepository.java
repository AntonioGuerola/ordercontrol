package com.antonio.ordercontrol.repositories;

import com.antonio.ordercontrol.models.RegistrosVenta;
import com.antonio.ordercontrol.models.TipoPeriodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RegistrosVentaRepository extends JpaRepository<RegistrosVenta,Long> {
    List<RegistrosVenta> findAllByTipoPeriodo(TipoPeriodo tipoPeriodo);
    Optional<RegistrosVenta> findByFechaInicio(LocalDate fechaInicio);
    Optional<RegistrosVenta> findByFechaFin(LocalDate fechaFin);

    @Query("SELECT r FROM RegistrosVenta r WHERE :fecha BETWEEN r.fechaInicio AND r.fechaFin")
    List<RegistrosVenta> findByFechaDentroDelRango(@Param("fecha") LocalDate fecha);
}
