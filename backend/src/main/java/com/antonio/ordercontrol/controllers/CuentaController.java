package com.antonio.ordercontrol.controllers;

import com.antonio.ordercontrol.dtos.CuentaDTO;
import com.antonio.ordercontrol.dtos.CuentaProductoDTO;
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
    public List<CuentaDTO> getAllCuentas(){
        return cuentaService.getAllCuentas();
    }

    @GetMapping("/{id}")
    public CuentaDTO getCuentaById(@PathVariable Long id) throws RecordNotFoundException {
        return cuentaService.getCuentaById(id);
    }

    @GetMapping("/mesa/{idMesa}")
    public List<CuentaDTO>  getCuentaByMesa(@PathVariable Long idMesa) {
        return cuentaService.getCuentasByIdMesa(idMesa);
    }

    @PostMapping
    public CuentaDTO createCuenta(@RequestBody CuentaDTO cuentaDTO){
        return cuentaService.createCuenta(cuentaDTO);
    }

    @PostMapping("/generar/mesa/{idMesa}")
    public ResponseEntity<CuentaDTO> generarCuenta(@PathVariable Long idMesa){
        CuentaDTO cuentaDTO = cuentaService.generarCuentaParaMesa(idMesa);
        return new ResponseEntity<>(cuentaDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public CuentaDTO updateCuenta(@PathVariable Long id, @RequestBody CuentaDTO cuentaDTO) throws RecordNotFoundException {
        return cuentaService.updateCuenta(id, cuentaDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCuenta(@PathVariable Long id) throws RecordNotFoundException {
        cuentaService.deleteCuenta(id);
    }

    @GetMapping("/mesa/{idMesa}/productos")
    public ResponseEntity<List<CuentaProductoDTO>> getProductosPorMesa(@PathVariable Long idMesa) {
        List<CuentaProductoDTO> productos = cuentaService.getProductosConsumidosPorMesa(idMesa);
        return ResponseEntity.ok(productos);
    }

}
