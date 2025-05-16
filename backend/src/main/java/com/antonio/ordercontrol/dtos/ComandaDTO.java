package com.antonio.ordercontrol.dtos;

import java.util.List;

public class ComandaDTO {
    private Long id;
    private String estado;
    private Long idMesa;
    private List<ComandaProductoDTO> productos;

    public ComandaDTO(Long id, String estado, Long idMesa, List<ComandaProductoDTO> productos) {
        this.id = id;
        this.estado = estado;
        this.idMesa = idMesa;
        this.productos = productos;
    }

    public ComandaDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Long idMesa) {
        this.idMesa = idMesa;
    }

    public List<ComandaProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ComandaProductoDTO> productos) {
        this.productos = productos;
    }
}
