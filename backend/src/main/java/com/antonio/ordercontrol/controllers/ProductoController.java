package com.antonio.ordercontrol.controllers;

import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.mappers.ProductoMapper;
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
    public ResponseEntity<List<ProductoDTO>> getAllProductos(){
        return ResponseEntity.ok(productoService.getAllProductos().stream().map(ProductoMapper::toProductoDTO).toList());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<ProductoDTO>> getProductosDisponibles(){
        return ResponseEntity.ok(productoService.getProductosDisponibles().stream().map(ProductoMapper::toProductoDTO).toList());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ProductoDTO>> getProductosPorTipo(@PathVariable String tipo){
        return ResponseEntity.ok(productoService.getProductosPorTipo(tipo).stream().map(ProductoMapper::toProductoDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id){
        Producto producto = productoService.getProductoById(id);
        return ResponseEntity.ok(ProductoMapper.toProductoDTO(producto));
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
    public ResponseEntity<ProductoDTO> cambiarDisponibilidad(@PathVariable Long id, @RequestBody Map<String, Boolean> cuerpo) throws RecordNotFoundException{
        Boolean estado = cuerpo.get("disponible");
        Producto producto = productoService.cambiarDisponibilidad(id, estado);
        return ResponseEntity.ok(ProductoMapper.toProductoDTO(producto));
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<ProductoDTO>> getProductosPorCategoria(@PathVariable Long id){
        return ResponseEntity.ok(productoService.getProductoPorIdCategoria(id).stream().map(ProductoMapper::toProductoDTO).toList());
    }
}