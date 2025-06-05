package com.antonio.ordercontrol.services;

import com.antonio.ordercontrol.dtos.MesaDTO;
import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.mappers.MesaMapper;
import com.antonio.ordercontrol.models.Comanda;
import com.antonio.ordercontrol.models.Cuenta;
import com.antonio.ordercontrol.models.Mesa;
import com.antonio.ordercontrol.models.TipoMesa;
import com.antonio.ordercontrol.repositories.*;
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

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ComandaRepository comandaRepository;

    @Autowired
    private ComandaProductoRepository ComandaProductoRepository;

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

    public void anularMesa(Long idMesa) throws RecordNotFoundException {
        Mesa mesa = mesaRepository.findById(idMesa)
                .orElseThrow(() -> new RecordNotFoundException("No existe mesa para anular con id: ", idMesa));

        List<Cuenta> cuentas = cuentaRepository.findByIdMesa_Id(idMesa);
        cuentaRepository.deleteAll(cuentas);

        List<Comanda> comandas = comandaRepository.findByIdMesa_Id(Long.valueOf(mesa.getId()));
        for (Comanda comanda : comandas) {
            ComandaProductoRepository.deleteAll(comanda.getComandaproductos());
        }
        comandaRepository.deleteAll(comandas);

        mesa.setEstado("LIBRE");

        mesaRepository.save(mesa);
    }

    public void liberarMesa(Long id) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada con id: " + id));
        mesa.setEstado("LIBRE");
        mesaRepository.save(mesa);
    }
}
