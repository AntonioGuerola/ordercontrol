package com.antonio.ordercontrol.controllers;

import com.antonio.ordercontrol.dtos.MesaDTO;
import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.models.Mesa;
import com.antonio.ordercontrol.services.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
@CrossOrigin(origins = "*")
public class MesaController {
    @Autowired
    private MesaService mesaService;

    @GetMapping
    public List<MesaDTO> getAllMesas(){
        return mesaService.getAllMesas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MesaDTO> getMesaById(@PathVariable Long id) throws RecordNotFoundException {
        return ResponseEntity.ok(mesaService.getMesaById(id));
    }

    @PostMapping
    public ResponseEntity<MesaDTO> createMesa(@RequestBody MesaDTO mesaDTO){
        return ResponseEntity.ok(mesaService.createMesa(mesaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MesaDTO> updateMesa(@PathVariable Long id, @RequestBody MesaDTO mesaDTO) throws RecordNotFoundException{
        return ResponseEntity.ok(mesaService.updateMesa(id, mesaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMesa(@PathVariable Long id) throws RecordNotFoundException{
        mesaService.deleteMesa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{estado}")
    public List<MesaDTO> getMesaByEstado(@PathVariable String estado){
        return mesaService.getMesasPorEstado(estado);
    }

    @GetMapping("/tipo/{tipo}")
    public List<MesaDTO> getMesaByTipo(@PathVariable String tipo){
        return mesaService.getMesasPorTipo(tipo);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<MesaDTO> cambiarEstado(@PathVariable Long id, @RequestBody String nuevoEstado) throws RecordNotFoundException{
        return ResponseEntity.ok(mesaService.cambiarEstado(id,nuevoEstado));
    }


}
