package com.antonio.ordercontrol.mappers;

import com.antonio.ordercontrol.dtos.ProductoDTO;
import com.antonio.ordercontrol.models.Categoria;
import com.antonio.ordercontrol.models.Producto;

public class ProductoMapper {
    public static ProductoDTO toProductoDTO(Producto producto) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(producto.getId().longValue());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setTipo(producto.getTipo());
        productoDTO.setDisponible(producto.getDisponible());

        if (producto.getCategoria() != null) {
            productoDTO.setIdCategoria(producto.getCategoria().getId().longValue());
            productoDTO.setNombreCategoria(producto.getCategoria().getNombre());
        }

        return productoDTO;
    }

    public static Producto toProducto(ProductoDTO productoDTO, Categoria  categoria) {
        Producto producto = new Producto();

        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setTipo(productoDTO.getTipo());
        producto.setDisponible(productoDTO.getDisponible() != null ? productoDTO.getDisponible() : true);
        producto.setCategoria(categoria);

        return producto;
    }

    public static void actualizarProducto(ProductoDTO productoDTO, Producto producto, Categoria categoria) {
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setTipo(productoDTO.getTipo());
        producto.setDisponible(productoDTO.getDisponible() !=  null ? productoDTO.getDisponible() : true);
        producto.setCategoria(categoria);
    }
}
