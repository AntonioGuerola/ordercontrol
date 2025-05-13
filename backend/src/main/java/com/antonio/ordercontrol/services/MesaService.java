package com.antonio.ordercontrol.services;

import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.models.Mesa;
import com.antonio.ordercontrol.repositories.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MesaService {
    @Autowired
    private MesaRepository mesaRepository;

    public Mesa createMesa(Mesa mesa){
        if (mesa.getTipo() == null){
            throw new IllegalStateException("La mesa debe tener un tipo asignado.");
        }
        return mesaRepository.save(mesa);
    }

    public Mesa updateMesa(Long id, Mesa mesa) throws RecordNotFoundException {
        Optional<Mesa> mesaOptional = mesaRepository.findById(id);
        if (mesaOptional.isPresent()) {
            Mesa mesaToUpdate = mesaOptional.get();
            mesaToUpdate.setTipo(mesa.getTipo());
            mesaToUpdate.setNumMesa( mesa.getNumMesa());
            mesaToUpdate.setEstado(mesa.getEstado());
            mesaToUpdate.setFechaHora( mesa.getFechaHora());
            return mesaRepository.save(mesaToUpdate);
        } else {
            throw new RecordNotFoundException("No existe Mesa para el id: ", id);
        }
    }

    public void deleteMesa(Long id) throws RecordNotFoundException {
        if (mesaRepository.existsById(id)) {
            mesaRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No existe Mesa para el id: ", id);
        }
    }

    public Mesa getMesaById(Long id) throws RecordNotFoundException {
        return mesaRepository.findById(id).orElseThrow(() ->  new RecordNotFoundException("No existe Mesa para el id: ", id));
    }

    public List<Mesa> getAllMesas() {
        return mesaRepository.findAll();
    }

    public List<Mesa> getMesasPorEstado(String estado){
        return mesaRepository.findByEstadoIgnoreCase(estado);
    }

    public List<Mesa> getMesasPorTipo(String tipo){
        return mesaRepository.findByTipoIgnoreCase(tipo);
    }

    public List<Mesa> getMesasPorNumero(int numero){
        return mesaRepository.findByNumMesa(numero);
    }

    public Mesa cambiarEstado(Long id, String nuevoEstado) throws RecordNotFoundException{
        Mesa mesa = getMesaById(id);
        mesa.setEstado(nuevoEstado);
        return mesaRepository.save(mesa);
    }
}
