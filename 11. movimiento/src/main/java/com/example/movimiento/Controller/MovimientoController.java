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
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Gestión de Movimientos", description = "Endpoints para registrar, consultar y eliminar movimientos")
@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService service;

    @Operation(summary = "Listar todos los movimientos",
               description = "Obtiene una lista de todos los movimientos registrados en el sistema")
    @GetMapping
    public ResponseEntity<List<Movimiento>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Operation(summary = "Registrar un movimiento",
               description = "Registra un nuevo movimiento en el sistema")
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
    @GetMapping("/ubicacion/{idUbicacion}")
    public ResponseEntity<List<Movimiento>> buscarPorUbicacion(
            @PathVariable String idUbicacion) {
        return ResponseEntity.ok(
                service.buscarPorUbicacion(idUbicacion));
    }

    @Operation(summary = "Buscar movimientos por tipo de movimiento",
               description = "Obtiene una lista de movimientos que coinciden con un tipo de movimiento específico")
    @GetMapping("/tipo/{tipoMovimiento}")
    public ResponseEntity<List<Movimiento>> buscarPorTipoMovimiento(
            @PathVariable String tipoMovimiento) {
        return ResponseEntity.ok(
                service.buscarPorTipoMovimiento(tipoMovimiento));
    }

    @Operation(summary = "Buscar movimientos por desconsolidación",
               description = "Obtiene una lista de movimientos que coinciden con una desconsolidación específica")
    @GetMapping("/desconsolidacion/{idDesconsolidacion}")
    public ResponseEntity<List<Movimiento>> buscarPorDesconsolidacion(
            @PathVariable Long idDesconsolidacion) {
        return ResponseEntity.ok(
                service.buscarPorDesconsolidacion(
                        idDesconsolidacion));
    }

    @Operation(summary = "Eliminar movimiento",
               description = "Elimina un movimiento específico del sistema")
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