package com.example.movimiento.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Consultamos que la ID de la Ubicación del RACK a la que estamos guardando exista
@FeignClient(name = "warehouse")
public interface WarehouseClient {
    @GetMapping("/api/warehouse/{idUbicacion}")
    Object buscarUbicacion(@PathVariable String idUbicacion);
}