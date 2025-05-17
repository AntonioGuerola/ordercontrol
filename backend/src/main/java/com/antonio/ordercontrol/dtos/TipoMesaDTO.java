package com.antonio.ordercontrol.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TipoMesaDTO {
    private Long id;

    @NotBlank(message = "El nombre del tipo de la mesa no puede estar vacío.")
    @Size(max = 20)
    private String nombre;

    public TipoMesaDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public TipoMesaDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
