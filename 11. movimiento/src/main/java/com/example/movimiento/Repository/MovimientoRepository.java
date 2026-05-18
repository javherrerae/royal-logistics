package com.example.movimiento.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.movimiento.Model.Movimiento;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
// Usamos Movimiento Long porque idMovimiento es la clave primaria de la entidad
    // Buscar movimientos por SKU
    List<Movimiento> findBySku(String sku);

    // Buscar movimientos por ubicación
    List<Movimiento> findByIdUbicacion(String idUbicacion);

    // Buscar movimientos por tipo
    List<Movimiento> findByTipoMovimiento(String tipoMovimiento);

    // Buscar movimientos por desconsolidación
    List<Movimiento> findByIdDesconsolidacion(Long idDesconsolidacion);
}