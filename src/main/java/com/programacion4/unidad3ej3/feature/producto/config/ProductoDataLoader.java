package com.programacion4.unidad3ej3.feature.producto.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Random;

import com.programacion4.unidad3ej3.feature.producto.models.Producto;
import com.programacion4.unidad3ej3.feature.producto.repositories.IProductoRepository;

@Configuration
public class ProductoDataLoader {

    private static final String[] CATEGORIAS = {
            "Remera", "Buzo", "Pantalón", "Campera", "Chomba", "Zapatillas", "Gorra"
    };

    private static final String[] DESCRIPCIONES = {
            "de algodón premium", "estilo oversize", "con detalles bordados", 
            "impermeable", "de temporada invierno", "edición limitada"
    };

    @Bean
    public CommandLineRunner cargarProductos(IProductoRepository repository) {
        return args -> {
            // Verificamos si ya hay datos para no duplicar en cada reinicio
            if (repository.count() > 0) {
                return;
            }

            Random random = new Random();
            int cantidad = 5 + random.nextInt(6); // Genera entre 5 y 10 productos

            for (int i = 0; i < cantidad; i++) {
                String categoria = CATEGORIAS[random.nextInt(CATEGORIAS.length)];
                String descripcion = DESCRIPCIONES[random.nextInt(DESCRIPCIONES.length)];
                
                Producto producto = new Producto();
                
                // Seteamos los campos según tu modelo
                producto.setNombre(categoria + " " + (i + 1));
                producto.setCodigo("PROD-" + (1000 + i));
                producto.setDescripcion(categoria + " " + descripcion);
                
                // Precios entre 1500.0 y 9500.0
                double precioAleatorio = 1500.0 + (8000.0 * random.nextDouble());
                producto.setPrecio(Math.round(precioAleatorio * 100.0) / 100.0); // Redondeo a 2 decimales
                
                // Stock entre 10 y 100 unidades
                producto.setStock(10 + random.nextInt(91));
                
                // Por defecto no están eliminados
                producto.setEstaEliminado(false);

                repository.save(producto);
            }
            
            System.out.println(">> Se han precargado " + cantidad + " productos de prueba en H2.");
        };
    }
}
