package com.antonio.ordercontrol.controllers;

import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.models.Categoria;
import com.antonio.ordercontrol.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> getAllCategorias(){
        return ResponseEntity.ok(categoriaService.getAllCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable Long id) throws RecordNotFoundException{
        return ResponseEntity.ok(categoriaService.getCategoriaById(id));
    }

    @PostMapping
    public ResponseEntity<Categoria> createCategoria(@RequestBody Categoria categoria){
        return ResponseEntity.ok(categoriaService.createCategoria(categoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable Long id, @RequestBody Categoria categoria) throws RecordNotFoundException{
        return ResponseEntity.ok(categoriaService.updateCategoria(id, categoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoria(@PathVariable Long id) throws RecordNotFoundException{
        categoriaService.deleteCategoria(id);
        return ResponseEntity.ok("Categoría eliminada con éxito");
    }
}
