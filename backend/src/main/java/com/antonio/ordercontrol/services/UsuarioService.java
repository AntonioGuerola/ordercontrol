package com.antonio.ordercontrol.services;

import com.antonio.ordercontrol.dtos.UsuarioDTO;
import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.mappers.UsuarioMapper;
import com.antonio.ordercontrol.models.RolUsuario;
import com.antonio.ordercontrol.models.Usuario;
import com.antonio.ordercontrol.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioDTO createUsuario(Usuario usuario){
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())){
            throw new IllegalStateException("Ya existe un usuario con ese correo.");
        }
        return UsuarioMapper.toUsuarioDTO(usuarioRepository.save(usuario));
    }

    public UsuarioDTO updateUsuario(Long id, Usuario usuario) throws RecordNotFoundException {
        Usuario usuarioExistente = usuarioRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Usuario no encontrado para el id:", id));

        if (!usuarioExistente.getCorreo().equals(usuario.getCorreo()) && usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new IllegalStateException("Ya existe un usuario con ese correo.");
        }

        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setCorreo(usuario.getCorreo());
        usuarioExistente.setContrasena(usuario.getContrasena());
        usuarioExistente.setRol(usuario.getRol().name());

        return UsuarioMapper.toUsuarioDTO(usuarioRepository.save(usuarioExistente));
    }

    public void deleteUsuario(Long id) throws RecordNotFoundException {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No hay Usuario para el id: ", id));
        usuarioRepository.delete(usuario);
    }

    public UsuarioDTO getUsuarioById(Long id) throws RecordNotFoundException {
        return UsuarioMapper.toUsuarioDTO(usuarioRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No ha Usuario para el id: ", id)));
    }

    public List<UsuarioDTO> getAllUsuarios(){
        return usuarioRepository.findAll().stream().map(UsuarioMapper::toUsuarioDTO).collect(Collectors.toList());
    }

    public List<UsuarioDTO> getUsuariosByRol(RolUsuario rol){
        return usuarioRepository.findByRol(rol).stream().map(UsuarioMapper::toUsuarioDTO).collect(Collectors.toList());
    }
}
