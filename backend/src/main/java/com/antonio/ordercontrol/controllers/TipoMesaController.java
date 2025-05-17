package com.antonio.ordercontrol.controllers;

import com.antonio.ordercontrol.dtos.TipoMesaDTO;
import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.models.TipoMesa;
import com.antonio.ordercontrol.services.TipoMesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-mesa")
public class TipoMesaController {
    @Autowired
    private TipoMesaService tipoMesaService;

    @GetMapping
    public ResponseEntity<List<TipoMesaDTO>> getAllTipoMesa() {
        return ResponseEntity.ok(tipoMesaService.getAllTipoMesas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoMesaDTO> getTipoMesaById(@PathVariable Long id) throws RecordNotFoundException {
        return ResponseEntity.ok(tipoMesaService.getTipoMesaById(id));
    }

    @PostMapping
    public ResponseEntity<TipoMesaDTO> createTipoMesa(@RequestBody TipoMesaDTO tipoMesaDTO) {
        return ResponseEntity.ok(tipoMesaService.createMesa(tipoMesaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoMesaDTO> updateTipomesa(@PathVariable Long id, @RequestBody TipoMesaDTO tipoMesaDTO) throws RecordNotFoundException {
        return ResponseEntity.ok(tipoMesaService.updateMesa(id, tipoMesaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> deleteTipoMesa(@PathVariable Long id) throws RecordNotFoundException {
        tipoMesaService.deleteTipoMesa(id);
        return ResponseEntity.ok("Tipo de mesa eliminado con éxito.");
    }
}
