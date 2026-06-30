package com.example.movimiento.service;

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

import com.example.movimiento.Client.DesconsolidacionClient;
import com.example.movimiento.Client.ProductoClient;
import com.example.movimiento.Client.WarehouseClient;
import com.example.movimiento.Model.Movimiento;
import com.example.movimiento.Repository.MovimientoRepository;
import com.example.movimiento.Service.MovimientoService;

@ExtendWith(MockitoExtension.class)
public class MovimientoServiceTest {

    // Simulamos la base de datos
    @Mock
    private MovimientoRepository repository;

    // Simulamos los 3 microservicios externos
    @Mock
    private ProductoClient productoClient;

    @Mock
    private WarehouseClient warehouseClient;

    @Mock
    private DesconsolidacionClient desconsolidacionClient;

    // Inyectamos todos los mocks en el servicio orquestador
    @InjectMocks
    private MovimientoService movimientoService;

    @Test
    public void registrar_conDatosYDependenciasValidas_deberiaGuardar() {
        // Arrange: Preparamos un movimiento perfecto
        Movimiento movNuevo = new Movimiento();
        movNuevo.setSku("SKU-100");
        movNuevo.setIdUbicacion("P1-R1-N1");
        movNuevo.setIdDesconsolidacion(10L);
        movNuevo.setCantidad(50);
        movNuevo.setTipoMovimiento("INGRESO");

        // Simulamos que ProductoClient responde OK (retornando un Object cualquiera)
        Mockito.when(productoClient.buscarProducto("SKU-100")).thenReturn(new Object());
        
        // Simulamos que WarehouseClient responde OK
        Mockito.when(warehouseClient.buscarUbicacion("P1-R1-N1")).thenReturn(new Object());
        
        // Simulamos que DesconsolidacionClient responde OK
        Mockito.when(desconsolidacionClient.buscarDesconsolidacion(10L)).thenReturn(new Object());
        
        // Simulamos el guardado en base de datos
        Mockito.when(repository.save(any(Movimiento.class))).thenReturn(movNuevo);

        // Act: Ejecutamos el servicio
        Movimiento resultado = movimientoService.registrar(movNuevo);

        // Assert: Validamos que se completó el registro
        assertNotNull(resultado);
        assertEquals("INGRESO", resultado.getTipoMovimiento());

        // Verificamos que se llamó exactamente 1 vez a cada componente del ecosistema
        Mockito.verify(productoClient, Mockito.times(1)).buscarProducto("SKU-100");
        Mockito.verify(warehouseClient, Mockito.times(1)).buscarUbicacion("P1-R1-N1");
        Mockito.verify(desconsolidacionClient, Mockito.times(1)).buscarDesconsolidacion(10L);
        Mockito.verify(repository, Mockito.times(1)).save(movNuevo);
    }

    @Test
    public void registrar_conProductoInexistente_deberiaLanzarExcepcionYAbortar() {
        // Arrange: Preparamos un movimiento con un SKU que no existe
        Movimiento movInvalido = new Movimiento();
        movInvalido.setSku("SKU-FANTASMA");
        movInvalido.setIdUbicacion("P1-R1-N1");
        movInvalido.setIdDesconsolidacion(10L);
        movInvalido.setCantidad(50);
        movInvalido.setTipoMovimiento("INGRESO");

        // Simulamos que el microservicio de Productos arroja un error (ej. un 404 de Feign)
        Mockito.when(productoClient.buscarProducto("SKU-FANTASMA"))
               .thenThrow(new RuntimeException("Feign 404 Not Found"));

        // Act & Assert: Validamos que lance la excepción específica de tu lógica de negocio
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            movimientoService.registrar(movInvalido);
        });

        assertEquals("No existe un producto con ese SKU.", exception.getMessage());

        // REGLAS CRÍTICAS DE SEGURIDAD (FAIL-FAST):
        // Como falló el SKU, jamás debió consultar la ubicación, ni la desconsolidación, ni la BD.
        Mockito.verify(warehouseClient, Mockito.never()).buscarUbicacion(any());
        Mockito.verify(desconsolidacionClient, Mockito.never()).buscarDesconsolidacion(any());
        Mockito.verify(repository, Mockito.never()).save(any(Movimiento.class));
    }
}