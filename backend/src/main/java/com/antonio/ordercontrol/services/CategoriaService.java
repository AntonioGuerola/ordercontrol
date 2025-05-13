package com.antonio.ordercontrol.services;

import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.models.Categoria;
import com.antonio.ordercontrol.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> getAllCategorias(){
        return categoriaRepository.findAll();
    }

    public Categoria getCategoriaById(Long id) throws RecordNotFoundException {
        return categoriaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No existe categoría con el id: ", id));
    }

    public Categoria createCategoria(Categoria categoria) {
        validarCategoria(categoria);
        return categoriaRepository.save(categoria);
    }

    public Categoria updateCategoria(Long id, Categoria categoria) {
        Categoria categoriaExistente = getCategoriaById(id);
        categoriaExistente.setNombre(categoria.getNombre());
        validarCategoria(categoriaExistente);
        return categoriaRepository.save(categoriaExistente);
    }

    public void deleteCategoria(Long id) throws RecordNotFoundException {
        Categoria categoriaExistente = getCategoriaById(id);
        if (!categoriaExistente.getProductos().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar una categoria que tiene productos asociados.");
        }
        categoriaRepository.delete(categoriaExistente);
    }

    private void validarCategoria(Categoria categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().isBlank()) {
            throw new IllegalStateException("El nombre del categoria no puede estar vacio.");
        }

        if (categoriaRepository.existsByNombreIgnoreCase(categoria.getNombre())) {
            throw new IllegalStateException("Ya existe una categoría con ese nombre.");
        }
    }
}
