package logistica.producto.service;

import java.time.LocalDate;
import java.util.Optional;

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

import logistica.producto.client.DesconsolidacionClient;
import logistica.producto.client.StockFeignClient;
import logistica.producto.dto.StockDTO;
import logistica.producto.model.Producto;
import logistica.producto.repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository repository;

    @Mock
    private DesconsolidacionClient desconsolidacionClient;

    @Mock
    private StockFeignClient stockClient;

    @InjectMocks
    private ProductoService productoService;

    @Test
    public void registrar_conDatosValidos_deberiaGuardarYRetornarProducto() {
        // Arrange: Preparamos un producto válido
        Producto productoNuevo = new Producto();
        productoNuevo.setSku("SKU-999");
        productoNuevo.setFecFabricacion(LocalDate.now().minusDays(5));
        productoNuevo.setFecCaducidad(LocalDate.now().plusDays(10)); // Fecha coherente
        productoNuevo.setIdDesconsolidacion(1L);

        // Simulamos que el repositorio NO encuentra el SKU (es decir, es nuevo)
        Mockito.when(repository.findById("SKU-999")).thenReturn(Optional.empty());
        // Simulamos que al guardar en base de datos, retorna el mismo producto
        Mockito.when(repository.save(any(Producto.class))).thenReturn(productoNuevo);
        // DesconsolidacionClient no retorna nada crítico aquí, solo necesitamos que no lance excepción.

        // Act: Ejecutamos el método de negocio
        Producto resultado = productoService.registrar(productoNuevo);

        // Assert: Verificamos que el resultado no sea nulo y que sea el mismo SKU
        assertNotNull(resultado);
        assertEquals("SKU-999", resultado.getSku());
        
        // Verificamos que el Service haya llamado exactamente 1 vez al repositorio para guardar
        Mockito.verify(repository, Mockito.times(1)).save(productoNuevo);
        // Verificamos que se haya llamado al Feign Client para inicializar el stock
        Mockito.verify(stockClient, Mockito.times(1)).inicializarStock(any(StockDTO.class));
    }

    @Test
    public void registrar_conSkuDuplicado_deberiaLanzarExcepcion() {
        // Arrange: Preparamos un producto y simulamos que YA EXISTE en la base de datos
        Producto productoDuplicado = new Producto();
        productoDuplicado.setSku("SKU-EXISTENTE");

        Mockito.when(repository.findById("SKU-EXISTENTE")).thenReturn(Optional.of(productoDuplicado));

        // Act & Assert: Ejecutamos y esperamos que lance la excepción exacta que programaste
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productoService.registrar(productoDuplicado);
        });

        // Verificamos que el mensaje de error sea el correcto
        assertEquals("El producto ya existe previamente.", exception.getMessage());
        
        // Comprobamos que, al fallar, JAMÁS se haya intentado guardar en BD ni llamar a Stock
        Mockito.verify(repository, Mockito.never()).save(any(Producto.class));
        Mockito.verify(stockClient, Mockito.never()).inicializarStock(any());
    }
}