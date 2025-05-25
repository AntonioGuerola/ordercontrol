package com.antonio.ordercontrol.controllers;

import com.antonio.ordercontrol.dtos.CategoriaDTO;
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
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias(){
        return ResponseEntity.ok(categoriaService.getAllCategoriasDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> getCategoriaById(@PathVariable Long id) throws RecordNotFoundException{
        return ResponseEntity.ok(categoriaService.getCategoriaDTOById(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> createCategoria(@RequestBody CategoriaDTO categoriaDTO){
        return ResponseEntity.ok(categoriaService.createCategoriaDTO(categoriaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> updateCategoria(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) throws RecordNotFoundException{
        return ResponseEntity.ok(categoriaService.updateCategoriaDTO(id, categoriaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoria(@PathVariable Long id) throws RecordNotFoundException{
        categoriaService.deleteCategoriaDTO(id);
        return ResponseEntity.ok("Categoría eliminada con éxito");
    }
}
