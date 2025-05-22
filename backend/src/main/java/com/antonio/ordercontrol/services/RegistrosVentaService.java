package com.antonio.ordercontrol.services;

import com.antonio.ordercontrol.dtos.RegistrosVentasDTO;
import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.mappers.RegistrosVentasMapper;
import com.antonio.ordercontrol.models.RegistrosVenta;
import com.antonio.ordercontrol.models.TipoPeriodo;
import com.antonio.ordercontrol.repositories.RegistrosVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RegistrosVentaService {
    @Autowired
    private RegistrosVentaRepository registrosVentaRepository;

    private RegistrosVentasMapper  registrosVentasMapper;

    public List<RegistrosVentasDTO> findAll() {
        return registrosVentasMapper.toRegistroVentasDTOList(registrosVentaRepository.findAll());
    }

    public List<RegistrosVentasDTO> findAllByTipoPeriodo(TipoPeriodo  tipoPeriodo) {
        return registrosVentasMapper.toRegistroVentasDTOList(registrosVentaRepository.findAllByTipoPeriodo(tipoPeriodo));
    }

    public RegistrosVentasDTO findById(Long id) throws RecordNotFoundException {
        RegistrosVenta registrosVenta = registrosVentaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No hay Registro de Venta para el id: ", id));
        return registrosVentasMapper.toRegistrosVentasDTO(registrosVenta);
    }

    public RegistrosVentasDTO findByFecha(Date fecha) {
        RegistrosVenta registrosVenta = registrosVentaRepository.findByFecha(fecha);
        return registrosVenta != null ? registrosVentasMapper.toRegistrosVentasDTO(registrosVenta) : null;
    }

    public RegistrosVentasDTO createRegistroVenta(RegistrosVentasDTO registrosVentasDTO) {
        RegistrosVenta registrosVenta = registrosVentasMapper.toRegistrosVenta(registrosVentasDTO);
        return registrosVentasMapper.toRegistrosVentasDTO(registrosVentaRepository.save(registrosVenta));
    }

    public  RegistrosVentasDTO updateRegistroVenta(Long id, RegistrosVentasDTO nuevosRegistrosVenta) throws RecordNotFoundException{
        RegistrosVenta registrosVenta = registrosVentaRepository.findById(id).orElseThrow(() ->  new RecordNotFoundException("No hay Registro de Venta para el id: ", id));

        registrosVenta.setTipoPeriodo(nuevosRegistrosVenta.getTipoPeriodo().name());
        registrosVenta.setFechaInicio(nuevosRegistrosVenta.getFechaInicio());
        registrosVenta.setFechaFin(nuevosRegistrosVenta.getFechaFin());
        registrosVenta.setMontoTotal(nuevosRegistrosVenta.getMontoTotal());
        return registrosVentasMapper.toRegistrosVentasDTO(registrosVentaRepository.save(registrosVenta));
    }

    public void deleteRegistroVenta(Long id) throws RecordNotFoundException{
        RegistrosVenta registrosVenta = registrosVentaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No hay Registro de Venta para el id: ", id));
        registrosVentaRepository.delete(registrosVenta);
    }
}
