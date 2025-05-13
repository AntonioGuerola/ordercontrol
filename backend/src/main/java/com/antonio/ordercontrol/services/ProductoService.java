package com.antonio.ordercontrol.services;

import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.models.Producto;
import com.antonio.ordercontrol.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public Producto createProducto(Producto producto) {
        validarProducto(producto);
        producto = productoRepository.save(producto);
        return producto;
    }

    public Producto updateProducto(Long id, Producto producto) throws RecordNotFoundException {
        if (producto.getId() != null) {
            Optional<Producto> productoOptional = productoRepository.findById(id);
            if (productoOptional.isPresent()) {
                Producto newProducto = productoOptional.get();
                newProducto.setNombre(producto.getNombre());
                newProducto.setDescripcion(producto.getDescripcion());
                newProducto.setPrecio(producto.getPrecio());
                newProducto.setTipo(producto.getTipo());
                newProducto.setDisponible(producto.getDisponible());
                newProducto.setCategoria(producto.getCategoria());

                validarProducto(newProducto);
                newProducto = productoRepository.save(newProducto);
                return newProducto;
            } else {
                throw new RecordNotFoundException("No existe Producto para el id: ", producto.getId());
            }
        } else {
            throw new RecordNotFoundException("No hay id en el Producto a actualizar ", 0l);
        }
    }

    public void deleteProducto(Long id) throws RecordNotFoundException {
        Optional<Producto> productoOptional = productoRepository.findById(id);
        if (productoOptional.isPresent()) {
            productoRepository.delete(productoOptional.get());
        }else{
            throw new RecordNotFoundException("No existe Producto para el id: ",id);
        }
    }

    public Producto getProductoById(Long id) throws RecordNotFoundException {
        Optional<Producto> producto = productoRepository.findById(id);
        if(producto.isPresent()){
            return producto.get();
        }else{
            throw new RecordNotFoundException("No existe Producto para el id: ",id);
        }
    }

    public List<Producto> getAllProductos() {
        List<Producto> productosList = productoRepository.findAll();
        if(productosList.size()>0){
            return productosList;
        }else{
            return new ArrayList<Producto>();
        }
    }

    public List<Producto> getProductosDisponibles(){
        return productoRepository.findByDisponibleTrue();
    }

    public List<Producto> getProductosPorTipo(String tipo){
        return productoRepository.findByTipoIgnoreCase(tipo);
    }

    public Producto cambiarDisponibilidad(Long id, boolean disponible) throws RecordNotFoundException {
        Producto producto = getProductoById(id);
        producto.setDisponible(disponible);
        return productoRepository.save(producto);
    }

    private void validarProducto(Producto producto){
        if (producto.getNombre() == null || producto.getNombre().isBlank()){
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }

        if (producto.getPrecio() == null || producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("El precio del producto debe ser mayor que cero.");
        }

        if (producto.getTipo() == null || producto.getTipo().isBlank()){
            throw new IllegalArgumentException("El tipo del producto no puede estar vacío.");
        }
    }
}

