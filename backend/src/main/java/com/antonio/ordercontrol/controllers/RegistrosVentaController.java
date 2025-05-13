package com.antonio.ordercontrol.controllers;

import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.models.Cuenta;
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
    public List<RegistrosVenta> getAllRegistrosVentas(){
        return registrosVentaService.findAll();
    }

    @GetMapping("/registro-ventas/{tipoPeriodo}")
    public List<RegistrosVenta> getAllRegistrosVentasByTipoPeriodo(@PathVariable TipoPeriodo tipoPeriodo){
        return registrosVentaService.findAllByTipoPeriodo(tipoPeriodo);
    }

    @GetMapping("/{id}")
    public RegistrosVenta findById(@PathVariable Long id){
        return registrosVentaService.findById(id);
    }

    @GetMapping("/registro-ventas/{fecha}")
    public RegistrosVenta findByFecha(@PathVariable Date fecha){
        return registrosVentaService.findByFecha(fecha);
    }

    @PostMapping
    public RegistrosVenta createRegistrosVenta(@RequestBody RegistrosVenta registrosVenta){
        return registrosVentaService.createRegistroVenta(registrosVenta);
    }

    @PutMapping("/{id}")
    public RegistrosVenta updateRegistrosVentas(@PathVariable Long id, @RequestBody RegistrosVenta registrosVenta) throws RecordNotFoundException {
        return registrosVentaService.updateRegistroVenta(id, registrosVenta);
    }

    @DeleteMapping("/{id}")
    public void deleteRegistrosVentas(@PathVariable Long id) throws RecordNotFoundException {
        registrosVentaService.deleteRegistroVenta(id);
    }
}