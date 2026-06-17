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
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Gestión de Facturas", description = "Endpoints para registrar, consultar y eliminar facturas")
@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService service;

    @Operation(summary = "Listar todas las facturas",
               description = "Obtiene una lista de todas las facturas registradas en el sistema")
    @GetMapping
    public ResponseEntity<List<Factura>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @Operation(summary = "Buscar factura por número",
               description = "Obtiene una factura específica utilizando su número")
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
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Factura>> buscarPorEstado(
            @PathVariable String estado) {

        return ResponseEntity.ok(service.buscarPorEstado(estado));
    }

    @Operation(summary = "Buscar facturas por proveedor",
               description = "Obtiene una lista de facturas que coinciden con un proveedor específico")
    @GetMapping("/proveedor/{proveedor}")
    public ResponseEntity<List<Factura>> buscarPorProveedor(
            @PathVariable String proveedor) {

        return ResponseEntity.ok(service.buscarPorProveedor(proveedor));
    }

    @Operation(summary = "Buscar facturas por fecha",
               description = "Obtiene una lista de facturas que coinciden con una fecha específica")
    @GetMapping("/recepcion/{idRecepcion}")
    public ResponseEntity<List<Factura>> buscarPorRecepcion(
            @PathVariable Long idRecepcion) {
        return ResponseEntity.ok(service.buscarPorRecepcion(idRecepcion));
    }

    @Operation(summary = "Buscar facturas por número de orden de compra",
               description = "Obtiene una lista de facturas que coinciden con un número de orden de compra específico")
    @DeleteMapping("/{nFactura}")
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