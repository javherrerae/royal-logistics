package com.example.movimiento.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Consultamos que la id de la Desconsolidación exista
@FeignClient(name = "desconsolidacion")
public interface DesconsolidacionClient {
    @GetMapping("/api/desconsolidaciones/{idDesconsolidacion}")
    Object buscarDesconsolidacion(@PathVariable Long idDesconsolidacion);
}