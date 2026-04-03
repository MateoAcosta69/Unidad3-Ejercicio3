package com.programacion4.unidad3ej3.feature.producto.services.impl.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programacion4.unidad3ej3.config.exceptions.BadRequestException;
import com.programacion4.unidad3ej3.config.exceptions.ConflictException;
import com.programacion4.unidad3ej3.feature.producto.dtos.request.ProductoRequestDto;
import com.programacion4.unidad3ej3.feature.producto.dtos.response.ProductoResponseDto;
import com.programacion4.unidad3ej3.feature.producto.models.Producto;
import com.programacion4.unidad3ej3.feature.producto.repositories.IProductoRepository;
import com.programacion4.unidad3ej3.feature.producto.services.interfaces.domain.IProductoCreateService;

@Service
public class ProductoCreateService implements IProductoCreateService {

    @Autowired
    private IProductoRepository productoRepository;

    @Override
    public ProductoResponseDto crearProducto(ProductoRequestDto request) {

        if (request == null) {
            throw new BadRequestException("Datos de producto inválidos");
        }

        List<String> errors = new ArrayList<>();

        if (request.getNombre() == null || request.getNombre().trim().isEmpty()) {
            errors.add("nombre es obligatorio");
        }
        if (request.getCodigo() == null || request.getCodigo().trim().isEmpty()) {
            errors.add("codigo es obligatorio");
        }
        if (request.getDescripcion() == null || request.getDescripcion().trim().isEmpty()) {
            errors.add("descripcion es obligatoria");
        }
        if (request.getPrecio() == null || request.getPrecio() <= 0) {
            errors.add("precio debe ser mayor que cero");
        }
        if (request.getStock() == null || request.getStock() < 0) {
            errors.add("stock no puede ser negativo");
        }

        if (!errors.isEmpty()) {
            throw new BadRequestException("Errores de validación", errors);
        }

        String nombre = capitalizar(request.getNombre().trim());
        String descripcion = capitalizar(request.getDescripcion().trim());

        if (productoRepository.existsByNombre(nombre)) {
            throw new ConflictException("Ya existe un producto con el nombre: " + nombre);
        }

        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setCodigo(request.getCodigo().trim());
        producto.setDescripcion(descripcion);
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setEstaEliminado(false);

        Producto guardado = productoRepository.save(producto);

        ProductoResponseDto response = new ProductoResponseDto();
        response.setId(guardado.getId());
        response.setNombre(guardado.getNombre());
        response.setCodigo(guardado.getCodigo());
        response.setDescripcion(guardado.getDescripcion());
        response.setPrecio(guardado.getPrecio());
        response.setStock(guardado.getStock());

        return response;
    }

    // 🔤 MÉTODO AUXILIAR
    private String capitalizar(String texto) {
        if (texto == null || texto.isEmpty()) return texto;

        return texto.substring(0, 1).toUpperCase() +
               texto.substring(1).toLowerCase();
    }
}