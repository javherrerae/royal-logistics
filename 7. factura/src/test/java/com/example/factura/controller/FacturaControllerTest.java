package com.example.factura.controller;

import com.example.factura.Controller.FacturaController;
import com.example.factura.Model.Factura;
import com.example.factura.Service.FacturaService;
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

@WebMvcTest(FacturaController.class)
public class FacturaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Simulamos el servicio para aislar la prueba del controlador
    @MockBean
    private FacturaService facturaService;

    @Test
    public void registrar_conDatosValidos_deberiaRetornar201() throws Exception {
        // Arrange: Preparamos una factura simulada con un estado permitido
        Factura facturaNueva = new Factura();
        facturaNueva.setNumeroFactura("FAC-1001");
        facturaNueva.setEstado("REGISTRADA");
        facturaNueva.setIdRecepcion(15L);

        // Instruimos al mock qué devolver cuando reciba cualquier Factura
        Mockito.when(facturaService.registrar(Mockito.any(Factura.class)))
               .thenReturn(facturaNueva);

        // Act: Ejecutamos la petición POST
        mockMvc.perform(post("/api/facturas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(facturaNueva)))
                
        // Assert: Validamos que devuelva 201 Created y los datos correctos
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroFactura").value("FAC-1001"))
                .andExpect(jsonPath("$.estado").value("REGISTRADA"));
    }

    @Test
    public void registrar_conEstadoNoPermitido_deberiaRetornar400() throws Exception {
        // Arrange: Preparamos una factura simulada para un caso de error
        Factura facturaInvalida = new Factura();
        facturaInvalida.setNumeroFactura("FAC-1002");
        facturaInvalida.setEstado("ESTADO_INEXISTENTE");

        // Simulamos que el Service lanza la RuntimeException programada
        Mockito.when(facturaService.registrar(Mockito.any(Factura.class)))
               .thenThrow(new RuntimeException("El estado de factura no es válido."));

        // Act: Ejecutamos la petición POST
        mockMvc.perform(post("/api/facturas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(facturaInvalida)))
                
        // Assert: Validamos que el Controller atrape la excepción y devuelva 400 Bad Request
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El estado de factura no es válido."));
    }
}