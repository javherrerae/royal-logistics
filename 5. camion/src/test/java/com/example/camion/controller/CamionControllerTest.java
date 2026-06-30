package com.example.camion.controller;

import com.example.camion.Controller.CamionController;
import com.example.camion.Model.Camion;
import com.example.camion.Service.CamionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(CamionController.class)
public class CamionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Simulamos el servicio para aislar la prueba del controlador
    @MockBean
    private CamionService camionService;

    @Test
    public void crear_conDatosValidos_deberiaRetornar201() throws Exception {
        // Arrange: Preparamos un camión simulado con TODOS sus datos obligatorios
        Camion camionNuevo = new Camion();
        camionNuevo.setPatente("AB1234");
        camionNuevo.setRutConductor("12345678-9"); // 💡 Dato obligatorio agregado
        camionNuevo.setNombreConductor("Juan Perez"); // 💡 Dato obligatorio agregado

        Mockito.when(camionService.guardar(Mockito.any(Camion.class)))
               .thenReturn(camionNuevo);

        // Act: Ejecutamos la petición POST simulada
        mockMvc.perform(post("/api/camiones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(camionNuevo)))
                
        // Assert: Validamos que retorne HTTP 201 Created y los datos coincidan
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.patente").value("AB1234"))
                .andExpect(jsonPath("$.rutConductor").value("12345678-9"))
                .andExpect(jsonPath("$.nombreConductor").value("Juan Perez"));
    }

    @Test
    public void eliminarCamion_conPatenteNoExistente_deberiaRetornar404() throws Exception {
        // Arrange: Simulamos que el servicio retorna false al intentar eliminar
        String patenteBuscada = "XX9999";
        Mockito.when(camionService.eliminarCamionPorPatente(patenteBuscada))
               .thenReturn(false);

        // Act: Ejecutamos la petición DELETE hacia el endpoint
        mockMvc.perform(delete("/api/camiones/" + patenteBuscada))
                
        // Assert: Validamos que el controlador responda HTTP 404 Not Found con el mensaje exacto
                .andExpect(status().isNotFound())
                .andExpect(content().string("No se puede eliminar: El camión con patente 'XX9999' no existe."));
    }
}