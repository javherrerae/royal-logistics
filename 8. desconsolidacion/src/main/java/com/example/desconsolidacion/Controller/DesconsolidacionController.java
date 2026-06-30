package com.example.desconsolidacion.Controller;

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

import com.example.desconsolidacion.Model.Desconsolidacion;
import com.example.desconsolidacion.Service.DesconsolidacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Gestión de Desconsolidaciones", description = "Endpoints para registrar, consultar y eliminar desconsolidaciones")
@RestController
@RequestMapping("/api/desconsolidaciones")
public class DesconsolidacionController {

    @Autowired
    private DesconsolidacionService service;

    @Operation(summary = "Listar todas las desconsolidaciones",
               description = "Obtiene una lista de todas las desconsolidaciones registradas en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado cuando aplica la búsqueda por identificador"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<Desconsolidacion>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @Operation(summary = "Buscar desconsolidación por ID",
               description = "Obtiene una desconsolidación específica utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado cuando aplica la búsqueda por identificador"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{idDesconsolidacion}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long idDesconsolidacion) {
        Desconsolidacion desconsolidacion = service.buscarPorId(idDesconsolidacion);
        
        if (desconsolidacion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("La desconsolidación con ID " + idDesconsolidacion + " no existe.");
        }
        
        return ResponseEntity.ok(desconsolidacion);
    }

    @Operation(summary = "Registrar desconsolidación",
               description = "Registra una nueva desconsolidación en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recurso registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o regla de negocio incumplida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> registrar(
            @RequestBody Desconsolidacion desconsolidacion) {
        try {
            Desconsolidacion nuevaDesconsolidacion =
                    service.registrar(desconsolidacion);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(nuevaDesconsolidacion);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @Operation(summary = "Buscar desconsolidación por número de factura",
               description = "Obtiene una lista de desconsolidaciones que coinciden con un número de factura específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado cuando aplica la búsqueda por identificador"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/factura/{numeroFactura}")
    public ResponseEntity<List<Desconsolidacion>> buscarPorFactura(
            @PathVariable String numeroFactura) {
        return ResponseEntity.ok(
                service.buscarPorFactura(numeroFactura));
    }

    @Operation(summary = "Buscar desconsolidaciones por cantidad de productos",
               description = "Obtiene una lista de desconsolidaciones que coinciden con una cantidad específica de productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado cuando aplica la búsqueda por identificador"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/cantidad/{cantidadProductos}")
    public ResponseEntity<List<Desconsolidacion>>
    buscarPorCantidadProductos(
            @PathVariable Integer cantidadProductos) {
        return ResponseEntity.ok(
                service.buscarPorCantidadProductos(cantidadProductos));
    }

    @Operation(summary = "Eliminar desconsolidación",
               description = "Elimina una desconsolidación específica utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso eliminado correctamente"),
            @ApiResponse(responseCode = "400", description = "No se pudo eliminar por validación de negocio"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{idDesconsolidacion}")
    public ResponseEntity<?> eliminar(
            @PathVariable Long idDesconsolidacion) {
        try {
            service.eliminar(idDesconsolidacion);
            return ResponseEntity.ok(
                    "Desconsolidación eliminada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}