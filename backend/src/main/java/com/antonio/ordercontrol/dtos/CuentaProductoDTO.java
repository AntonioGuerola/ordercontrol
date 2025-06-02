package com.antonio.ordercontrol.dtos;

import java.math.BigDecimal;

public class CuentaProductoDTO {
    private String nombre;
    private BigDecimal precio;
    private int cantidad;
    private String tipo;

    public CuentaProductoDTO() {}

    public CuentaProductoDTO(String nombre, BigDecimal precio, int cantidad, String tipo) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.tipo = tipo;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
