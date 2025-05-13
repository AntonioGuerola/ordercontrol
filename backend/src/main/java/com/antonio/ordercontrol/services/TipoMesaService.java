package com.antonio.ordercontrol.services;

import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.models.Mesa;
import com.antonio.ordercontrol.models.TipoMesa;
import com.antonio.ordercontrol.repositories.TipoMesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoMesaService {
    @Autowired
    private TipoMesaRepository tipoMesaRepository;

    public List<TipoMesa> getAllTipoMesas() {
        return tipoMesaRepository.findAll();
    }

    public TipoMesa getTipoMesaById(Long id) throws RecordNotFoundException {
        return tipoMesaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Tipo de mesa no encontrado.", id));
    }

    public TipoMesa createMesa(TipoMesa tipoMesa) {
        validarTipoMesa(tipoMesa.getNombre());
        return tipoMesaRepository.save(tipoMesa);
    }

    public TipoMesa updateMesa (Long id, TipoMesa tipoMesa) throws RecordNotFoundException {
        TipoMesa tipoMesaExistente = getTipoMesaById(id);
        tipoMesaExistente.setNombre(tipoMesa.getNombre());
        return tipoMesaRepository.save(tipoMesaExistente);
    }

    public void deleteTipoMesa(Long id) throws RecordNotFoundException {
        TipoMesa tipoMesaExistente = getTipoMesaById(id);
        tipoMesaRepository.delete(tipoMesaExistente);
    }

    private void  validarTipoMesa(String nombre) {
        validarTipoMesa(nombre, null);
    }

    private void validarTipoMesa(String nombre, Long idTipoMesa) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalStateException("El nombre del tipo de mesa no puede estar vacío.");
        }

        tipoMesaRepository.findByNombreIgnoreCase(nombre).ifPresent(existing -> {
            if (idTipoMesa != null || !existing.getId().equals(idTipoMesa)) {
                throw new IllegalStateException("Ya existe un tipo de mesa con ese nombre.");
            }
        });
    }
}
