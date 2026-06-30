package com.example.factura.service;

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

import com.example.factura.Model.Factura;
import com.example.factura.Repository.FacturaRepository;
import com.example.factura.Service.FacturaService;
import com.example.factura.client.RecepcionClient;

import feign.FeignException;

@ExtendWith(MockitoExtension.class)
public class FacturaServiceTest {

    // Simulamos la base de datos
    @Mock
    private FacturaRepository repository;

    // Simulamos la conexión con el microservicio de recepciones
    @Mock
    private RecepcionClient recepcionClient;

    // Inyectamos los mocks en el servicio real
    @InjectMocks
    private FacturaService facturaService;

    @Test
    public void registrar_conRecepcionYDatosValidos_deberiaGuardarFactura() {
        // Arrange: Preparamos la factura
        Factura facturaNueva = new Factura();
        facturaNueva.setNumeroFactura("FAC-2000");
        facturaNueva.setIdRecepcion(10L);
        facturaNueva.setEstado("REGISTRADA"); // Estado permitido

        // Simulamos que el microservicio Recepción responde OK (la recepción existe)
        Mockito.when(recepcionClient.buscarPorId(10L)).thenReturn(ResponseEntity.ok().build());
        
        // Simulamos el guardado en base de datos
        Mockito.when(repository.save(any(Factura.class))).thenReturn(facturaNueva);

        // Act: Ejecutamos el registro en la lógica de negocio
        Factura resultado = facturaService.registrar(facturaNueva);

        // Assert: Validamos que la factura se retorne con éxito
        assertNotNull(resultado);
        assertEquals("FAC-2000", resultado.getNumeroFactura());

        // Comprobamos que ambos componentes externos fueron consultados exactamente 1 vez
        Mockito.verify(recepcionClient, Mockito.times(1)).buscarPorId(10L);
        Mockito.verify(repository, Mockito.times(1)).save(facturaNueva);
    }

    @Test
    public void registrar_conRecepcionInexistente_deberiaLanzarExcepcion() {
        // Arrange: Preparamos una factura que apunta a una recepción falsa
        Factura factura = new Factura();
        factura.setNumeroFactura("FAC-ERROR");
        factura.setIdRecepcion(99L);
        factura.setEstado("REGISTRADA");

        // Simulamos el error 404 que lanzaría Feign si el ID no existe en el otro microservicio
        FeignException.NotFound feignNotFound = Mockito.mock(FeignException.NotFound.class);
        Mockito.when(recepcionClient.buscarPorId(99L)).thenThrow(feignNotFound);

        // Act & Assert: Validamos que se lance la excepción correcta deteniendo el flujo
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            facturaService.registrar(factura);
        });

        assertEquals("Operación rechazada: La recepción con ID 99 no existe.", exception.getMessage());

        // Regla de oro: Si la recepción falló, JAMÁS debe intentarse guardar en base de datos
        Mockito.verify(repository, Mockito.never()).save(any(Factura.class));
    }
}