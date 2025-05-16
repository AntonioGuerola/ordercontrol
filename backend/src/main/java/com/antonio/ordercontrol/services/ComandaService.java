package com.antonio.ordercontrol.services;

import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.models.Comanda;
import com.antonio.ordercontrol.models.EstadoComanda;
import com.antonio.ordercontrol.repositories.ComandaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComandaService {
    @Autowired
    private ComandaRepository comandaRepository;

    public Comanda createComanda(Comanda comanda){
        return comandaRepository.save(comanda);
    }

    public Comanda updateComanda(Long id, Comanda comanda){
        Optional<Comanda> comandaExistente = comandaRepository.findById(id);
        if(comandaExistente.isPresent()){
            Comanda comandaActualizada = comandaExistente.get();
            comandaActualizada.setEstado(comanda.getEstado().name());
            comandaActualizada.setIdMesa(comanda.getIdMesa());
            comandaActualizada.setIdUsuario(comanda.getIdUsuario());
            comandaActualizada.setIdCuenta(comanda.getIdCuenta());
            return comandaRepository.save(comandaActualizada);
        } else {
            throw new RecordNotFoundException("No existe comanda para el id: ", id);
        }
    }

    public void deleteComanda (Long id) throws RecordNotFoundException {
        Optional<Comanda> comandaExistente = comandaRepository.findById(id);
        if(comandaExistente.isPresent()){
            comandaRepository.delete(comandaExistente.get());
        } else {
            throw new RecordNotFoundException("No existe comanda para el id: ", id);
        }
    }

    public Comanda getComandaById(Long id) throws RecordNotFoundException {
        return comandaRepository.findById(id).orElseThrow(() ->  new RecordNotFoundException("No existe comanda para el id: ", id));
    }

    public List<Comanda> getAllComandas(){
        return comandaRepository.findAll();
    }

    public List<Comanda> getComandasByEstado(EstadoComanda estado){
        return comandaRepository.findByEstadoIgnoreCase(estado);
    }

    public List<Comanda> getComandasByMesa(Long idMesa){
        return comandaRepository.findByIdMesa(idMesa);
    }

    public List<Comanda> getComandasByUsuario(Long idUsuario){
        return comandaRepository.findByIdUsuario(idUsuario);
    }

    public Optional<Comanda> getComandaActivaByMesa(Long idMesa){
        return comandaRepository.findByIdMesaYEstado(idMesa, EstadoComanda.ABIERTA);
    }

    public void cerrarComanda(Long id){
        Comanda comanda = comandaRepository.findById(id).orElseThrow(() -> new RuntimeException("Comanda no encontrada."));
        comanda.setEstado(EstadoComanda.CERRADA.name());
        comandaRepository.save(comanda);
    }
}
