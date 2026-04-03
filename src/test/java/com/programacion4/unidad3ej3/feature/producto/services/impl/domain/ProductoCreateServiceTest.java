package com.programacion4.unidad3ej3.feature.producto.services.impl.domain;

import com.programacion4.unidad3ej3.config.exceptions.BadRequestException;
import com.programacion4.unidad3ej3.config.exceptions.ConflictException;
import com.programacion4.unidad3ej3.feature.producto.dtos.request.ProductoRequestDto;
import com.programacion4.unidad3ej3.feature.producto.dtos.response.ProductoResponseDto;
import com.programacion4.unidad3ej3.feature.producto.models.Producto;
import com.programacion4.unidad3ej3.feature.producto.repositories.IProductoRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoCreateServiceTest {

    @Mock
    private IProductoRepository productoRepository;

    @InjectMocks
    private ProductoCreateService productoCreateService;

    @Test
    void crearProducto_exitoso() {
        ProductoRequestDto request = new ProductoRequestDto();
        request.setNombre("manzana");
        request.setCodigo("MZ001");
        request.setDescripcion("fruta roja");
        request.setPrecio(10.0);
        request.setStock(5);

        when(productoRepository.existsByNombre("Manzana")).thenReturn(false);

        Producto productoGuardado = new Producto(1L, "Manzana", "MZ001", "Fruta roja", 10.0, 5, false);
        when(productoRepository.save(any(Producto.class))).thenReturn(productoGuardado);

        ProductoResponseDto response = productoCreateService.crearProducto(request);

        assertEquals(1L, response.getId());
        assertEquals("Manzana", response.getNombre());
        assertEquals("MZ001", response.getCodigo());
        assertEquals("Fruta roja", response.getDescripcion());
        assertEquals(10.0, response.getPrecio());
        assertEquals(5, response.getStock());

        verify(productoRepository).existsByNombre("Manzana");
        verify(productoRepository).save(any(Producto.class));
    }

    @Test
    void crearProducto_rechazaDuplicado() {
        ProductoRequestDto request = new ProductoRequestDto();
        request.setNombre("manzana");
        request.setCodigo("MZ001");
        request.setDescripcion("fruta roja");
        request.setPrecio(10.0);
        request.setStock(5);

        when(productoRepository.existsByNombre("Manzana")).thenReturn(true);

        ConflictException ex = assertThrows(ConflictException.class, () -> productoCreateService.crearProducto(request));
        assertTrue(ex.getMessage().contains("Ya existe un producto"));

        verify(productoRepository).existsByNombre("Manzana");
        verify(productoRepository, never()).save(any(Producto.class));
    }

    @Test
    void crearProducto_rechazaDatosInvalidos() {
        ProductoRequestDto request = new ProductoRequestDto();
        request.setNombre("");
        request.setCodigo(" ");
        request.setDescripcion(null);
        request.setPrecio(-1.0);
        request.setStock(-5);

        BadRequestException ex = assertThrows(BadRequestException.class, () -> productoCreateService.crearProducto(request));
        assertTrue(ex.getErrors().size() >= 1);

        verify(productoRepository, never()).existsByNombre(anyString());
        verify(productoRepository, never()).save(any(Producto.class));
    }
}
