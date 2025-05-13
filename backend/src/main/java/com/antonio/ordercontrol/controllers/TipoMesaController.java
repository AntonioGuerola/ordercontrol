package com.antonio.ordercontrol.controllers;

import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.models.TipoMesa;
import com.antonio.ordercontrol.services.TipoMesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-mesa")
public class TipoMesaController {
    @Autowired
    private TipoMesaService tipoMesaService;

    @GetMapping
    public List<TipoMesa> getAllTipoMesa() {
        return tipoMesaService.getAllTipoMesas();
    }

    @GetMapping("/{id}")
    public TipoMesa getTipoMesaById(@PathVariable Long id) throws RecordNotFoundException {
        return tipoMesaService.getTipoMesaById(id);
    }

    @PostMapping
    public TipoMesa createTipoMesa(@RequestBody TipoMesa tipoMesa) {
        return tipoMesaService.createMesa(tipoMesa);
    }

    @PutMapping("/{id}")
    public TipoMesa updateTipomesa(@PathVariable Long id, @RequestBody TipoMesa tipoMesa) throws RecordNotFoundException {
        return tipoMesaService.updateMesa(id, tipoMesa);
    }

    @PutMapping("/{id}")
    public void deleteTipoMesa(@PathVariable Long id) throws RecordNotFoundException {
        tipoMesaService.deleteTipoMesa(id);
    }
}
