package com.antonio.ordercontrol.controllers;

import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.models.Comandaproducto;
import com.antonio.ordercontrol.services.ComandaProductoService;
import com.antonio.ordercontrol.services.ComandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comandaproductos")
@CrossOrigin(origins = "*")
public class ComandaProductoController {
    @Autowired
    private ComandaProductoService comandaProductoService;

    @GetMapping
    public List<Comandaproducto> getAllComandaProducto(){
        return comandaProductoService.getAllComandaProducto();
    }

    @GetMapping("/{id}")
    public Comandaproducto getComandaProductoById(@PathVariable Long id) throws RecordNotFoundException {
        return comandaProductoService.getComandaProductoById(id);
    }

    @GetMapping("/comanda/{idComanda}")
    public List<Comandaproducto> getComandaProductoByComanda(@PathVariable Long idComanda) {
        return comandaProductoService.getComandaProductoByComandaId(idComanda);
    }

    @PostMapping
    public Comandaproducto createComandaProducto(@RequestBody Comandaproducto comandaproducto){
        return comandaProductoService.createComandaProducto(comandaproducto);
    }

    @PutMapping("/{id}")
    public Comandaproducto updateComandaProducto(@PathVariable Long id,  @RequestBody Comandaproducto comandaproducto)  throws RecordNotFoundException {
        return comandaProductoService.updateComandaProducto(id, comandaproducto);
    }

    @DeleteMapping("/{id}")
    public void deleteComandaProducto(@PathVariable Long id) throws RecordNotFoundException {
        comandaProductoService.deleteComandaProducto(id);
    }
}
