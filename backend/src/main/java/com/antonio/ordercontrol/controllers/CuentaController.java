package com.antonio.ordercontrol.controllers;

import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.models.Cuenta;
import com.antonio.ordercontrol.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
@CrossOrigin(origins = "*")
public class CuentaController {
    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public List<Cuenta> getAllCuentas(){
        return cuentaService.getAllCuentas();
    }

    @GetMapping("/{id}")
    public Cuenta getCuentaById(@PathVariable Long id) throws RecordNotFoundException {
        return cuentaService.getCuentaById(id);
    }

    @GetMapping("/mesa/{idMesa}")
    public List<Cuenta>  getCuentaByMesa(@PathVariable Long idMesa) {
        return cuentaService.getCuentasByIdMesa(idMesa);
    }

    @PostMapping
    public Cuenta createCuenta(@RequestBody Cuenta cuenta){
        return cuentaService.createCuenta(cuenta);
    }

    @PostMapping("/generar/mesa/{idMesa}")
    public ResponseEntity<Cuenta> generarCuenta(@PathVariable Long idMesa){
        Cuenta cuenta = cuentaService.generarCuentaParaMesa(idMesa);
        return new ResponseEntity<>(cuenta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Cuenta updateCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta) throws RecordNotFoundException {
        return cuentaService.updateCuenta(id, cuenta);
    }

    @DeleteMapping("/{id}")
    public void deleteCuenta(@PathVariable Long id) throws RecordNotFoundException {
        cuentaService.deleteCuenta(id);
    }
}
