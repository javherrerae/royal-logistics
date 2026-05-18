package com.example.movimiento.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movimiento.Client.DesconsolidacionClient;
import com.example.movimiento.Client.ProductoClient;
import com.example.movimiento.Client.WarehouseClient;
import com.example.movimiento.Model.Movimiento;
import com.example.movimiento.Repository.MovimientoRepository;

import jakarta.transaction.Transactional;


// El service es nuestra capa de nuestra lógica de negocio, por ende acá hacemos todas las validaciones.
@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository repository;

    @Autowired
    private ProductoClient productoClient;

    @Autowired
    private WarehouseClient warehouseClient;

    @Autowired
    private DesconsolidacionClient desconsolidacionClient;

    // Listamos todos los movimientos
    public List<Movimiento> listarTodos() {
        return repository.findAll();
    }

    // Registramos un movimiento
    @Transactional
    public Movimiento registrar(Movimiento movimiento) {

        // Validamos que el SKU exista
        try {
            productoClient.buscarProducto(
                    movimiento.getSku());
        } catch (Exception e) {
            throw new RuntimeException(
                    "No existe un producto con ese SKU.");
        }

        // Validamos que la ubicación exista
        try {
            warehouseClient.buscarUbicacion(
                    movimiento.getIdUbicacion());
        } catch (Exception e) {
            throw new RuntimeException(
                    "No existe una ubicación con ese ID.");
        }

        // Validamos que la desconsolidación exista
        try {
            desconsolidacionClient.buscarDesconsolidacion(
                    movimiento.getIdDesconsolidacion());
        } catch (Exception e) {
            throw new RuntimeException(
                    "No existe una desconsolidación con ese ID.");
        }

        // La cantidad del movimiento debe ser mayor a 0, porque no puedo agregar 0 productos (0 celulares) a una ubicación
        if (movimiento.getCantidad() <= 0) {
            throw new RuntimeException(
                    "La cantidad debe ser mayor a 0.");
        }

        // Solo se permiten tipos de movimiento de ingreso
        if (
            !movimiento.getTipoMovimiento()
            .equalsIgnoreCase("INGRESO")
        ) {
            throw new RuntimeException(
                    "El tipo de movimiento no es válido.");
        }

        return repository.save(movimiento);
    }

    // Buscamos movimientos por SKU
    public List<Movimiento> buscarPorSku(String sku) {
        return repository.findBySku(sku);
    }

    // Buscamos movimientos por ubicación
    public List<Movimiento> buscarPorUbicacion(
            String idUbicacion) {
        return repository.findByIdUbicacion(
                idUbicacion);
    }

    // Buscamos movimientos por tipo de movimiento
    public List<Movimiento> buscarPorTipoMovimiento(
            String tipoMovimiento) {
        return repository.findByTipoMovimiento(
                tipoMovimiento);
    }

    // Buscamos movimientos por desconsolidación
    public List<Movimiento> buscarPorDesconsolidacion(
            Long idDesconsolidacion) {
        return repository.findByIdDesconsolidacion(
                idDesconsolidacion);
    }

    // Eliminamos un movimiento
    @Transactional
    public void eliminar(Long idMovimiento) {
        repository.deleteById(idMovimiento);
    }
}