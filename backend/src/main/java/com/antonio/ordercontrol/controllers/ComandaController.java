package com.antonio.ordercontrol.controllers;

import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.models.Comanda;
import com.antonio.ordercontrol.models.EstadoComanda;
import com.antonio.ordercontrol.services.ComandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comandas")
@CrossOrigin(origins = "*")
public class ComandaController {
    @Autowired
    private ComandaService comandaService;

    @GetMapping
    public Comanda createComanda(@RequestBody Comanda comanda){
        return comandaService.createComanda(comanda);
    }

    @GetMapping("/{id}")
    public Comanda getComandaById(@PathVariable Long id) throws RecordNotFoundException {
        return comandaService.getComandaById(id);
    }

    @PutMapping("/{id}")
    public Comanda updateComanda(@PathVariable Long id, @RequestBody Comanda comanda) throws RecordNotFoundException {
        return comandaService.updateComanda(id, comanda);
    }

    @DeleteMapping("/{id}")
    public void deleteComanda(@PathVariable Long id) throws RecordNotFoundException {
        comandaService.deleteComanda(id);
    }

    @GetMapping("/estado/{estado}")
    public List<Comanda> getComandaByEstado(@PathVariable EstadoComanda estado){
        return comandaService.getComandasByEstado(estado);
    }

    @GetMapping("/mesa/{idMesa}")
    public List<Comanda> getComandaByMesa(@PathVariable Long idMesa){
        return comandaService.getComandasByMesa(idMesa);
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Comanda> getComandaByUsuario(@PathVariable Long idUsuario){
        return comandaService.getComandasByUsuario(idUsuario);
    }
}
