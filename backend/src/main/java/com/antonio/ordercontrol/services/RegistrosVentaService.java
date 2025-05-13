package com.antonio.ordercontrol.services;

import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
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

    public List<RegistrosVenta> findAll() {
        return registrosVentaRepository.findAll();
    }

    public List<RegistrosVenta> findAllByTipoPeriodo(TipoPeriodo  tipoPeriodo) {
        return registrosVentaRepository.findAllByTipoPeriodo(tipoPeriodo);
    }

    public RegistrosVenta findById(Long id) throws RecordNotFoundException {
        return registrosVentaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No existe Registro con id: ", id));
    }

    public RegistrosVenta findByFecha(Date fecha) {
        return registrosVentaRepository.findByFecha(fecha);
    }

    public RegistrosVenta createRegistroVenta(RegistrosVenta registrosVenta) {
        return registrosVentaRepository.save(registrosVenta);
    }

    public  RegistrosVenta updateRegistroVenta(Long id, RegistrosVenta nuevosRegistrosVenta) throws RecordNotFoundException{
        RegistrosVenta registrosVenta = findById(id);
        registrosVenta.setTipoPeriodo(nuevosRegistrosVenta.getTipoPeriodo().name());
        registrosVenta.setFechaInicio(nuevosRegistrosVenta.getFechaInicio());
        registrosVenta.setFechaFin(nuevosRegistrosVenta.getFechaFin());
        registrosVenta.setMontoTotal(nuevosRegistrosVenta.getMontoTotal());
        return registrosVentaRepository.save(nuevosRegistrosVenta);
    }

    public void deleteRegistroVenta(Long id) throws RecordNotFoundException{
        RegistrosVenta registrosVenta = findById(id);
        registrosVentaRepository.delete(registrosVenta);
    }
}
