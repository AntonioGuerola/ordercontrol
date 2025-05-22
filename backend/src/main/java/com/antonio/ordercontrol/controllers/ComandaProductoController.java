package com.antonio.ordercontrol.controllers;

import com.antonio.ordercontrol.dtos.ComandaProductoDTO;
import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.services.ComandaProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comandaproductos")
@CrossOrigin(origins = "*")
public class ComandaProductoController {

    @Autowired
    private ComandaProductoService comandaProductoService;

    @GetMapping
    public ResponseEntity<List<ComandaProductoDTO>> getAllComandaProducto() {
        List<ComandaProductoDTO> productos = comandaProductoService.getAllComandaProducto();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComandaProductoDTO> getComandaProductoById(@PathVariable Long id) throws RecordNotFoundException {
        ComandaProductoDTO dto = comandaProductoService.getComandaProductoById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/comanda/{idComanda}")
    public ResponseEntity<List<ComandaProductoDTO>> getComandaProductoByComanda(@PathVariable Long idComanda) {
        List<ComandaProductoDTO> productos = comandaProductoService.getComandaProductoByComandaId(idComanda);
        return ResponseEntity.ok(productos);
    }

    @PostMapping
    public ResponseEntity<ComandaProductoDTO> createComandaProducto(@RequestBody ComandaProductoDTO dto) {
        ComandaProductoDTO creado = comandaProductoService.createComandaProducto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComandaProductoDTO> updateComandaProducto(@PathVariable Long id, @RequestBody ComandaProductoDTO dto) throws RecordNotFoundException {
        ComandaProductoDTO actualizado = comandaProductoService.updateComandaProducto(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComandaProducto(@PathVariable Long id) throws RecordNotFoundException {
        comandaProductoService.deleteComandaProducto(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/comanda/{idComanda}/producto")
    public ResponseEntity<ComandaProductoDTO> agregarProductoAComanda(@PathVariable Long idComanda, @RequestBody ComandaProductoDTO dto) {
        ComandaProductoDTO nuevo = comandaProductoService.agregarProductoAComanda(idComanda, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
}