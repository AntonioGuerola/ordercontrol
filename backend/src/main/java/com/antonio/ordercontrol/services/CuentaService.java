package com.antonio.ordercontrol.services;

import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.models.Comanda;
import com.antonio.ordercontrol.models.Cuenta;
import com.antonio.ordercontrol.models.EstadoComanda;
import com.antonio.ordercontrol.models.Mesa;
import com.antonio.ordercontrol.repositories.ComandaProductoRepository;
import com.antonio.ordercontrol.repositories.ComandaRepository;
import com.antonio.ordercontrol.repositories.CuentaRepository;
import com.antonio.ordercontrol.repositories.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class CuentaService {
    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ComandaRepository comandaRepository;
    @Autowired
    private MesaRepository mesaRepository;

    public List<Cuenta> getAllCuentas() {
        return cuentaRepository.findAll();
    }

    public Cuenta getCuentaById(Long id) throws RecordNotFoundException {
        return cuentaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No existe Cuenta con id: ", id));
    }

    public Cuenta createCuenta(Cuenta cuenta){
        return  cuentaRepository.save(cuenta);
    }

    public Cuenta generarCuentaParaMesa(Long idMesa) {
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

        return cuentaRepository.save(cuenta);
    }

    public Cuenta updateCuenta(Long id, Cuenta nuevaCuenta) throws RecordNotFoundException {
        Cuenta cuenta =  getCuentaById(id);
        cuenta.setIdMesa(nuevaCuenta.getIdMesa());
        cuenta.setSumaTotal(nuevaCuenta.getSumaTotal());
        cuenta.setHoraCobro(nuevaCuenta.getHoraCobro());
        cuenta.setMetodoPago(nuevaCuenta.getMetodoPago());
        return cuentaRepository.save(cuenta);
    }

    public void  deleteCuenta(Long id) throws RecordNotFoundException {
        Cuenta cuenta = getCuentaById(id);
        cuentaRepository.delete(cuenta);
    }

    public List<Cuenta>  getCuentasByIdMesa(Long idMesa) {
        return  cuentaRepository.findByIdMesa(idMesa);
    }
}
