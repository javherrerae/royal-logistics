package com.example.movimiento.Controller;

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

import com.example.movimiento.Model.Movimiento;
import com.example.movimiento.Service.MovimientoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Gestión de Movimientos", description = "Endpoints para registrar, consultar y eliminar movimientos")
@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService service;

    @Operation(summary = "Listar todos los movimientos",
               description = "Obtiene una lista de todos los movimientos registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado cuando aplica la búsqueda por identificador"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<Movimiento>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Operation(summary = "Registrar un movimiento",
               description = "Registra un nuevo movimiento en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recurso registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o regla de negocio incumplida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> registrar(
            @RequestBody Movimiento movimiento) {
        try {
            Movimiento nuevoMovimiento =
                    service.registrar(movimiento);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(nuevoMovimiento);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @Operation(summary = "Buscar movimientos por SKU",
               description = "Obtiene una lista de movimientos que coinciden con un SKU específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado cuando aplica la búsqueda por identificador"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/sku/{sku}")
    public ResponseEntity<List<Movimiento>> buscarPorSku(
            @PathVariable String sku) {
        return ResponseEntity.ok(
                service.buscarPorSku(sku));
    }
    // Buscar por movimiento es importante ya que tenemos la trazabilidad completa de donde se guardaron ESOS productos (y su cantidad)
    // en CUALES ubicaciones


    @Operation(summary = "Buscar movimientos por ubicación",
               description = "Obtiene una lista de movimientos que coinciden con una ubicación específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado cuando aplica la búsqueda por identificador"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/ubicacion/{idUbicacion}")
    public ResponseEntity<List<Movimiento>> buscarPorUbicacion(
            @PathVariable String idUbicacion) {
        return ResponseEntity.ok(
                service.buscarPorUbicacion(idUbicacion));
    }

    @Operation(summary = "Buscar movimientos por tipo de movimiento",
               description = "Obtiene una lista de movimientos que coinciden con un tipo de movimiento específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado cuando aplica la búsqueda por identificador"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/tipo/{tipoMovimiento}")
    public ResponseEntity<List<Movimiento>> buscarPorTipoMovimiento(
            @PathVariable String tipoMovimiento) {
        return ResponseEntity.ok(
                service.buscarPorTipoMovimiento(tipoMovimiento));
    }

    @Operation(summary = "Buscar movimientos por desconsolidación",
               description = "Obtiene una lista de movimientos que coinciden con una desconsolidación específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado cuando aplica la búsqueda por identificador"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/desconsolidacion/{idDesconsolidacion}")
    public ResponseEntity<List<Movimiento>> buscarPorDesconsolidacion(
            @PathVariable Long idDesconsolidacion) {
        return ResponseEntity.ok(
                service.buscarPorDesconsolidacion(
                        idDesconsolidacion));
    }

    @Operation(summary = "Eliminar movimiento",
               description = "Elimina un movimiento específico del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso eliminado correctamente"),
            @ApiResponse(responseCode = "400", description = "No se pudo eliminar por validación de negocio"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{idMovimiento}")
    public ResponseEntity<?> eliminar(
            @PathVariable Long idMovimiento) {
        try {
            service.eliminar(idMovimiento);
            return ResponseEntity.ok(
                    "Movimiento eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}