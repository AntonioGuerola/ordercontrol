package com.antonio.ordercontrol.mappers;

import com.antonio.ordercontrol.dtos.ComandaProductoDTO;
import com.antonio.ordercontrol.models.Comanda;
import com.antonio.ordercontrol.models.Comandaproducto;
import com.antonio.ordercontrol.models.Producto;
import com.antonio.ordercontrol.repositories.ComandaRepository;
import com.antonio.ordercontrol.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ComandaProductoMapper {
    private ComandaRepository comandaRepository;
    private ProductoRepository productoRepository;

    public ComandaProductoDTO toComandaProductoDTO(Comandaproducto entity) {
        ComandaProductoDTO dto = new ComandaProductoDTO();
        dto.setId(entity.getId().longValue());
        dto.setIdComanda(entity.getComanda().getId().longValue());
        dto.setIdProducto(entity.getProducto().getId().longValue());
        dto.setCantidad(entity.getCantidad());
        dto.setPrecioUnitario(entity.getPrecioUnitario());
        return dto;
    }

    public Comandaproducto toEntity(ComandaProductoDTO dto) {
        Comandaproducto entity = new Comandaproducto();
        entity.setId(dto.getId() != null ? dto.getId().intValue() : null);
        entity.setCantidad(dto.getCantidad());
        entity.setPrecioUnitario(dto.getPrecioUnitario());

        Comanda comanda = comandaRepository.findById(dto.getIdComanda())
                .orElseThrow(() -> new RuntimeException("Comanda no encontrada"));
        Producto producto = productoRepository.findById(dto.getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        entity.setComanda(comanda);
        entity.setProducto(producto);

        return entity;
    }
}
