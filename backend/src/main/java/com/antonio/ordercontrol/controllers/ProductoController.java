package com.antonio.ordercontrol.controllers;

import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.models.Producto;
import com.antonio.ordercontrol.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.antonio.ordercontrol.dtos.ProductoDTO;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos(){
        return ResponseEntity.ok(productoService.getAllProductos());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Producto>> getProductosDisponibles(){
        return ResponseEntity.ok(productoService.getProductosDisponibles());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Producto>> getProductosPorTipo(@PathVariable String tipo){
        return ResponseEntity.ok(productoService.getProductosPorTipo(tipo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id){
        return ResponseEntity.ok(productoService.getProductoById(id));
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> createProducto(@RequestBody ProductoDTO productoDTO){
        ProductoDTO createProductoDTO = productoService.createProducto(productoDTO);
        return ResponseEntity.ok(createProductoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> updateProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) throws RecordNotFoundException {
        return ResponseEntity.ok(productoService.updateProducto(id, productoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable Long id) throws RecordNotFoundException {
        productoService.deleteProducto(id);
        return ResponseEntity.ok("Producto eliminado con éxito");
    }

    @PatchMapping("/{id}/disponible")
    public ResponseEntity<Producto> cambiarDisponibilidad(@PathVariable Long id, @RequestBody Map<String, Boolean> cuerpo) throws RecordNotFoundException{
        Boolean estado = cuerpo.get("disponible");
        return ResponseEntity.ok(productoService.cambiarDisponibilidad(id, estado));
    }

    @GetMapping("/categoria/{id}")
    public List<Producto> getProductosPorCategoria(@PathVariable Long id){
        return productoService.getProductoPorIdCategoria(id);
    }
}