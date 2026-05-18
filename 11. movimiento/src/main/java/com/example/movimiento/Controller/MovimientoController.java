package com.example.movimiento.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.movimiento.Model.Movimiento;
import com.example.movimiento.Service.MovimientoService;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService service;

    // Listamos todos los movimientos
    @GetMapping
    public ResponseEntity<List<Movimiento>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // Registramos un movimiento
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

    // Buscar movimientos por SKU
    @GetMapping("/sku/{sku}")
    public ResponseEntity<List<Movimiento>> buscarPorSku(
            @PathVariable String sku) {
        return ResponseEntity.ok(
                service.buscarPorSku(sku));
    }
    // Buscar por movimiento es importante ya que tenemos la trazabilidad completa de donde se guardaron ESOS productos (y su cantidad)
    // en CUALES ubicaciones


    // Buscar movimientos por ubicación
    @GetMapping("/ubicacion/{idUbicacion}")
    public ResponseEntity<List<Movimiento>> buscarPorUbicacion(
            @PathVariable String idUbicacion) {
        return ResponseEntity.ok(
                service.buscarPorUbicacion(idUbicacion));
    }

    // Buscar movimientos por tipo de movimiento
    @GetMapping("/tipo/{tipoMovimiento}")
    public ResponseEntity<List<Movimiento>> buscarPorTipoMovimiento(
            @PathVariable String tipoMovimiento) {
        return ResponseEntity.ok(
                service.buscarPorTipoMovimiento(tipoMovimiento));
    }

    // Buscar movimientos por desconsolidación
    @GetMapping("/desconsolidacion/{idDesconsolidacion}")
    public ResponseEntity<List<Movimiento>> buscarPorDesconsolidacion(
            @PathVariable Long idDesconsolidacion) {
        return ResponseEntity.ok(
                service.buscarPorDesconsolidacion(
                        idDesconsolidacion));
    }

    // Eliminamos movimiento
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