package com.example.recepcion.controller;

import com.example.recepcion.Controller.RecepcionController;
import com.example.recepcion.Model.Recepcion;
import com.example.recepcion.Service.RecepcionService;
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

@WebMvcTest(RecepcionController.class)
public class RecepcionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Simulamos el servicio para aislar el controlador
    @MockBean
    private RecepcionService recepcionService;

    @Test
    public void registrar_conDatosValidos_deberiaRetornar201() throws Exception {
        // Arrange: Preparamos un objeto Recepcion válido
        Recepcion recepcionNueva = new Recepcion();
        recepcionNueva.setIdRecepcion(1L);
        recepcionNueva.setPatente("AB1234");
        recepcionNueva.setNumeroAnden(2L);
        recepcionNueva.setEstado("PENDIENTE");

        // Le indicamos al mock del servicio qué debe devolver al ejecutarse
        Mockito.when(recepcionService.registrar(Mockito.any(Recepcion.class)))
               .thenReturn(recepcionNueva);

        // Act: Simulamos la petición POST
        mockMvc.perform(post("/api/recepciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recepcionNueva)))
                
        // Assert: Validamos el código HTTP y el JSON de respuesta
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idRecepcion").value(1L))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));
    }

    @Test
    public void registrar_conEstadoInvalido_deberiaRetornar400() throws Exception {
        // Arrange: Preparamos un objeto y simulamos que el servicio rechaza la operación
        Recepcion recepcionInvalida = new Recepcion();
        recepcionInvalida.setEstado("ESTADO_INVENTADO");

        // Simulamos la excepción que tienes programada en RecepcionService
        Mockito.when(recepcionService.registrar(Mockito.any(Recepcion.class)))
               .thenThrow(new RuntimeException("El estado de recepción no es válido."));

        // Act: Simulamos la petición POST
        mockMvc.perform(post("/api/recepciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recepcionInvalida)))
                
        // Assert: Validamos que el bloque catch del Controller capture el error y responda HTTP 400
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El estado de recepción no es válido."));
    }
}