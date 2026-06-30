package logistica.producto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import logistica.producto.model.Producto;
import logistica.producto.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Simulamos el servicio para no tocar la base de datos real
    @MockBean
    private ProductoService productoService;

    @Test
    public void registrar_conDatosValidos_deberiaRetornar201() throws Exception {
        // Arrange: Preparamos un producto simulado y le decimos al mock qué devolver
        Producto productoRecibido = new Producto();
        productoRecibido.setSku("SKU-123");
        productoRecibido.setNombreProducto("Taladro Inalámbrico");
        
        Mockito.when(productoService.registrar(Mockito.any(Producto.class)))
               .thenReturn(productoRecibido);

        // Act: Ejecutamos una petición POST simulada hacia el endpoint
        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoRecibido)))
                
        // Assert: Esperamos que responda HTTP 201 Created y devuelva los datos correctos
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sku").value("SKU-123"))
                .andExpect(jsonPath("$.nombreProducto").value("Taladro Inalámbrico"));
    }

    @Test
    public void registrar_conSkuDuplicado_deberiaRetornar400() throws Exception {
        // Arrange: Simulamos que el service lanza la excepción de duplicidad que tienes programada
        Producto productoDuplicado = new Producto();
        productoDuplicado.setSku("SKU-EXISTENTE");

        Mockito.when(productoService.registrar(Mockito.any(Producto.class)))
               .thenThrow(new RuntimeException("El producto ya existe previamente."));

        // Act: Ejecutamos la petición POST
        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoDuplicado)))
                
        // Assert: Esperamos que el controller atrape la excepción y retorne HTTP 400 Bad Request
                .andExpect(status().isBadRequest());
    }
}