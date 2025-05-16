package com.antonio.ordercontrol.services;

import com.antonio.ordercontrol.dtos.ProductoDTO;
import com.antonio.ordercontrol.exceptions.RecordNotFoundException;
import com.antonio.ordercontrol.mappers.ProductoMapper;
import com.antonio.ordercontrol.models.Categoria;
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
    @Autowired
    private CategoriaService categoriaService;

    public ProductoDTO createProducto(ProductoDTO productoDTO) {
        validarProductoDTO(productoDTO);

        Categoria categoria = categoriaService.getCategoriaById(productoDTO.getIdCategoria());
        Producto producto = ProductoMapper.toProducto(productoDTO, categoria);
        producto = productoRepository.save(producto);
        return ProductoMapper.toProductoDTO(producto);
    }

    public ProductoDTO updateProducto(Long id, ProductoDTO productoDTO) throws RecordNotFoundException {
        Optional<Producto> productoOptional = productoRepository.findById(id);

        if (productoOptional.isPresent()) {
            Producto productoExistente = productoOptional.get();

            Categoria categoria = categoriaService.getCategoriaById(productoDTO.getIdCategoria());

            ProductoMapper.actualizarProducto(productoDTO, productoExistente, categoria);

            validarProducto(productoExistente);

            Producto productoActualizado = productoRepository.save(productoExistente);

            return ProductoMapper.toProductoDTO(productoActualizado);
        } else {
            throw new RecordNotFoundException("No existe Producto para el id: ", id);
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

    public List<Producto> getProductoPorIdCategoria(Long idCategoria){
        return productoRepository.findByIdCategoriaIgnoreCase(idCategoria);
    }

    private void validarProductoDTO(ProductoDTO productoDTO){
        if (productoDTO.getNombre() == null || productoDTO.getNombre().isBlank()){
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }

        if (productoDTO.getPrecio() == null || productoDTO.getPrecio().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("El precio del producto debe ser mayor que 0.");
        }

        if (productoDTO.getTipo() == null || productoDTO.getTipo().isBlank()){
            throw new IllegalArgumentException("El tipo del producto no puede estar vacío.");
        }

        if (productoDTO.getIdCategoria() == null){
            throw new IllegalArgumentException("Debe especificar una categoría válida.");
        }
    }
}