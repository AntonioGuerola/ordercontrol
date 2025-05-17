package com.antonio.ordercontrol.mappers;

import com.antonio.ordercontrol.dtos.TipoMesaDTO;
import com.antonio.ordercontrol.models.TipoMesa;

public class TipoMesaMapper {
    public static TipoMesaDTO toTipoMesaDTO(TipoMesa tipoMesa) {
        return new TipoMesaDTO(tipoMesa.getId().longValue(), tipoMesa.getNombre());
    }

    public static TipoMesa toTipoMesa(TipoMesaDTO tipoMesaDTO) {
        return new TipoMesa(tipoMesaDTO.getId(), tipoMesaDTO.getNombre());
    }

    public static void updateTipoMesa(TipoMesaDTO tipoMesaDTO, TipoMesa tipoMesa) {
        tipoMesa.setNombre(tipoMesaDTO.getNombre());
    }
}
