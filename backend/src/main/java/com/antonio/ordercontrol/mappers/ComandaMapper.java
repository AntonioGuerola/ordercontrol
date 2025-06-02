package com.antonio.ordercontrol.mappers;

import com.antonio.ordercontrol.dtos.ComandaCreatedDTO;
import com.antonio.ordercontrol.dtos.ComandaDTO;
import com.antonio.ordercontrol.models.Comanda;
import com.antonio.ordercontrol.models.Mesa;
import com.antonio.ordercontrol.models.Usuario;

public class ComandaMapper {
    public static ComandaDTO toComandaDTO(Comanda comanda){
        ComandaDTO comandaDTO = new ComandaDTO();
        comandaDTO.setId(comanda.getId().longValue());
        comandaDTO.setFechaCreacion(comanda.getFechaCreacion());
        comandaDTO.setEstado(comanda.getEstado());
        comandaDTO.setIdMesa(comanda.getIdMesa().getId().longValue());
        comandaDTO.setIdUsuario(comanda.getIdUsuario() != null ? comanda.getIdUsuario().getId().longValue() : null);
        comandaDTO.setIdCuenta(comanda.getIdCuenta());
        return comandaDTO;
    }

    public static Comanda fromCreateComandaDTO(ComandaCreatedDTO comandaDTO, Mesa mesa, Usuario usuario){
        Comanda comanda = new Comanda();
        comanda.setIdMesa(mesa);
        comanda.setEstado(comandaDTO.getEstadoComanda().name());
        comanda.setIdUsuario(usuario);
        comanda.setIdCuenta(comandaDTO.getIdCuenta());
        return comanda;
    }
}
