package com.antonio.ordercontrol.mappers;

import com.antonio.ordercontrol.dtos.MesaDTO;
import com.antonio.ordercontrol.models.Mesa;

public class MesaMapper {
    public static MesaDTO toMesaDTO(Mesa mesa) {
        return new MesaDTO(
                mesa.getId().longValue(),
                mesa.getTipo().getNombre(),
                mesa.getNumMesa(),
                mesa.getEstado(),
                mesa.getFechaHora()
        );
    }

    public static Mesa toMesa(MesaDTO mesaDTO) {
        Mesa mesa = new Mesa();

        if (mesaDTO.getId() != null) {
            mesa.setId(mesaDTO.getId().intValue());
        }

        mesa.setNumMesa(mesaDTO.getNumMesa());
        mesa.setEstado(mesaDTO.getEstado());
        mesa.setFechaHora(mesaDTO.getFechaHora());

        return mesa;
    }
}
