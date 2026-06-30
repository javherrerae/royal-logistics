package com.example.anden.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.anden.Model.Anden;
import com.example.anden.Repository.AndenRepository;
import com.example.anden.Service.AndenService;

@ExtendWith(MockitoExtension.class)
public class AndenServiceTest {

    // Simulamos la conexión a la base de datos
    @Mock
    private AndenRepository andenRepository;

    // Inyectamos el repositorio simulado dentro del servicio real
    @InjectMocks
    private AndenService andenService;

    @Test
    public void guardar_conAndenValido_deberiaRetornarAndenGuardado() {
        // Arrange: Preparamos un andén y configuramos el comportamiento del repositorio
        Anden andenNuevo = new Anden();
        andenNuevo.setNumeroanden(5L);
        andenNuevo.setEstado("DISPONIBLE");

        Mockito.when(andenRepository.save(any(Anden.class))).thenReturn(andenNuevo);

        // Act: Ejecutamos el método guardar del servicio
        Anden resultado = andenService.guardar(andenNuevo);

        // Assert: Verificamos que no sea nulo y los datos coincidan
        assertNotNull(resultado);
        assertEquals(5L, resultado.getNumeroanden());
        assertEquals("DISPONIBLE", resultado.getEstado());

        // Comprobamos que el repositorio fue llamado exactamente 1 vez para guardar
        Mockito.verify(andenRepository, Mockito.times(1)).save(andenNuevo);
    }

    @Test
    public void buscarPorNumero_cuandoAndenNoExiste_deberiaRetornarNull() {
        // Arrange: Simulamos que la base de datos no encuentra el andén 99
        Long numeroBuscado = 99L;
        Mockito.when(andenRepository.findByNumeroanden(numeroBuscado)).thenReturn(null);

        // Act: Ejecutamos la búsqueda en el servicio
        Anden resultado = andenService.buscarPorNumero(numeroBuscado);

        // Assert: Verificamos que el servicio retorne null de forma controlada
        assertNull(resultado);
        
        // Comprobamos que el repositorio sí intentó hacer la búsqueda 1 vez
        Mockito.verify(andenRepository, Mockito.times(1)).findByNumeroanden(numeroBuscado);
    }
}