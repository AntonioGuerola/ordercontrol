package com.antonio.ordercontrol.dtos;

import java.time.Instant;

public class MesaDTO {
    private Long id;
    private String tipo;
    private Integer numMesa;
    private String estado;
    private Instant fechaHora;

    public MesaDTO() {
    }

    public MesaDTO(Long id, String tipo, Integer numMesa, String estado, Instant fechaHora) {
        this.id = id;
        this.tipo = tipo;
        this.numMesa = numMesa;
        this.estado = estado;
        this.fechaHora = fechaHora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getNumMesa() {
        return numMesa;
    }

    public void setNumMesa(Integer numMesa) {
        this.numMesa = numMesa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Instant getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Instant fechaHora) {
        this.fechaHora = fechaHora;
    }
}
