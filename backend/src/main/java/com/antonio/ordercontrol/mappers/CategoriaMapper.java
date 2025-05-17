package com.antonio.ordercontrol.mappers;

import com.antonio.ordercontrol.dtos.CategoriaDTO;
import com.antonio.ordercontrol.dtos.ProductoDTO;
import com.antonio.ordercontrol.models.Categoria;
import com.antonio.ordercontrol.models.Producto;

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

    public static void actualizarCategoria(CategoriaDTO categoriaDTO, Categoria categoria) {
        categoria.setId(categoriaDTO.getId().intValue());
        categoria.setNombre(categoriaDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setTipo(productoDTO.getTipo());
        producto.setDisponible(productoDTO.getDisponible() !=  null ? productoDTO.getDisponible() : true);
        producto.setCategoria(categoria);
    }}
