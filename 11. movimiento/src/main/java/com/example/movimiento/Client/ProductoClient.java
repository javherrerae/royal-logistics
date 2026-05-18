package com.example.movimiento.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Haciendo el cLient y usando el FeignClient, consultamos la Clase y base de datos Producto si existe ese SKU 
// que estamos llamando y lo valide
@FeignClient(name = "producto")
public interface ProductoClient {

    @GetMapping("/api/productos/{sku}")
    Object buscarProducto(@PathVariable String sku);
}