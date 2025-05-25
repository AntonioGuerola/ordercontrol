package com.antonio.ordercontrol.mappers;

import com.antonio.ordercontrol.dtos.UsuarioDTO;
import com.antonio.ordercontrol.models.Usuario;

public class UsuarioMapper {
    public static UsuarioDTO toUsuarioDTO(Usuario usuario){
        return new UsuarioDTO(
                usuario.getId().longValue(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getRol()
        );
    }

    public static Usuario toUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario();
        usuario.setId(usuarioDTO.getId() != null ? usuarioDTO.getId().intValue()  : null);
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setRol(usuarioDTO.getRol().name());
        return usuario;
    }
}
