package com.example.warehouse.Controller;

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

import com.example.warehouse.Model.Warehouse;
import com.example.warehouse.Service.WarehouseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Gestión de Ubicaciones", description = "Endpoints para registrar, consultar y eliminar ubicaciones en la bodega")
@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseService service;

    @Operation(summary = "Listar todas las ubicaciones",
               description = "Obtiene una lista de todas las ubicaciones registradas en la bodega")
    @GetMapping
    public ResponseEntity<List<Warehouse>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @Operation(summary = "Buscar ubicación por ID de ubicación",
               description = "Obtiene una ubicación específica utilizando su ID de ubicación")
    @GetMapping("/ubicacion/{idUbicacion}")
    public ResponseEntity<?> buscarPorIdUbicacion(@PathVariable String idUbicacion) {
        Warehouse warehouse = service.buscarPorIdUbicacion(idUbicacion);
        
        if (warehouse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("La ubicación física '" + idUbicacion + "' no existe en los registros de la bodega.");
        }
        
        return ResponseEntity.ok(warehouse);
    }

    @Operation(summary = "Registrar ubicación",
               description = "Registra una nueva ubicación en la bodega")
    @PostMapping
    public ResponseEntity<?> registrar(
            @RequestBody Warehouse warehouse) {
        try {
            Warehouse nuevaUbicacion =
                    service.registrar(warehouse);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(nuevaUbicacion);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @Operation(summary = "Buscar ubicación por pasillo",
               description = "Obtiene una lista de ubicaciones que coinciden con un pasillo específico")
    @GetMapping("/pasillo/{pasillo}")
    public ResponseEntity<List<Warehouse>> buscarPorPasillo(
            @PathVariable String pasillo) {
        return ResponseEntity.ok(
                service.buscarPorPasillo(pasillo));
    }

    @Operation(summary = "Buscar ubicación por rack",
               description = "Obtiene una lista de ubicaciones que coinciden con un rack específico")
    @GetMapping("/rack/{rack}")
    public ResponseEntity<List<Warehouse>> buscarPorRack(
            @PathVariable String rack) {
        return ResponseEntity.ok(
                service.buscarPorRack(rack));
    }

    @Operation(summary = "Buscar ubicación por nivel",
               description = "Obtiene una lista de ubicaciones que coinciden con un nivel específico")
    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<Warehouse>> buscarPorNivel(
            @PathVariable Integer nivel) {
        return ResponseEntity.ok(
                service.buscarPorNivel(nivel));
    }

    @Operation(summary = "Eliminar ubicación",
               description = "Elimina una ubicación específica utilizando su ID de ubicación")
    @DeleteMapping("/{idUbicacion}")
    public ResponseEntity<?> eliminar(
            @PathVariable String idUbicacion) {
        try {
            service.eliminar(idUbicacion);
            return ResponseEntity.ok(
                    "Ubicación eliminada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}