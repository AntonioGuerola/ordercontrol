package com.antonio.ordercontrol.services;

import com.antonio.ordercontrol.dtos.MesaDTO;
import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.mappers.MesaMapper;
import com.antonio.ordercontrol.models.Mesa;
import com.antonio.ordercontrol.models.TipoMesa;
import com.antonio.ordercontrol.repositories.MesaRepository;
import com.antonio.ordercontrol.repositories.TipoMesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MesaService {

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private TipoMesaRepository tipoMesaRepository;

    public MesaDTO createMesa(MesaDTO mesaDTO){
        TipoMesa tipoMesa = tipoMesaRepository.findByNombreIgnoreCase(mesaDTO.getTipo())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de mesa no encontrado"));

        int numMesasExistentes = mesaRepository.findByTipo(tipoMesa).size();
        int nuevoNumeroMesa = numMesasExistentes + 1;

        Mesa mesa = new Mesa();
        mesa.setTipo(tipoMesa);
        mesa.setNumMesa(nuevoNumeroMesa);
        mesa.setEstado("libre");
        mesa.setFechaHora(mesaDTO.getFechaHora());

        return MesaMapper.toMesaDTO(mesaRepository.save(mesa));
    }

    public MesaDTO updateMesa(Long id, MesaDTO mesaDTO) throws RecordNotFoundException {
        Mesa mesa = mesaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No existe Mesa para el id: ", id));
        TipoMesa tipoMesa = tipoMesaRepository.findByNombreIgnoreCase(mesaDTO.getTipo()).orElseThrow(() -> new IllegalArgumentException("Tipo de mesa no encontrado"));

        mesa.setTipo(tipoMesa);
        mesa.setNumMesa(mesaDTO.getNumMesa());
        mesa.setEstado(mesaDTO.getEstado());
        mesa.setFechaHora(mesaDTO.getFechaHora());

        return MesaMapper.toMesaDTO(mesaRepository.save(mesa));
    }

    public void deleteMesa(Long id) throws RecordNotFoundException {
        if (mesaRepository.existsById(id)) {
            mesaRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No existe Mesa para el id: ", id);
        }
    }

    public MesaDTO getMesaById(Long id) throws RecordNotFoundException {
        Mesa mesa = mesaRepository.findById(id).orElseThrow(() ->  new RecordNotFoundException("No existe Mesa para el id: ", id));
        return MesaMapper.toMesaDTO(mesa);
    }

    public List<MesaDTO> getAllMesas() {
        return mesaRepository.findAll().stream().map(MesaMapper::toMesaDTO).collect(Collectors.toList());
    }

    public List<MesaDTO> getMesasPorEstado(String estado){
        return mesaRepository.findByEstadoIgnoreCase(estado).stream().map(MesaMapper::toMesaDTO).collect(Collectors.toList());
    }

    public List<MesaDTO> getMesasPorTipo(String tipoNombre){
        TipoMesa tipoMesa = tipoMesaRepository.findByNombreIgnoreCase(tipoNombre)
                .orElseThrow(() -> new RuntimeException("Tipo de mesa no encontrado: " + tipoNombre));

        return mesaRepository.findByTipo(tipoMesa)
                .stream()
                .map(MesaMapper::toMesaDTO)
                .collect(Collectors.toList());
    }

    public List<MesaDTO> getMesasPorNumero(int numero){
        return mesaRepository.findByNumMesa(numero).stream().map(MesaMapper::toMesaDTO).collect(Collectors.toList());
    }

    public MesaDTO cambiarEstado(Long id, String nuevoEstado) throws RecordNotFoundException{
        validarEstado(nuevoEstado);
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No existe Mesa para el id: ", id));
        mesa.setEstado(nuevoEstado);
        return MesaMapper.toMesaDTO(mesaRepository.save(mesa));
    }

    private void validarEstado(String estado) {
        if (!estado.equalsIgnoreCase("libre") && !estado.equalsIgnoreCase("ocupada")) {
            throw new IllegalArgumentException("Estado no válido. Solo se permite 'libre' u 'ocupada'");
        }
    }
}
