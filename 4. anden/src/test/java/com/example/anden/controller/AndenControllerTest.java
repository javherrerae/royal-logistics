package com.example.anden.controller;

import com.example.anden.Controller.AndenController;
import com.example.anden.Model.Anden;
import com.example.anden.Service.AndenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(AndenController.class)
public class AndenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Simulamos el servicio para probar solo el Controller
    @MockBean
    private AndenService andenService;

    @Test
    public void crear_conDatosValidos_deberiaRetornar201() throws Exception {
        // Arrange: Preparamos un andén simulado cumpliendo las validaciones (@Valid)
        Anden andenNuevo = new Anden();
        andenNuevo.setNumeroanden(12L);
        andenNuevo.setEstado("DISPONIBLE"); // 💡 ¡AGREGAMOS ESTA LÍNEA!
        
        Mockito.when(andenService.guardar(Mockito.any(Anden.class)))
               .thenReturn(andenNuevo);

        // Act: Ejecutamos una petición POST simulada hacia el endpoint
        mockMvc.perform(post("/api/andenes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(andenNuevo)))
                
        // Assert: Esperamos que responda HTTP 201 Created
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroanden").value(12L))
                // También podemos validar que el JSON devuelva el estado correcto
                .andExpect(jsonPath("$.estado").value("DISPONIBLE")); 
    }

    @Test
    public void buscarPorNumero_conAndenNoExistente_deberiaRetornar404() throws Exception {
        // Arrange: Simulamos que el service retorna null porque el andén no existe
        Long numeroBuscado = 99L;
        Mockito.when(andenService.buscarPorNumero(numeroBuscado)).thenReturn(null);

        // Act: Ejecutamos la petición GET hacia el endpoint con el número inexistente
        mockMvc.perform(get("/api/andenes/" + numeroBuscado))
                
        // Assert: Esperamos que el controller maneje el null y responda HTTP 404 Not Found
                .andExpect(status().isNotFound())
                .andExpect(content().string("El andén número 99 no existe en el sistema."));
    }
}