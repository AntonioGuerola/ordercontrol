package com.antonio.ordercontrol.services;

import com.antonio.ordercontrol.dtos.TipoMesaDTO;
import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.mappers.TipoMesaMapper;
import com.antonio.ordercontrol.models.TipoMesa;
import com.antonio.ordercontrol.repositories.TipoMesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoMesaService {
    @Autowired
    private TipoMesaRepository tipoMesaRepository;

    public List<TipoMesaDTO> getAllTipoMesas() {
        return tipoMesaRepository.findAll().stream().map(TipoMesaMapper::toTipoMesaDTO).toList();
    }

    public TipoMesaDTO getTipoMesaById(Long id) throws RecordNotFoundException {
        TipoMesa tipoMesa = tipoMesaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Tipo de mesa no encontrado.", id));
        return TipoMesaMapper.toTipoMesaDTO(tipoMesa);
    }

    public TipoMesaDTO createMesa(TipoMesaDTO tipoMesaDTO) {
        validarTipoMesaDTO(tipoMesaDTO);
        TipoMesa tipoMesa = TipoMesaMapper.toTipoMesa(tipoMesaDTO);
        tipoMesa =  tipoMesaRepository.save(tipoMesa);
        return TipoMesaMapper.toTipoMesaDTO(tipoMesa);
    }

    public TipoMesaDTO updateMesa (Long id, TipoMesaDTO tipoMesaDTO) throws RecordNotFoundException {
        TipoMesa tipoMesaExistente = tipoMesaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No existe un tipo de mesa con id: ", id));
        TipoMesaMapper.updateTipoMesa(tipoMesaDTO, tipoMesaExistente);
        tipoMesaExistente =  tipoMesaRepository.save(tipoMesaExistente);
        return TipoMesaMapper.toTipoMesaDTO(tipoMesaExistente);
    }

    public void deleteTipoMesa(Long id) throws RecordNotFoundException {
        TipoMesa tipoMesaExistente = tipoMesaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No existe un tipo de mesa con id: ", id));
        tipoMesaRepository.delete(tipoMesaExistente);
    }

    private void validarTipoMesaDTO(TipoMesaDTO tipoMesaDTO) {
        if (tipoMesaDTO.getNombre() == null || tipoMesaDTO.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del tipo de mesa no puede estar vacío.");
        }

        if (tipoMesaRepository.existsByNombreIgnoreCase(tipoMesaDTO.getNombre())) {
            throw new IllegalArgumentException("Ya existe un tipo de mesa con ese nombre.");
        }
    }
}
