package com.antonio.ordercontrol.dtos;

import com.antonio.ordercontrol.models.RolUsuario;

public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String correo;
    private RolUsuario rol;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String nombre, String correo, RolUsuario rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }
}
