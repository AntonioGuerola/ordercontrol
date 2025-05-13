package com.antonio.ordercontrol.services;

import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.models.Comandaproducto;
import com.antonio.ordercontrol.repositories.ComandaProductoRepository;
import com.antonio.ordercontrol.repositories.ComandaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComandaProductoService {
    @Autowired
    private ComandaRepository comandaRepository;
    @Autowired
    private ComandaProductoRepository comandaProductoRepository;

    public Comandaproducto createComandaProducto(Comandaproducto comandaProducto){
        return comandaProductoRepository.save(comandaProducto);
    }

    public Comandaproducto updateComandaProducto(Long id, Comandaproducto comandaProducto) throws RecordNotFoundException {
        Optional<Comandaproducto> comandaProductoExistente = comandaProductoRepository.findById(id);
        if (comandaProductoExistente.isPresent()) {
            Comandaproducto comandaProductoUpdate = comandaProductoExistente.get();
            comandaProductoUpdate.setCantidad(comandaProducto.getCantidad());
            comandaProductoUpdate.setComanda(comandaProducto.getComanda());
            comandaProductoUpdate.setProducto(comandaProductoUpdate.getProducto());
            comandaProductoUpdate.setPrecioUnitario(comandaProductoUpdate.getPrecioUnitario());
            return comandaProductoRepository.save(comandaProductoUpdate);
        } else {
            throw new RecordNotFoundException("No existe ComandaProducto con eee id: ", id);
        }
    }

    public void deleteComandaProducto(Long id) throws RecordNotFoundException {
        Optional<Comandaproducto> comandaProductoExistente = comandaProductoRepository.findById(id);
        if (comandaProductoExistente.isPresent()) {
            comandaProductoRepository.delete(comandaProductoExistente.get());
        } else {
            throw new RecordNotFoundException("No existe ComandaProducto con id: ", id);
        }
    }

    public Comandaproducto getComandaProductoById(Long id) throws RecordNotFoundException{
        return comandaProductoRepository.findById(id).orElseThrow(() ->  new RecordNotFoundException("No existe ComandaProducto con id: ", id));
    }

    public List<Comandaproducto> getAllComandaProducto(){
        return comandaProductoRepository.findAll();
    }

    public List<Comandaproducto> getComandaProductoByComandaId(Long id){
        return comandaProductoRepository.findByComandaId(id);
    }
}
