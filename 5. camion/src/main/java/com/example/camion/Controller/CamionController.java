package com.example.camion.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.camion.Model.Camion;
import com.example.camion.Service.CamionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Gestión de Camiones", description = "Endpoints para registrar, consultar y eliminar camiones")
@RestController
@RequestMapping("/api/camiones")
public class CamionController {

    private final CamionService camionService;

    public CamionController(CamionService camionService) {
        this.camionService = camionService;
    }

    @Operation(summary = "Listar todos los camiones",
               description = "Obtiene una lista de todos los camiones registrados en el sistema")
    @GetMapping
    public ResponseEntity<List<Camion>> listar() {
        List<Camion> camiones = camionService.listarTodos();
        return ResponseEntity.ok(camiones);
    }

    @Operation(summary = "Buscar camión por patente",
               description = "Obtiene un camión específico utilizando su patente")
    @GetMapping("/patente/{patente}")
    public ResponseEntity<?> buscarPorPatente(@PathVariable String patente) {
        // Buscamos el camión en el servicio
        Camion camion = camionService.buscarPorPatente(patente); 
    
        if (camion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El camión con patente '" + patente + "' no está registrado.");
            }
        
            return ResponseEntity.ok(camion);
        }

    @Operation(summary = "Registrar camión",
               description = "Registra un nuevo camión en el sistema")
    @PostMapping
    public ResponseEntity<Camion> crear(@Valid @RequestBody Camion camion) {
        if (camion == null) {
            return ResponseEntity.badRequest().build();
        }

        Camion nuevoCamion = camionService.guardar(camion);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCamion);
    }

    @Operation(summary = "Eliminar camión",
               description = "Elimina un camión específico del sistema")
    @DeleteMapping("/{patente}")
    public ResponseEntity<?> eliminarCamion(@PathVariable String patente) {
        boolean eliminado = camionService.eliminarCamionPorPatente(patente);
        
        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede eliminar: El camión con patente '" + patente + "' no existe.");
        }
        
        return ResponseEntity.ok("Camión con patente '" + patente + "' eliminado exitosamente.");
    }
}
