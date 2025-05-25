package com.antonio.ordercontrol.controllers;

import com.antonio.ordercontrol.dtos.ComandaCreatedDTO;
import com.antonio.ordercontrol.dtos.ComandaDTO;
import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.models.Comanda;
import com.antonio.ordercontrol.models.EstadoComanda;
import com.antonio.ordercontrol.models.Mesa;
import com.antonio.ordercontrol.services.ComandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comandas")
@CrossOrigin(origins = "*")
public class ComandaController {

    @Autowired
    private ComandaService comandaService;

    @GetMapping
    public ResponseEntity<List<ComandaDTO>> getAllComandas(){
        List<ComandaDTO> comandas = comandaService.getAllComandas();
        return ResponseEntity.ok(comandas);
    }

    @PostMapping
    public ResponseEntity<ComandaDTO> createComanda(@RequestBody ComandaCreatedDTO comanda){
        ComandaDTO comandaDTO = comandaService.createComanda(comanda);
        return ResponseEntity.ok(comandaDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComandaDTO> getComandaById(@PathVariable Long id) throws RecordNotFoundException {
        ComandaDTO comandaDTO = comandaService.getComandaById(id);
        return ResponseEntity.ok(comandaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComandaDTO> updateComanda(@PathVariable Long id, @RequestBody Comanda comanda) throws RecordNotFoundException {
        ComandaDTO updated = comandaService.updateComanda(id, comanda);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComanda(@PathVariable Long id) throws RecordNotFoundException {
        comandaService.deleteComanda(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ComandaDTO>> getComandaByEstado(@PathVariable EstadoComanda estado){
        List<ComandaDTO> comandas = comandaService.getComandasByEstado(estado);
        return ResponseEntity.ok(comandas);
    }

    @GetMapping("/mesa/{idMesa}")
    public ResponseEntity<List<ComandaDTO>> getComandaByMesa(@PathVariable Long idMesa){
        List<ComandaDTO> comandas = comandaService.getComandasByMesa(idMesa);
        return ResponseEntity.ok(comandas);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ComandaDTO>> getComandaByUsuario(@PathVariable Long idUsuario){
        List<ComandaDTO> comandas = comandaService.getComandasByUsuario(idUsuario);
        return ResponseEntity.ok(comandas);
    }

    @GetMapping("/mesa/{idMesa}/activa")
    public ResponseEntity<ComandaDTO> getComandaActiva(@PathVariable Long idMesa){
        return comandaService.getComandaActivaByMesa(idMesa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/cerrar")
    public ResponseEntity<Void> cerrarComanda(@PathVariable Long id){
        comandaService.cerrarComanda(id);
        return ResponseEntity.ok().build();
    }
}
