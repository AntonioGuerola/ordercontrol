package com.antonio.ordercontrol.controllers;

import com.antonio.ordercontrol.dtos.UsuarioDTO;
import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.mappers.UsuarioMapper;
import com.antonio.ordercontrol.models.RolUsuario;
import com.antonio.ordercontrol.models.Usuario;
import com.antonio.ordercontrol.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public UsuarioDTO createUsuario(@RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = UsuarioMapper.toUsuario(usuarioDTO);
        return usuarioService.createUsuario(usuario);
    }

    @PutMapping("/{id}")
    public UsuarioDTO updateUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) throws RecordNotFoundException {
        Usuario usuario = UsuarioMapper.toUsuario(usuarioDTO);
        return usuarioService.updateUsuario(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Long id) throws RecordNotFoundException {
        usuarioService.deleteUsuario(id);
    }

    @GetMapping("/{id}")
    public UsuarioDTO getUsuarioById(@PathVariable Long id) throws RecordNotFoundException {
        return usuarioService.getUsuarioById(id);
    }

    @GetMapping
    public List<UsuarioDTO> getAllUsuarios(){
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<?> getUsuariosByRol(@PathVariable String rol){
        try{
            RolUsuario rolUsuario = RolUsuario.valueOf(rol.toUpperCase());
            List<UsuarioDTO> usuarios = usuarioService.getUsuariosByRol(rolUsuario);
            return ResponseEntity.ok(usuarios);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body("Rol inválido. Valores permitidos: ADMIN, CAMARERO o COCINERO.");
        }
    }
}
