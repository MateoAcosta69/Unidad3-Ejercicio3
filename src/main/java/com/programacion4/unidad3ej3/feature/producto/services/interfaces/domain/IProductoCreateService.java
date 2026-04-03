package com.programacion4.unidad3ej3.feature.producto.services.interfaces.domain;

import com.programacion4.unidad3ej3.feature.producto.dtos.request.ProductoRequestDto;
import com.programacion4.unidad3ej3.feature.producto.dtos.response.ProductoResponseDto;

public interface IProductoCreateService {

    ProductoResponseDto crearProducto(ProductoRequestDto request);
}