package com.example.warehouse.service;

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

import com.example.warehouse.Model.Warehouse;
import com.example.warehouse.Repository.WarehouseRepository;
import com.example.warehouse.Service.WarehouseService;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTest {

    // Simulamos la conexión a la base de datos para no afectar datos reales
    @Mock
    private WarehouseRepository repository;

    // Inyectamos el mock en el servicio que estamos testeando
    @InjectMocks
    private WarehouseService warehouseService;

    @Test
    public void registrar_conNivelValido_deberiaGuardar() {
        // Arrange: Preparamos un objeto con un nivel lógico y real
        Warehouse nuevaUbicacion = new Warehouse();
        nuevaUbicacion.setIdUbicacion("P1-R1-N2");
        nuevaUbicacion.setPasillo("P1");
        nuevaUbicacion.setRack("R1");
        nuevaUbicacion.setNivel(2); // Nivel válido (mayor a 0)

        // Simulamos el comportamiento del repositorio al guardar
        Mockito.when(repository.save(any(Warehouse.class))).thenReturn(nuevaUbicacion);

        // Act: Ejecutamos el servicio
        Warehouse resultado = warehouseService.registrar(nuevaUbicacion);

        // Assert: Validamos que retorne los datos correctos
        assertNotNull(resultado);
        assertEquals(2, resultado.getNivel());
        
        // Verificamos que se haya ejecutado el insert en base de datos exactamente 1 vez
        Mockito.verify(repository, Mockito.times(1)).save(nuevaUbicacion);
    }

    @Test
    public void registrar_conNivelInvalido_deberiaLanzarExcepcion() {
        // Arrange: Preparamos una ubicación físicamente imposible
        Warehouse ubicacionInvalida = new Warehouse();
        ubicacionInvalida.setIdUbicacion("P1-R1-N0");
        ubicacionInvalida.setNivel(0); // Nivel inválido según tu regla de negocio

        // Act & Assert: Comprobamos que lance exactamente tu excepción de negocio
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            warehouseService.registrar(ubicacionInvalida);
        });

        assertEquals("El nivel debe ser mayor a 0.", exception.getMessage());

        // Regla de Integridad: Si la validación falla, JAMÁS se debe intentar tocar la base de datos
        Mockito.verify(repository, Mockito.never()).save(any(Warehouse.class));
    }
}