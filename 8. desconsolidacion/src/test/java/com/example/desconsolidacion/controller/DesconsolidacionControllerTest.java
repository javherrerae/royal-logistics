package com.example.desconsolidacion.controller;

import com.example.desconsolidacion.Controller.DesconsolidacionController;
import com.example.desconsolidacion.Model.Desconsolidacion;
import com.example.desconsolidacion.Service.DesconsolidacionService;
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

@WebMvcTest(DesconsolidacionController.class)
public class DesconsolidacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Simulamos el servicio para probar únicamente el comportamiento del Controller
    @MockBean
    private DesconsolidacionService service;

    @Test
    public void registrar_conDatosValidos_deberiaRetornar201() throws Exception {
        // Arrange: Preparamos un objeto Desconsolidacion válido
        Desconsolidacion nuevaDesconsolidacion = new Desconsolidacion();
        nuevaDesconsolidacion.setIdDesconsolidacion(1L);
        nuevaDesconsolidacion.setNumeroFactura("FAC-9999");
        nuevaDesconsolidacion.setCantidadProductos(50); // Cantidad válida

        Mockito.when(service.registrar(Mockito.any(Desconsolidacion.class)))
               .thenReturn(nuevaDesconsolidacion);

        // Act: Simulamos la petición POST
        mockMvc.perform(post("/api/desconsolidaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevaDesconsolidacion)))
                
        // Assert: Validamos que devuelva 201 Created y los datos esperados
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idDesconsolidacion").value(1L))
                .andExpect(jsonPath("$.cantidadProductos").value(50));
    }

    @Test
    public void registrar_conCantidadCero_deberiaRetornar400() throws Exception {
        // Arrange: Preparamos un objeto simulando el error
        Desconsolidacion desconsolidacionInvalida = new Desconsolidacion();
        desconsolidacionInvalida.setNumeroFactura("FAC-9999");
        desconsolidacionInvalida.setCantidadProductos(0); // Cantidad inválida

        // Simulamos que el Service atrapa el error y lanza la RuntimeException
        Mockito.when(service.registrar(Mockito.any(Desconsolidacion.class)))
               .thenThrow(new RuntimeException("La cantidad de productos debe ser mayor a 0."));

        // Act: Simulamos la petición POST
        mockMvc.perform(post("/api/desconsolidaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(desconsolidacionInvalida)))
                
        // Assert: Validamos que el catch responda HTTP 400 Bad Request
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La cantidad de productos debe ser mayor a 0."));
    }
}