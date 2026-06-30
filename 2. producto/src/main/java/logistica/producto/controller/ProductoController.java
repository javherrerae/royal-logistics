package logistica.producto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import logistica.producto.model.Producto;
import logistica.producto.service.ProductoService;

@Tag(name = "Gestión de Productos", description = "Endpoints para registrar, consultar y eliminar productos")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    @Autowired
    private ProductoService service;

    @Operation(summary = "Listar todos los productos",
               description = "Obtiene una lista de todos los productos registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado cuando aplica la búsqueda por identificador"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Operation(summary = "Buscar producto por SKU",
               description = "Obtiene un producto específico utilizando su SKU")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado cuando aplica la búsqueda por identificador"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/sku/{sku}")
    public ResponseEntity<?> buscarPorSku(@PathVariable String sku) {
        Producto producto = service.buscarPorSku(sku);
        if (producto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El producto con SKU " + sku + " no existe.");
        }
        return ResponseEntity.ok(producto);
    }

    @Operation(summary = "Registrar producto",
               description = "Registra un nuevo producto en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recurso registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o regla de negocio incumplida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Producto producto) {
        try {
            Producto nuevoProducto = service.registrar(producto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(nuevoProducto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @Operation(summary = "Buscar productos por categoría",
               description = "Obtiene una lista de productos que pertenecen a una categoría específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado cuando aplica la búsqueda por identificador"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Producto>> buscarPorCategoria(
            @PathVariable String categoria) {
        return ResponseEntity.ok(service.buscarPorCategoria(categoria));
    }

    @Operation(summary = "Buscar productos por desconsolidación",
               description = "Obtiene una lista de productos que pertenecen a una desconsolidación específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado cuando aplica la búsqueda por identificador"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/desconsolidacion/{idDesconsolidacion}")
    public ResponseEntity<List<Producto>> buscarPorDesconsolidacion(
            @PathVariable Long idDesconsolidacion) {
        return ResponseEntity.ok(
                service.buscarPorDesconsolidacion(idDesconsolidacion));
    }

    @Operation(summary = "Buscar productos por nombre",
               description = "Obtiene una lista de productos que coinciden con un nombre específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado cuando aplica la búsqueda por identificador"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/nombre/{nombreProducto}")
    public ResponseEntity<List<Producto>> buscarPorNombre(
            @PathVariable String nombreProducto) {
        return ResponseEntity.ok(
                service.buscarPorNombre(nombreProducto));
    }

    @Operation(summary = "Eliminar producto",
               description = "Elimina un producto específico del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso eliminado correctamente"),
            @ApiResponse(responseCode = "400", description = "No se pudo eliminar por validación de negocio"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{sku}")
    public ResponseEntity<?> eliminar(@PathVariable String sku) {
        try {
            service.eliminar(sku);
            return ResponseEntity.ok("Producto eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}