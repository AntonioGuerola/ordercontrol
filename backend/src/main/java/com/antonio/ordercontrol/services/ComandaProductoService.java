package com.antonio.ordercontrol.services;

import com.antonio.ordercontrol.dtos.ComandaProductoDTO;
import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.mappers.ComandaProductoMapper;
import com.antonio.ordercontrol.models.Comandaproducto;
import com.antonio.ordercontrol.repositories.ComandaProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComandaProductoService {

    @Autowired
    private ComandaProductoRepository comandaProductoRepository;

    @Autowired
    private ComandaProductoMapper comandaProductoMapper;

    public ComandaProductoDTO createComandaProducto(ComandaProductoDTO comandaProductoDTO){
        Comandaproducto comandaproducto = comandaProductoMapper.toEntity(comandaProductoDTO);
        return comandaProductoMapper.toComandaProductoDTO(comandaProductoRepository.save(comandaproducto));
    }

    public ComandaProductoDTO updateComandaProducto(Long id, ComandaProductoDTO comandaProductoDTO) throws RecordNotFoundException {
        Comandaproducto comandaProductoExistente = comandaProductoRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No hay ComandaProducto para el id: ", id));
        comandaProductoExistente.setCantidad(comandaProductoDTO.getCantidad());
        comandaProductoExistente.setPrecioUnitario(comandaProductoDTO.getPrecioUnitario());

        if (!comandaProductoExistente.getProducto().getId().equals(comandaProductoDTO.getIdProducto())) {
            comandaProductoExistente.setProducto(comandaProductoMapper.toEntity(comandaProductoDTO).getProducto());
        }

        return comandaProductoMapper.toComandaProductoDTO(comandaProductoRepository.save(comandaProductoExistente));
    }

    public void deleteComandaProducto(Long id) throws RecordNotFoundException {
        if (!comandaProductoRepository.existsById(id)) {
            throw new RecordNotFoundException("No hay ComandaProducto para el id: ", id);
        }
        comandaProductoRepository.deleteById(id);
    }

    public ComandaProductoDTO getComandaProductoById(Long id) throws RecordNotFoundException{
        return comandaProductoMapper.toComandaProductoDTO(comandaProductoRepository.findById(id).orElseThrow(() ->  new RecordNotFoundException("No existe ComandaProducto con id: ", id)));
    }

    public List<ComandaProductoDTO> getAllComandaProducto(){
        return comandaProductoRepository.findAll().stream().map(comandaProductoMapper::toComandaProductoDTO).collect(Collectors.toList());
    }

    public List<ComandaProductoDTO> getComandaProductoByComandaId(Long id){
        return comandaProductoRepository.findByComandaId(id).stream().map(comandaProductoMapper::toComandaProductoDTO).collect(Collectors.toList());
    }

    public ComandaProductoDTO agregarProductoAComanda(Long idComanda, ComandaProductoDTO comandaProductoDTO){
        comandaProductoDTO.setIdComanda(idComanda);
        Comandaproducto comandaproducto = comandaProductoMapper.toEntity(comandaProductoDTO);
        Comandaproducto comandaProductoExistente = comandaProductoRepository.save(comandaproducto);

        return comandaProductoMapper.toComandaProductoDTO(comandaProductoRepository.save(comandaproducto));
    }
}