package logistica.stock.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import logistica.stock.model.Stock;
import logistica.stock.service.StockServiceServ;
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

@WebMvcTest(StockController.class)
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Simulamos la capa de servicio para aislar la prueba en el Controller
    @MockBean
    private StockServiceServ service;

    @Test
    public void registrarOActualizar_conDatosValidos_deberiaRetornar201() throws Exception {
        // Arrange: Preparamos un objeto de Stock
        Stock nuevoStock = new Stock();
        // Nota: Asumiendo que tu entidad Stock tiene un campo ID autogenerado, no lo seteamos aquí
        nuevoStock.setSku("SKU-999");
        nuevoStock.setIdUbicacion("A1-R1-N1");
        nuevoStock.setCantDisponibles(50); // Cantidad positiva

        // Le indicamos al mock qué debe retornar
        Mockito.when(service.registrarOActualizar(Mockito.any(Stock.class)))
               .thenReturn(nuevoStock);

        // Act: Simulamos la petición POST a la ruta raíz del controller
        mockMvc.perform(post("/api/stock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoStock)))
                
        // Assert: Validamos que devuelva 201 Created y los valores coincidan
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sku").value("SKU-999"))
                .andExpect(jsonPath("$.idUbicacion").value("A1-R1-N1"))
                .andExpect(jsonPath("$.cantDisponibles").value(50));
    }

    @Test
    public void registrarOActualizar_conCantidadNegativa_deberiaRetornar400() throws Exception {
        // Arrange: Preparamos un objeto con cantidad inválida para simular el fallo
        Stock stockInvalido = new Stock();
        stockInvalido.setSku("SKU-999");
        stockInvalido.setCantDisponibles(-10); // Cantidad negativa

        // Simulamos que el Service atrapa el error y lanza la excepción programada
        Mockito.when(service.registrarOActualizar(Mockito.any(Stock.class)))
               .thenThrow(new RuntimeException("La cantidad inicial de stock no puede ser un número negativo."));

        // Act: Simulamos la petición POST
        mockMvc.perform(post("/api/stock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stockInvalido)))
                
        // Assert: Validamos que el catch() del Controller devuelva 400 Bad Request
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La cantidad inicial de stock no puede ser un número negativo."));
    }
}