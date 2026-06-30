package logistica.stock.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import feign.FeignException;
import logistica.stock.client.ProductClient;
import logistica.stock.model.Stock;
import logistica.stock.repository.StockRepository;

@ExtendWith(MockitoExtension.class)
public class StockServiceServTest {

    // Simulamos la conexión a BD
    @Mock
    private StockRepository stockRepository;

    // Simulamos la comunicación con el microservicio Producto
    @Mock
    private ProductClient productoClient;

    // Inyectamos los mocks en el servicio
    @InjectMocks
    private StockServiceServ stockServiceServ;

    @Test
    public void registrarOActualizar_conStockExistente_deberiaSumarCantidades() {
        // Arrange: Preparamos el stock que nos envían por la API
        Stock stockEntrante = new Stock();
        stockEntrante.setSku("SKU-ACTIVO");
        stockEntrante.setIdUbicacion("P1-R1-N1");
        stockEntrante.setCantDisponibles(20);

        // Arrange: Preparamos el stock que supuestamente ya está guardado en la base de datos
        Stock stockEnBaseDeDatos = new Stock();
        stockEnBaseDeDatos.setSku("SKU-ACTIVO");
        stockEnBaseDeDatos.setIdUbicacion("P1-R1-N1");
        stockEnBaseDeDatos.setCantDisponibles(30);

        // Simulamos que el producto SÍ existe en el microservicio de Productos
        Mockito.when(productoClient.buscarPorSku("SKU-ACTIVO"))
               .thenReturn(ResponseEntity.ok().build());

        // Simulamos que al buscar en BD, encontramos el registro anterior
        Mockito.when(stockRepository.findBySkuAndIdUbicacion("SKU-ACTIVO", "P1-R1-N1"))
               .thenReturn(stockEnBaseDeDatos);

        // Simulamos el guardado
        Mockito.when(stockRepository.save(any(Stock.class))).thenReturn(stockEnBaseDeDatos);

        // Act: Ejecutamos el servicio
        Stock resultado = stockServiceServ.registrarOActualizar(stockEntrante);

        // Assert: Validamos la lógica matemática (30 en BD + 20 entrantes = 50 totales)
        assertNotNull(resultado);
        assertEquals(50, resultado.getCantDisponibles());

        // Verificamos que se ejecutó una consulta y un guardado
        Mockito.verify(stockRepository, Mockito.times(1)).findBySkuAndIdUbicacion("SKU-ACTIVO", "P1-R1-N1");
        Mockito.verify(stockRepository, Mockito.times(1)).save(stockEnBaseDeDatos);
    }

    @Test
    public void registrarOActualizar_conSkuNoEncontradoEnFeign_deberiaLanzarExcepcion() {
        // Arrange: Preparamos un ingreso para un producto fantasma
        Stock stockInvalido = new Stock();
        stockInvalido.setSku("SKU-FANTASMA");
        stockInvalido.setIdUbicacion("P1-R1-N1");
        stockInvalido.setCantDisponibles(10);

        // Simulamos que Feign lanza una excepción NotFound y le configuramos el status() en 404
        // tal como lo espera la validación de tu servicio.
        FeignException.NotFound feignNotFound = Mockito.mock(FeignException.NotFound.class);
        Mockito.when(feignNotFound.status()).thenReturn(404);
        
        Mockito.when(productoClient.buscarPorSku("SKU-FANTASMA")).thenThrow(feignNotFound);

        // Act & Assert: Validamos que la excepción esperada sea lanzada con el texto exacto
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            stockServiceServ.registrarOActualizar(stockInvalido);
        });

        assertEquals("El SKU 'SKU-FANTASMA' no existe en el catálogo de productos.", exception.getMessage());

        // Regla de Oro: Si falla Feign, JAMÁS debemos tocar el repositorio
        Mockito.verify(stockRepository, Mockito.never()).findBySkuAndIdUbicacion(any(), any());
        Mockito.verify(stockRepository, Mockito.never()).save(any(Stock.class));
    }
}