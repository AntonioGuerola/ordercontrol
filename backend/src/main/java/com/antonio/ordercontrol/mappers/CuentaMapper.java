package com.antonio.ordercontrol.mappers;

import com.antonio.ordercontrol.dtos.CuentaDTO;
import com.antonio.ordercontrol.models.Cuenta;
import com.antonio.ordercontrol.models.Mesa;
import com.antonio.ordercontrol.repositories.MesaRepository;

public class CuentaMapper {
    private MesaRepository mesaRepository;

    public CuentaDTO toCuentaDTO(Cuenta cuenta) {
        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setId(cuenta.getId().longValue());
        cuentaDTO.setIdMesa(cuenta.getIdMesa().getId().longValue());
        cuentaDTO.setSumaTotal(cuenta.getSumaTotal());
        cuentaDTO.setHoraCobro(cuenta.getHoraCobro());
        cuentaDTO.setMetodoPago(cuenta.getMetodoPago());
        return cuentaDTO;
    }

    public Cuenta toEntity(CuentaDTO cuentaDTO) {
        Cuenta cuenta = new Cuenta();

        if (cuentaDTO.getId() != null ) {
            cuenta.setId(cuentaDTO.getId().intValue());
        }

        Mesa mesa = mesaRepository.findById(cuentaDTO.getIdMesa()).orElseThrow(() -> new RuntimeException("No hay Mesa para el id: "  + cuentaDTO.getIdMesa()));
        cuenta.setIdMesa(mesa);
        cuenta.setSumaTotal(cuentaDTO.getSumaTotal());
        cuenta.setHoraCobro(cuentaDTO.getHoraCobro());
        cuenta.setMetodoPago(cuentaDTO.getMetodoPago());
        return cuenta;
    }
}
