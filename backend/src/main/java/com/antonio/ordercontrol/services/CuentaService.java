package com.antonio.ordercontrol.services;

import com.antonio.ordercontrol.dtos.CuentaDTO;
import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.mappers.CuentaMapper;
import com.antonio.ordercontrol.models.Comanda;
import com.antonio.ordercontrol.models.Cuenta;
import com.antonio.ordercontrol.models.EstadoComanda;
import com.antonio.ordercontrol.models.Mesa;
import com.antonio.ordercontrol.repositories.ComandaRepository;
import com.antonio.ordercontrol.repositories.CuentaRepository;
import com.antonio.ordercontrol.repositories.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaService {
    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ComandaRepository comandaRepository;

    @Autowired
    private MesaRepository mesaRepository;

    private CuentaMapper cuentaMapper;

    public List<CuentaDTO> getAllCuentas() {
        return cuentaRepository.findAll().stream().map(cuentaMapper::toCuentaDTO).collect(Collectors.toList());
    }

    public CuentaDTO getCuentaById(Long id) throws RecordNotFoundException {
        Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No existe Cuenta con id: ", id));
        return cuentaMapper.toCuentaDTO(cuenta);
    }

    public CuentaDTO createCuenta(CuentaDTO cuentaDTO){
        Cuenta cuenta = cuentaMapper.toEntity(cuentaDTO);
        return cuentaMapper.toCuentaDTO(cuentaRepository.save(cuenta));
    }

    public CuentaDTO generarCuentaParaMesa(Long idMesa) {
        Mesa mesa = mesaRepository.findById(idMesa).orElseThrow(() -> new RuntimeException("Mesa no encontrada."));

        List<Comanda> comandas = comandaRepository.findByIdMesaYEstado(mesa, EstadoComanda.CERRADA);

        BigDecimal sumaTotal = comandas.stream().flatMap(comanda -> comanda.getComandaproductos().stream())
                .map(comandaproducto -> comandaproducto.getPrecioUnitario()
                        .multiply(BigDecimal.valueOf(comandaproducto.getCantidad()))).reduce(BigDecimal.ZERO, BigDecimal::add);

        Cuenta cuenta = new Cuenta();
        cuenta.setIdMesa(mesa);
        cuenta.setSumaTotal(sumaTotal);
        cuenta.setHoraCobro(Instant.now());

        for (Comanda comanda : comandas){
            comanda.setEstado(EstadoComanda.CERRADA.name());
        }

        comandaRepository.saveAll(comandas);

        return cuentaMapper.toCuentaDTO(cuentaRepository.save(cuenta));
    }

    public CuentaDTO updateCuenta(Long id, CuentaDTO nuevaCuenta) throws RecordNotFoundException {
        Cuenta cuenta =  cuentaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No hay Cuenta para el id: ", id));

        Mesa mesa = mesaRepository.findById(nuevaCuenta.getIdMesa()).orElseThrow(() -> new  RecordNotFoundException("No hay Mesa para el id: ", nuevaCuenta.getIdMesa()));

        cuenta.setIdMesa(mesa);
        cuenta.setSumaTotal(nuevaCuenta.getSumaTotal());
        cuenta.setHoraCobro(nuevaCuenta.getHoraCobro());
        cuenta.setMetodoPago(nuevaCuenta.getMetodoPago());

        return cuentaMapper.toCuentaDTO(cuentaRepository.save(cuenta));
    }

    public void  deleteCuenta(Long id) throws RecordNotFoundException {
        Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No hay Cuenta para el id: ", id));
        cuentaRepository.delete(cuenta);
    }

    public List<CuentaDTO> getCuentasByIdMesa(Long idMesa) {
        return cuentaRepository.findByIdMesa(idMesa).stream().map(cuentaMapper::toCuentaDTO).collect(Collectors.toList());
    }
}
