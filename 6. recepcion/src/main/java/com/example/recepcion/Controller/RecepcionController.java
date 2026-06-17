package com.example.recepcion.Controller;

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

import com.example.recepcion.Model.Recepcion;
import com.example.recepcion.Service.RecepcionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Gestión de Recepciones", description = "Endpoints para registrar, consultar y eliminar recepciones")
@RestController
@RequestMapping("/api/recepciones")
public class RecepcionController {

    @Autowired
    private RecepcionService service;

    @Operation(summary = "Listar todas las recepciones",
               description = "Obtiene una lista de todas las recepciones registradas en el sistema")
    @GetMapping
    public ResponseEntity<List<Recepcion>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas()); 
    }

    @Operation(summary = "Buscar recepción por ID",
               description = "Obtiene una recepción específica utilizando su ID")
    @GetMapping("/id/{idRecepcion}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long idRecepcion) {
        Recepcion recepcion = service.buscarPorId(idRecepcion);
        
        if (recepcion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró ningún registro de recepción con el ID: " + idRecepcion);
        }
        
        return ResponseEntity.ok(recepcion);
}

    @Operation(summary = "Registrar recepción",
            description = "Registra una nueva recepción en el sistema")
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Recepcion recepcion) {
        try {
            Recepcion nuevaRecepcion = service.registrar(recepcion);

            return ResponseEntity.status(HttpStatus.CREATED)
                                .body(nuevaRecepcion);
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(e.getMessage());
        }
    }

    @Operation(summary = "Buscar recepciones por estado",
               description = "Obtiene una lista de recepciones filtradas por su estado")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Recepcion>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.buscarPorEstado(estado));
    }

    @Operation(summary = "Buscar recepciones por patente",
               description = "Obtiene una lista de recepciones filtradas por su patente")
    @GetMapping("/patente/{patente}")
    public ResponseEntity<List<Recepcion>> buscarPorPatente (@PathVariable String patente) {
        return ResponseEntity.ok(service.buscarPorPatente(patente));
    }

    @Operation(summary = "Buscar recepciones por número de andén",
               description = "Obtiene una lista de recepciones filtradas por su número de andén")
    @GetMapping("/anden/{nAnden}")
    public ResponseEntity<List<Recepcion>> buscarPorAnden(@PathVariable Long nAnden) {
        return ResponseEntity.ok(service.buscarPorAnden(nAnden));
    }

    @Operation(summary = "Eliminar recepción",
                description = "Elimina una recepción específica del sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            service.eliminar(id);

            return ResponseEntity.ok("Recepcion eliminada correctamente");
            // Usamos el RuntimeException para atrapar el error y mandarlo con un 400 BAD REQUEST
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(e.getMessage());

        }
    }   
}
