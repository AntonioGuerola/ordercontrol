package com.antonio.ordercontrol.mappers;

import com.antonio.ordercontrol.dtos.CuentaDTO;
import com.antonio.ordercontrol.dtos.CuentaProductoDTO;
import com.antonio.ordercontrol.models.Cuenta;
import com.antonio.ordercontrol.models.Mesa;
import com.antonio.ordercontrol.repositories.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CuentaMapper {
    @Autowired
    private MesaRepository mesaRepository;

    public CuentaDTO toCuentaDTO(Cuenta cuenta) {
        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setId(cuenta.getId().longValue());
        cuentaDTO.setIdMesa(cuenta.getIdMesa().getId().longValue());
        cuentaDTO.setSumaTotal(cuenta.getSumaTotal());
        cuentaDTO.setHoraCobro(cuenta.getHoraCobro());
        cuentaDTO.setMetodoPago(cuenta.getMetodoPago());

        List<CuentaProductoDTO> productos = cuenta.getIdMesa().getComandas().stream()
                .flatMap(comanda -> comanda.getComandaproductos().stream())
                .map(cp -> new CuentaProductoDTO(
                        cp.getProducto().getNombre(),
                        cp.getPrecioUnitario(),
                        cp.getCantidad(),
                        cp.getProducto().getTipo()
                ))
                .collect(Collectors.toList());

        cuentaDTO.setProductos(productos);
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
