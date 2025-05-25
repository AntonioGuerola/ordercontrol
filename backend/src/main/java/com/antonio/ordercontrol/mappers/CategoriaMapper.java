package com.antonio.ordercontrol.mappers;

import com.antonio.ordercontrol.dtos.CategoriaDTO;
import com.antonio.ordercontrol.models.Categoria;

public class CategoriaMapper {
    public static CategoriaDTO toCategoriaDTO(Categoria categoria) {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(categoria.getId().longValue());
        categoriaDTO.setNombre(categoria.getNombre());
        return categoriaDTO;
    }

    public static Categoria toCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setId(categoriaDTO.getId().intValue());
        categoria.setNombre(categoriaDTO.getNombre());
        return categoria;
    }
}
