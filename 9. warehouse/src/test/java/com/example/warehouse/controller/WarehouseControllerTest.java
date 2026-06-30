package com.example.warehouse.controller;

import com.example.warehouse.Controller.WarehouseController;
import com.example.warehouse.Model.Warehouse;
import com.example.warehouse.Service.WarehouseService;
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

@WebMvcTest(WarehouseController.class)
public class WarehouseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Simulamos el servicio para probar la capa de red de forma aislada
    @MockBean
    private WarehouseService service;

    @Test
    public void registrar_conDatosValidos_deberiaRetornar201() throws Exception {
        // Arrange: Preparamos un objeto Warehouse válido
        Warehouse nuevaUbicacion = new Warehouse();
        nuevaUbicacion.setIdUbicacion("A1-R2-N3");
        nuevaUbicacion.setPasillo("A1");
        nuevaUbicacion.setRack("R2");
        nuevaUbicacion.setNivel(3); // Nivel mayor a 0

        Mockito.when(service.registrar(Mockito.any(Warehouse.class)))
               .thenReturn(nuevaUbicacion);

        // Act: Simulamos la petición POST
        mockMvc.perform(post("/api/warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevaUbicacion)))
                
        // Assert: Validamos que devuelva 201 Created y mapee correctamente el JSON
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idUbicacion").value("A1-R2-N3"))
                .andExpect(jsonPath("$.nivel").value(3));
    }

    @Test
    public void registrar_conNivelInvalido_deberiaRetornar400() throws Exception {
        // Arrange: Preparamos un objeto que viola la regla de negocio
        Warehouse ubicacionInvalida = new Warehouse();
        ubicacionInvalida.setIdUbicacion("A1-R2-N0");
        ubicacionInvalida.setNivel(0); // Nivel inválido

        // Simulamos la excepción que lanza tu WarehouseService
        Mockito.when(service.registrar(Mockito.any(Warehouse.class)))
               .thenThrow(new RuntimeException("El nivel debe ser mayor a 0."));

        // Act: Simulamos la petición POST
        mockMvc.perform(post("/api/warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ubicacionInvalida)))
                
        // Assert: Validamos que el Controller capture el error y retorne 400 Bad Request
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El nivel debe ser mayor a 0."));
    }
}