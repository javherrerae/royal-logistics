package com.example.desconsolidacion.service;

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

import com.example.desconsolidacion.Model.Desconsolidacion;
import com.example.desconsolidacion.Repository.DesconsolidacionRepository;
import com.example.desconsolidacion.Service.DesconsolidacionService;
import com.example.desconsolidacion.client.FacturaClient;

import feign.FeignException;

@ExtendWith(MockitoExtension.class)
public class DesconsolidacionServiceTest {

    // Simulamos la conexión a la base de datos
    @Mock
    private DesconsolidacionRepository repository;

    // Simulamos el cliente Feign para no hacer llamadas reales a otros microservicios
    @Mock
    private FacturaClient facturaClient;

    // Inyectamos los mocks en la clase que realmente estamos probando
    @InjectMocks
    private DesconsolidacionService desconsolidacionService;

    @Test
    public void registrar_conFacturaYCantidadValida_deberiaGuardar() {
        // Arrange: Preparamos los datos
        Desconsolidacion nuevaDesc = new Desconsolidacion();
        nuevaDesc.setNumeroFactura("FAC-1234");
        nuevaDesc.setCantidadProductos(50); // Validamos que sea mayor a 0

        // Simulamos que el microservicio de Facturas responde OK
        Mockito.when(facturaClient.buscarPorNumeroFactura("FAC-1234"))
               .thenReturn(ResponseEntity.ok().build());
        
        // Simulamos que la base de datos guarda y retorna la entidad
        Mockito.when(repository.save(any(Desconsolidacion.class)))
               .thenReturn(nuevaDesc);

        // Act: Ejecutamos la lógica de negocio
        Desconsolidacion resultado = desconsolidacionService.registrar(nuevaDesc);

        // Assert: Validamos que se guardó correctamente
        assertNotNull(resultado);
        assertEquals(50, resultado.getCantidadProductos());

        // Verificamos la comunicación: 1 llamada a Feign, 1 llamada a BD
        Mockito.verify(facturaClient, Mockito.times(1)).buscarPorNumeroFactura("FAC-1234");
        Mockito.verify(repository, Mockito.times(1)).save(nuevaDesc);
    }

    @Test
    public void registrar_conFacturaNoRegistrada_deberiaLanzarExcepcion() {
        // Arrange: Preparamos datos con una factura fantasma
        Desconsolidacion descInvalida = new Desconsolidacion();
        descInvalida.setNumeroFactura("FAC-ERROR");
        descInvalida.setCantidadProductos(50);

        // Simulamos que Feign arroja un error 404 al buscar la factura
        FeignException.NotFound feignNotFound = Mockito.mock(FeignException.NotFound.class);
        Mockito.when(facturaClient.buscarPorNumeroFactura("FAC-ERROR")).thenThrow(feignNotFound);

        // Act & Assert: Validamos que lance tu excepción de negocio exacta
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            desconsolidacionService.registrar(descInvalida);
        });

        assertEquals("Operación rechazada: El número de factura 'FAC-ERROR' no está registrado.", exception.getMessage());

        // Seguridad: Garantizamos que si falla Feign, el repositorio JAMÁS se toca
        Mockito.verify(repository, Mockito.never()).save(any(Desconsolidacion.class));
    }
}