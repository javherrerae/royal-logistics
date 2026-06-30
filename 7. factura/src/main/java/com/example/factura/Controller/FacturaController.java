package com.example.factura.Controller;

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

import com.example.factura.Model.Factura;
import com.example.factura.Service.FacturaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Gestión de Facturas", description = "Endpoints para registrar, consultar y eliminar facturas")
@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService service;

    @Operation(summary = "Listar todas las facturas",
               description = "Obtiene una lista de todas las facturas registradas en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado cuando aplica la búsqueda por identificador"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<Factura>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @Operation(summary = "Buscar factura por número",
               description = "Obtiene una factura específica utilizando su número")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado cuando aplica la búsqueda por identificador"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{numeroFactura}")
    public ResponseEntity<?> buscarPorNumeroFactura(@PathVariable String numeroFactura) {
        Factura factura = service.buscarPorNumeroFactura(numeroFactura);
        
        if (factura == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El número de factura '" + numeroFactura + "' no existe en los registros.");
        }
        
        return ResponseEntity.ok(factura);
    }

    @Operation(summary = "Registrar factura",
               description = "Registra una nueva factura en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recurso registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o regla de negocio incumplida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Factura factura) {

        try {
            Factura nuevaFactura = service.registrar(factura);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(nuevaFactura);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @Operation(summary = "Buscar facturas por estado",
               description = "Obtiene una lista de facturas que coinciden con un estado específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado cuando aplica la búsqueda por identificador"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Factura>> buscarPorEstado(
            @PathVariable String estado) {

        return ResponseEntity.ok(service.buscarPorEstado(estado));
    }

    @Operation(summary = "Buscar facturas por proveedor",
               description = "Obtiene una lista de facturas que coinciden con un proveedor específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado cuando aplica la búsqueda por identificador"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/proveedor/{proveedor}")
    public ResponseEntity<List<Factura>> buscarPorProveedor(
            @PathVariable String proveedor) {

        return ResponseEntity.ok(service.buscarPorProveedor(proveedor));
    }

    @Operation(summary = "Buscar facturas por fecha",
               description = "Obtiene una lista de facturas que coinciden con una fecha específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado cuando aplica la búsqueda por identificador"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/recepcion/{idRecepcion}")
    public ResponseEntity<List<Factura>> buscarPorRecepcion(
            @PathVariable Long idRecepcion) {
        return ResponseEntity.ok(service.buscarPorRecepcion(idRecepcion));
    }

    @Operation(summary = "Borrar facturas por número de orden de compra",
               description = "Elimina una factura específica por su número de orden de compra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso eliminado correctamente"),
            @ApiResponse(responseCode = "400", description = "No se pudo eliminar por validación de negocio"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{numeroFactura}")
    public ResponseEntity<?> eliminar(@PathVariable String numeroFactura) {
        try {
            service.eliminar(numeroFactura);
            return ResponseEntity.ok("Factura eliminada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}