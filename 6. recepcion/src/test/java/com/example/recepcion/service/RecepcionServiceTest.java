package com.example.recepcion.service;

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

import com.example.recepcion.Model.Recepcion;
import com.example.recepcion.Repository.RecepcionRepository;
import com.example.recepcion.Service.RecepcionService;
import com.example.recepcion.client.AndenClient;
import com.example.recepcion.client.CamionClient;

import feign.FeignException;

@ExtendWith(MockitoExtension.class)
public class RecepcionServiceTest {

    @Mock
    private RecepcionRepository repository;

    @Mock
    private CamionClient camionClient;

    @Mock
    private AndenClient andenClient;

    @InjectMocks
    private RecepcionService recepcionService;

    @Test
    public void registrar_conDatosYDependenciasValidas_deberiaGuardarRecepcion() {
        // Arrange: Preparamos la recepción
        Recepcion recepcion = new Recepcion();
        recepcion.setPatente("AB1234");
        recepcion.setNumeroAnden(5L);
        recepcion.setEstado("PENDIENTE");

        // Simulamos que el microservicio de Camiones responde un 200 OK
        Mockito.when(camionClient.buscarPorPatente("AB1234")).thenReturn(ResponseEntity.ok().build());
        
        // Simulamos que el microservicio de Andenes responde un 200 OK
        Mockito.when(andenClient.buscarPorNumero(5L)).thenReturn(ResponseEntity.ok().build());
        
        // Simulamos que la base de datos guarda y retorna la recepción
        Mockito.when(repository.save(any(Recepcion.class))).thenReturn(recepcion);

        // Act: Ejecutamos la lógica de negocio
        Recepcion resultado = recepcionService.registrar(recepcion);

        // Assert: Validamos que se creó correctamente
        assertNotNull(resultado);
        assertEquals("PENDIENTE", resultado.getEstado());

        // Verificamos que el orquestador haya llamado a los 3 componentes exactamente 1 vez
        Mockito.verify(camionClient, Mockito.times(1)).buscarPorPatente("AB1234");
        Mockito.verify(andenClient, Mockito.times(1)).buscarPorNumero(5L);
        Mockito.verify(repository, Mockito.times(1)).save(recepcion);
    }

    @Test
    public void registrar_conCamionNoRegistrado_deberiaLanzarExcepcion() {
        // Arrange: Preparamos una recepción de un camión fantasma
        Recepcion recepcion = new Recepcion();
        recepcion.setPatente("XX9999");
        recepcion.setNumeroAnden(5L);
        recepcion.setEstado("PENDIENTE");

        // Simulamos el error 404 Not Found que Feign lanza cuando no halla el recurso
        FeignException.NotFound feignNotFound = Mockito.mock(FeignException.NotFound.class);
        Mockito.when(camionClient.buscarPorPatente("XX9999")).thenThrow(feignNotFound);

        // Act & Assert: Validamos que lance la excepción exacta que programaste
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            recepcionService.registrar(recepcion);
        });

        assertEquals("Operación rechazada: La patente 'XX9999' no está registrada.", exception.getMessage());

        // Validaciones Críticas de Seguridad: 
        // Si el camión falla, JAMÁS debe validarse el andén y JAMÁS debe tocarse la base de datos
        Mockito.verify(andenClient, Mockito.never()).buscarPorNumero(any());
        Mockito.verify(repository, Mockito.never()).save(any(Recepcion.class));
    }
}