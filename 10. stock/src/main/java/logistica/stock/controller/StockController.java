package logistica.stock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import logistica.stock.model.Stock;
import logistica.stock.service.StockServiceServ;

@Tag(name = "Gestión de Stock", description = "Endpoints para registrar, consultar y eliminar stock de productos")
@RestController
@RequestMapping("/api/stock")
public class StockController {

    @Autowired
    private StockServiceServ service;

    @Operation(summary = "Listar todo el stock",
               description = "Obtiene una lista de todo el stock registrado en el sistema, incluyendo productos, ubicaciones y cantidades")
    @GetMapping
    public ResponseEntity<List<Stock>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Operation(summary = "Inicializar stock",
               description = "Registra un nuevo stock en el sistema. Si el SKU y la ubicación ya existen, se actualizará la cantidad existente.")
    @PostMapping("/inicializar")
    public ResponseEntity<Stock> inicializarStock(@RequestBody @NonNull Stock stock) {
        Stock nuevoStock = service.inicializarStock(stock);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoStock);
}

    @Operation(summary = "Registrar o actualizar stock",
               description = "Registra un nuevo stock en el sistema o incrementa las existencias si ya existe en la ubicación.")
    @PostMapping
    public ResponseEntity<?> registrarOActualizar(@RequestBody Stock stock) {
        try {
            Stock nuevoStock = service.registrarOActualizar(stock);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoStock);
        } catch (RuntimeException e) {
            // Captura las excepciones de lógica de negocio (ej: si el SKU no existe en Eureka)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Buscar stock por SKU",
               description = "Obtiene una lista de stock que coinciden con un SKU específico")
    @GetMapping("/producto/sku/{sku}")
    public ResponseEntity<List<Stock>> buscarPorSku(@PathVariable String sku) {
        return ResponseEntity.ok(service.buscarPorSku(sku));
    }

    @Operation(summary = "Buscar stock por ubicación",
               description = "Obtiene una lista de stock que coinciden con una ubicación específica")
    @GetMapping("/ubicacion/{idUbicacion}")
    public ResponseEntity<List<Stock>> buscarPorUbicacion(@PathVariable String idUbicacion) {
        return ResponseEntity.ok(service.buscarPorUbicacion(idUbicacion));
    }

    /* 
    // 5. Descontar stock (Simula una salida de mercancía o un movimiento interno)
    @PutMapping("/descontar")
    public ResponseEntity<?> descontarStock(
            @RequestParam String sku,
            @RequestParam String idUbicacion,
            @RequestParam Integer cantidad) {
        try {
            Stock stockActualizado = service.descontarStock(sku, idUbicacion, cantidad);
            return ResponseEntity.ok(stockActualizado);
        } catch (RuntimeException e) {
            // Captura errores como "Stock insuficiente"
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 6. Reporte de alertas: Obtener stock crítico por debajo de un límite
    @GetMapping("/critico/{limite}")
    public ResponseEntity<List<Stock>> obtenerStockCritico(@PathVariable Integer limite) {
        return ResponseEntity.ok(service.obtenerStockCritico(limite));
    }
        */
}