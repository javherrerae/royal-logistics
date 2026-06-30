package com.example.camion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.camion.Model.Camion;
import com.example.camion.Repository.CamionRepository;
import com.example.camion.Service.CamionService;

@ExtendWith(MockitoExtension.class)
public class CamionServiceTest {

    // Simulamos la conexión a la base de datos MySQL
    @Mock
    private CamionRepository camionRepository;

    // Inyectamos el mock dentro del servicio a testear
    @InjectMocks
    private CamionService camionService;

    @Test
    public void guardar_conCamionValido_deberiaRetornarCamionGuardado() {
        // Arrange: Preparamos un camión simulado y configuramos el repositorio
        Camion camionNuevo = new Camion();
        camionNuevo.setPatente("AB1234");
        camionNuevo.setRutConductor("12345678-9");
        camionNuevo.setNombreConductor("Juan Perez");

        Mockito.when(camionRepository.save(any(Camion.class))).thenReturn(camionNuevo);

        // Act: Ejecutamos el método guardar en el servicio
        Camion resultado = camionService.guardar(camionNuevo);

        // Assert: Verificamos que no sea nulo y que la patente coincida
        assertNotNull(resultado);
        assertEquals("AB1234", resultado.getPatente());

        // Comprobamos que el servicio haya llamado al repositorio exactamente 1 vez para guardar
        Mockito.verify(camionRepository, Mockito.times(1)).save(camionNuevo);
    }

    @Test
    public void eliminarCamionPorPatente_cuandoNoExiste_deberiaRetornarFalse() {
        // Arrange: Simulamos que el repositorio informa que la patente no existe
        String patenteBuscada = "XX9999";
        Mockito.when(camionRepository.existsById(patenteBuscada)).thenReturn(false);

        // Act: Ejecutamos el método de eliminación en el servicio
        boolean resultado = camionService.eliminarCamionPorPatente(patenteBuscada);

        // Assert: Verificamos que el servicio haya retornado false como medida de seguridad
        assertFalse(resultado);
        
        // Verificamos que el repositorio haya hecho la consulta de existencia 1 vez
        Mockito.verify(camionRepository, Mockito.times(1)).existsById(patenteBuscada);
        // Validamos la regla de negocio más importante: que JAMÁS se haya intentado ejecutar un delete
        Mockito.verify(camionRepository, Mockito.never()).deleteById(any());
    }
}