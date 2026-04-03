package com.programacion4.unidad3ej3.feature.producto.controllers.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programacion4.unidad3ej3.feature.producto.dtos.request.ProductoRequestDto;
import com.programacion4.unidad3ej3.feature.producto.dtos.response.ProductoResponseDto;
import com.programacion4.unidad3ej3.feature.producto.services.interfaces.domain.IProductoCreateService;

@RestController
@RequestMapping("/productos")
public class ProductoCreateController {

    @Autowired
    private IProductoCreateService productoCreateService;

    @PostMapping
    public ResponseEntity<ProductoResponseDto> crearProducto(
            @RequestBody ProductoRequestDto request) {

        ProductoResponseDto response = productoCreateService.crearProducto(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
