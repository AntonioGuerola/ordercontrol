package com.antonio.ordercontrol.services;

import com.antonio.ordercontrol.dtos.CuentaDTO;
import com.antonio.ordercontrol.dtos.CuentaProductoDTO;
import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.mappers.CuentaMapper;
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

import java.io.IOException;
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

    @Autowired
    private ComandaProductoRepository comandaProductoRepository;

    @Autowired
    private RegistroCSVService registroCSVService;

    @Autowired
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
        Mesa mesa = mesaRepository.findById(idMesa)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada."));

        List<Comanda> comandas = comandaRepository.findByIdMesa_Id(idMesa);

        boolean sinProductos = comandas.isEmpty() || comandas.stream()
                .allMatch(c -> c.getComandaproductos().isEmpty());

        if (sinProductos) {
            throw new RuntimeException("No hay productos en la mesa para cobrar.");
        }

        BigDecimal sumaTotal = comandas.stream()
                .flatMap(c -> c.getComandaproductos().stream())
                .map(cp -> cp.getPrecioUnitario().multiply(BigDecimal.valueOf(cp.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Cuenta cuenta = new Cuenta();
        cuenta.setIdMesa(mesa);
        cuenta.setSumaTotal(sumaTotal);
        cuenta.setHoraCobro(Instant.now());

        Cuenta cuentaGuardada = cuentaRepository.save(cuenta);

        try {
            registroCSVService.procesarCuenta(cuentaGuardada);
        } catch (IOException e) {
            throw new RuntimeException("Error al generar CSV: " + e.getMessage(), e);
        }

        List<CuentaProductoDTO> productos = comandas.stream()
                .flatMap(c -> c.getComandaproductos().stream())
                .map(cp -> new CuentaProductoDTO(
                        cp.getProducto().getNombre(),
                        cp.getPrecioUnitario(),
                        cp.getCantidad(),
                        cp.getProducto().getTipo()
                ))
                .collect(Collectors.toList());

        comandaRepository.deleteAllInBatch(comandas);

        cuentaRepository.delete(cuentaGuardada);

        mesa.setEstado("LIBRE");
        mesaRepository.save(mesa);

        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setIdMesa(mesa.getId().longValue());
        cuentaDTO.setSumaTotal(sumaTotal);
        cuentaDTO.setProductos(productos);
        return cuentaDTO;
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
        return cuentaRepository.findByIdMesa_Id(idMesa).stream().map(cuentaMapper::toCuentaDTO).collect(Collectors.toList());
    }

    public List<CuentaProductoDTO> getProductosConsumidosPorMesa(Long idMesa) {
        List<Comanda> comandas = comandaRepository.findByIdMesa_Id(idMesa);

        return comandas.stream()
                .flatMap(comanda -> comanda.getComandaproductos().stream())
                .map(cp -> new CuentaProductoDTO(
                        cp.getProducto().getNombre(),
                        cp.getPrecioUnitario(),
                        cp.getCantidad(),
                        cp.getProducto().getTipo()
                ))
                .collect(Collectors.toList());
    }

    public CuentaDTO generarCuentaTemporalParaImpresion(Long idMesa) {
        Mesa mesa = mesaRepository.findById(idMesa)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada."));

        List<Comanda> comandas = comandaRepository.findByIdMesa_Id(idMesa);

        boolean sinProductos = comandas.isEmpty() || comandas.stream()
                .allMatch(c -> c.getComandaproductos().isEmpty());

        if (sinProductos) {
            throw new RuntimeException("No hay productos en la mesa para imprimir.");
        }

        BigDecimal sumaTotal = comandas.stream()
                .flatMap(c -> c.getComandaproductos().stream())
                .map(cp -> cp.getPrecioUnitario().multiply(BigDecimal.valueOf(cp.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<CuentaProductoDTO> productos = comandas.stream()
                .flatMap(c -> c.getComandaproductos().stream())
                .map(cp -> new CuentaProductoDTO(
                        cp.getProducto().getNombre(),
                        cp.getPrecioUnitario(),
                        cp.getCantidad(),
                        cp.getProducto().getTipo()
                )).collect(Collectors.toList());

        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setIdMesa(mesa.getId().longValue());
        cuentaDTO.setSumaTotal(sumaTotal);
        cuentaDTO.setHoraCobro(Instant.now());
        cuentaDTO.setProductos(productos);

        return cuentaDTO;
    }

}
