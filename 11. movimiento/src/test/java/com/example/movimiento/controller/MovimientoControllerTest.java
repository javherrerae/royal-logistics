package com.example.movimiento.controller;

import com.example.movimiento.Controller.MovimientoController;
import com.example.movimiento.Model.Movimiento;
import com.example.movimiento.Service.MovimientoService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(MovimientoController.class)
public class MovimientoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Simulamos la capa de servicio para aislar el controlador de Feign y BD
    @MockBean
    private MovimientoService service;

    @Test
    public void registrar_conDatosValidos_deberiaRetornar201() throws Exception {
        // Arrange: Preparamos el movimiento válido que nos envía el cliente
        Movimiento nuevoMovimiento = new Movimiento();
        nuevoMovimiento.setIdMovimiento(1L);
        nuevoMovimiento.setSku("SKU-100");
        nuevoMovimiento.setIdUbicacion("A1-R1-N1");
        nuevoMovimiento.setIdDesconsolidacion(5L);
        nuevoMovimiento.setCantidad(50);
        nuevoMovimiento.setTipoMovimiento("INGRESO");

        // Instruimos al mock qué retornar cuando reciba cualquier objeto Movimiento
        Mockito.when(service.registrar(Mockito.any(Movimiento.class)))
               .thenReturn(nuevoMovimiento);

        // Act: Ejecutamos la petición POST
        mockMvc.perform(post("/api/movimientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoMovimiento)))
                
        // Assert: Validamos el código HTTP 201 y los datos en la respuesta JSON
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sku").value("SKU-100"))
                .andExpect(jsonPath("$.cantidad").value(50))
                .andExpect(jsonPath("$.tipoMovimiento").value("INGRESO"));
    }

    @Test
    public void registrar_conTipoMovimientoInvalido_deberiaRetornar400() throws Exception {
        // Arrange: Simulamos un objeto que viola la regla de negocio
        Movimiento movimientoInvalido = new Movimiento();
        movimientoInvalido.setSku("SKU-100");
        movimientoInvalido.setTipoMovimiento("SALIDA"); // Solo se permite INGRESO

        // Simulamos que el Service lanza tu RuntimeException programada
        Mockito.when(service.registrar(Mockito.any(Movimiento.class)))
               .thenThrow(new RuntimeException("El tipo de movimiento no es válido."));

        // Act: Ejecutamos la petición POST
        mockMvc.perform(post("/api/movimientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimientoInvalido)))
                
        // Assert: Validamos que el Controller lo atrape y devuelva 400 Bad Request
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El tipo de movimiento no es válido."));
    }
}