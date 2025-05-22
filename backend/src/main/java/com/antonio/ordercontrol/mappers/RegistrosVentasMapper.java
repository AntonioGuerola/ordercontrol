package com.antonio.ordercontrol.mappers;

import com.antonio.ordercontrol.dtos.RegistrosVentasDTO;
import com.antonio.ordercontrol.models.RegistrosVenta;

import java.util.List;
import java.util.stream.Collectors;

public class RegistrosVentasMapper {
    public RegistrosVentasDTO toRegistrosVentasDTO(RegistrosVenta registrosVenta) {
        RegistrosVentasDTO registrosVentasDTO = new RegistrosVentasDTO();
        registrosVentasDTO.setId(registrosVenta.getId() != null ? registrosVenta.getId().longValue() : null);
        registrosVentasDTO.setTipoPeriodo(registrosVenta.getTipoPeriodo());
        registrosVentasDTO.setFechaInicio(registrosVenta.getFechaInicio());
        registrosVentasDTO.setFechaFin(registrosVenta.getFechaFin());
        registrosVentasDTO.setMontoTotal(registrosVenta.getMontoTotal());
        return registrosVentasDTO;
    }

    public RegistrosVenta toRegistrosVenta(RegistrosVentasDTO registrosVentasDTO) {
        RegistrosVenta registrosVenta = new RegistrosVenta();

        if (registrosVentasDTO.getId() != null) {
            registrosVenta.setId(registrosVentasDTO.getId().intValue());
        }

        registrosVenta.setTipoPeriodo(registrosVentasDTO.getTipoPeriodo().name());
        registrosVenta.setFechaInicio(registrosVentasDTO.getFechaInicio());
        registrosVenta.setFechaFin(registrosVentasDTO.getFechaFin());
        registrosVenta.setMontoTotal(registrosVentasDTO.getMontoTotal());
        return registrosVenta;
    }

    public List<RegistrosVentasDTO> toRegistroVentasDTOList(List<RegistrosVenta> registrosVentas) {
        return registrosVentas.stream().map(this::toRegistrosVentasDTO).collect(Collectors.toList());
    }

    public List<RegistrosVenta> toRegistroVentasList(List<RegistrosVentasDTO>  registrosVentasDTO) {
        return registrosVentasDTO.stream().map(this::toRegistrosVenta).collect(Collectors.toList());
    }
}
