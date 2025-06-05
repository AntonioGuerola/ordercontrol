package com.antonio.ordercontrol.dtos;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class CuentaDTO {
    private Long id;
    private Long idMesa;
    private BigDecimal sumaTotal;
    private Instant horaCobro;
    private String metodoPago;
    private List<CuentaProductoDTO> productos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Long idMesa) {
        this.idMesa = idMesa;
    }

    public BigDecimal getSumaTotal() {
        return sumaTotal;
    }

    public void setSumaTotal(BigDecimal sumaTotal) {
        this.sumaTotal = sumaTotal;
    }

    public Instant getHoraCobro() {
        return horaCobro;
    }

    public void setHoraCobro(Instant horaCobre) {
        this.horaCobro = horaCobro;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public List<CuentaProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<CuentaProductoDTO> productos) {
        this.productos = productos;
    }
}
