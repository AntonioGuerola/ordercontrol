package com.antonio.ordercontrol.services;

import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.models.RolUsuario;
import com.antonio.ordercontrol.models.Usuario;
import com.antonio.ordercontrol.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario createUsuario(Usuario usuario){
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())){
            throw new IllegalStateException("Ya existe un usuario con ese correo.");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Long id, Usuario usuario) throws RecordNotFoundException {
        Usuario usuarioExistente = usuarioRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Usuario no encontrado para el id:", id));

        if (!usuarioExistente.getCorreo().equals(usuario.getCorreo()) && usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new IllegalStateException("Ya existe un usuario con ese correo.");
        }

        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setCorreo(usuario.getCorreo());
        usuarioExistente.setContrasena(usuario.getContrasena());
        usuarioExistente.setRol(usuario.getRol().name());

        return usuarioRepository.save(usuarioExistente);
    }

    public void deleteUsuario(Long id) throws RecordNotFoundException {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Usuario no encontrado para el id: ", id));
        usuarioRepository.delete(usuario);
    }

    public Usuario getUsuarioById(Long id) throws RecordNotFoundException {
        return usuarioRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Usuario no encontrado para el id: ", id));
    }

    public List<Usuario> getAllUsuarios(){
        return usuarioRepository.findAll();
    }

    public List<Usuario> getUsuariosByRol(RolUsuario rol){
        return usuarioRepository.findByRol(rol);
    }
}
