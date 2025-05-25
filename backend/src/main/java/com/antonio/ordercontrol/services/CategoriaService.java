package com.antonio.ordercontrol.services;

import com.antonio.ordercontrol.dtos.CategoriaDTO;
import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.mappers.CategoriaMapper;
import com.antonio.ordercontrol.models.Categoria;
import com.antonio.ordercontrol.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<CategoriaDTO> getAllCategoriasDTO(){
        return categoriaRepository.findAll().stream().map(CategoriaMapper::toCategoriaDTO).toList();
    }

    public CategoriaDTO getCategoriaDTOById(Long id) throws RecordNotFoundException {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No existe categoria con el id: ", id));
        return CategoriaMapper.toCategoriaDTO(categoria);
    }

    public Categoria getCategoriaById(Long id) throws RecordNotFoundException {
        return categoriaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No existe categoría con el id: ", id));
    }

    public CategoriaDTO createCategoriaDTO(CategoriaDTO categoriaDTO) {
        Categoria categoria = CategoriaMapper.toCategoria(categoriaDTO);
        validarCategoria(categoria);
        categoria = categoriaRepository.save(categoria);
        return CategoriaMapper.toCategoriaDTO(categoria);
    }

    public CategoriaDTO updateCategoriaDTO(Long id, CategoriaDTO categoriaDTO) throws RecordNotFoundException {
        Categoria categoriaExistente = categoriaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No existe categoría con id: ", id));
        categoriaExistente.setNombre(categoriaDTO.getNombre());
        validarCategoria(categoriaExistente);
        categoriaExistente = categoriaRepository.save(categoriaExistente);
        return CategoriaMapper.toCategoriaDTO(categoriaExistente);
    }

    public void deleteCategoriaDTO(Long id) throws RecordNotFoundException {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No exite categoría para el id: ", id));

        if (!categoria.getProductos().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar una categoría que tiene productos asociados.");
        }

        categoriaRepository.delete(categoria);
    }

    private void validarCategoria(Categoria categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().isBlank()) {
            throw new IllegalStateException("El nombre del categoría no puede estar vacío.");
        }

        if (categoriaRepository.existsByNombreIgnoreCase(categoria.getNombre())) {
            throw new IllegalStateException("Ya existe una categoría con ese nombre.");
        }
    }
}
