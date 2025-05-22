package com.antonio.ordercontrol.controllers;

import com.antonio.ordercontrol.dtos.RegistrosVentasDTO;
import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.models.RegistrosVenta;
import com.antonio.ordercontrol.models.TipoPeriodo;
import com.antonio.ordercontrol.services.RegistrosVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/registro-ventas")
@CrossOrigin(origins = "*")
public class RegistrosVentaController {

    @Autowired
    private RegistrosVentaService registrosVentaService;

    @GetMapping
    public List<RegistrosVentasDTO> getAllRegistrosVentas(){
        return registrosVentaService.findAll();
    }

    @GetMapping("/tipo/{tipoPeriodo}")
    public List<RegistrosVentasDTO> getAllRegistrosVentasByTipoPeriodo(@PathVariable TipoPeriodo tipoPeriodo){
        return registrosVentaService.findAllByTipoPeriodo(tipoPeriodo);
    }

    @GetMapping("/{id}")
    public RegistrosVentasDTO findById(@PathVariable Long id){
        return registrosVentaService.findById(id);
    }

    @GetMapping("/fecha/{fecha}")
    public RegistrosVentasDTO findByFecha(@PathVariable Date fecha){
        return registrosVentaService.findByFecha(fecha);
    }

    @PostMapping
    public RegistrosVentasDTO createRegistrosVenta(@RequestBody RegistrosVentasDTO registrosVenta){
        return registrosVentaService.createRegistroVenta(registrosVenta);
    }

    @PutMapping("/{id}")
    public RegistrosVentasDTO updateRegistrosVentas(@PathVariable Long id, @RequestBody RegistrosVentasDTO registrosVenta) throws RecordNotFoundException {
        return registrosVentaService.updateRegistroVenta(id, registrosVenta);
    }

    @DeleteMapping("/{id}")
    public void deleteRegistrosVentas(@PathVariable Long id) throws RecordNotFoundException {
        registrosVentaService.deleteRegistroVenta(id);
    }
}