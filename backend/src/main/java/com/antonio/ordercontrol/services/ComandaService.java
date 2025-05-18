package com.antonio.ordercontrol.services;

import com.antonio.ordercontrol.dtos.ComandaCreatedDTO;
import com.antonio.ordercontrol.dtos.ComandaDTO;
import com.antonio.ordercontrol.dtos.MesaDTO;
import com.antonio.ordercontrol.dtos.UsuarioDTO;
import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.mappers.ComandaMapper;
import com.antonio.ordercontrol.models.Comanda;
import com.antonio.ordercontrol.models.EstadoComanda;
import com.antonio.ordercontrol.models.Mesa;
import com.antonio.ordercontrol.models.Usuario;
import com.antonio.ordercontrol.repositories.ComandaRepository;
import com.antonio.ordercontrol.repositories.MesaRepository;
import com.antonio.ordercontrol.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComandaService {
    @Autowired
    private ComandaRepository comandaRepository;
    @Autowired
    private MesaRepository mesaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public ComandaDTO createComanda(ComandaCreatedDTO comandaDTO){
        Mesa mesa = mesaRepository.findById(comandaDTO.getIdMesa()).orElseThrow(() -> new EntityNotFoundException("No existe Mesa para el id: " + comandaDTO.getIdMesa()));
        Usuario usuario = null;

        if (comandaDTO.getIdUsuario() != null) {
            usuario = usuarioRepository.findById(comandaDTO.getIdUsuario()).orElseThrow(() -> new EntityNotFoundException("No hay Usuario para el id: " + comandaDTO.getIdUsuario()));
        }

        Comanda comanda = ComandaMapper.fromCreateComandaDTO(comandaDTO, mesa, usuario);
        return ComandaMapper.toComandaDTO(comandaRepository.save(comanda));
    }

    public ComandaDTO updateComanda(Long id, Comanda comanda){
        Comanda comandaExistente = comandaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No hay Comanda para el id: ", id));
        comandaExistente.setEstado(comanda.getEstado().name());
        comandaExistente.setIdMesa(comanda.getIdMesa());
        comandaExistente.setIdUsuario(comanda.getIdUsuario());
        comandaExistente.setIdCuenta(comanda.getIdCuenta());
        return ComandaMapper.toComandaDTO(comandaRepository.save(comandaExistente));
    }

    public void deleteComanda (Long id) throws RecordNotFoundException {
        if(!comandaRepository.existsById(id)){
            throw new RecordNotFoundException("No existe comanda para el id: ", id);
        }
        comandaRepository.deleteById(id);
    }

    public ComandaDTO getComandaById(Long id) throws RecordNotFoundException {
        Comanda comanda = comandaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No existe comanda para el id: ", id));
        return ComandaMapper.toComandaDTO(comanda);
    }

    public List<ComandaDTO> getAllComandas(){
        return comandaRepository.findAll().stream().map(ComandaMapper::toComandaDTO).toList();
    }

    public List<ComandaDTO> getComandasByEstado(EstadoComanda estado){
        return comandaRepository.findByEstadoIgnoreCase(estado).stream()
                .map(ComandaMapper::toComandaDTO)
                .collect(Collectors.toList());
    }

    public List<ComandaDTO> getComandasByMesa(Long idMesa){
        return comandaRepository.findByIdMesa(idMesa).stream()
                .map(ComandaMapper::toComandaDTO)
                .collect(Collectors.toList());
    }

    public List<ComandaDTO> getComandasByUsuario(Long idUsuario){
        return comandaRepository.findByIdUsuario(idUsuario).stream()
                .map(ComandaMapper::toComandaDTO)
                .collect(Collectors.toList());
    }

    public Optional<ComandaDTO> getComandaActivaByMesa(Long idMesa){
        return comandaRepository.findByIdMesaYEstado(idMesa, EstadoComanda.ABIERTA)
                .map(ComandaMapper::toComandaDTO);
    }

    public void cerrarComanda(Long id){
        Comanda comanda = comandaRepository.findById(id).orElseThrow(() -> new RuntimeException("Comanda no encontrada."));
        comanda.setEstado(EstadoComanda.CERRADA.name());
        comandaRepository.save(comanda);
    }
}