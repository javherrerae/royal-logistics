package logistica.producto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import logistica.producto.client.StockFeignClient;
import logistica.producto.dto.StockDTO;
import logistica.producto.model.Producto;
import logistica.producto.repository.ProductoRepository;
import logistica.producto.client.DesconsolidacionClient;


@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repository;

    // Conexión con desconsolidación
    @Autowired
    private DesconsolidacionClient desconsolidacionClient;

    // Conexión con Stock
    @Autowired
    private StockFeignClient stockClient;


    // Listamos todos los productos
    public List<Producto> listarTodos() {
        return repository.findAll();
    }

    // Registramos un producto
    @Transactional
    public Producto registrar(Producto producto) {
        // Verificar duplicados locales de SKU
        boolean productoExiste = repository.findById(producto.getSku()).isPresent();
        if (productoExiste) {
            throw new RuntimeException("El producto ya existe previamente.");
        }

        // Coherencia de fechas logísticas
        if (producto.getFecCaducidad() != null &&
                producto.getFecCaducidad().isBefore(producto.getFecFabricacion())) {
            throw new RuntimeException("La fecha de caducidad no puede ser anterior a la fecha de fabricación.");
        }

        // Validar si la desconsolidación de origen existe en su microservicio
        try {
            desconsolidacionClient.buscarPorId(producto.getIdDesconsolidacion());
        } catch (feign.FeignException.NotFound e) {
            throw new RuntimeException("Operación rechazada: La desconsolidación con ID " + producto.getIdDesconsolidacion() + " no existe.");
        } catch (Exception e) {
            throw new RuntimeException("Error de comunicación inter-servicio con Desconsolidación.");
        }

        // Si la desconsolidación existe, guardamos el producto localmente
        Producto productoGuardado = repository.save(producto);

        // Inicializar de forma segura su stock correspondiente en stock
        try {
            StockDTO nuevoStock = new StockDTO(
                productoGuardado.getSku(), 
                "POR_ASIGNAR", 
                0
            );
            stockClient.inicializarStock(nuevoStock); 
        } catch (Exception e) {
            throw new RuntimeException("Producto validado y creado, pero falló la inicialización del stock: " + e.getMessage());
        }

        return productoGuardado;
    }

    // Buscamos producto por SKU
    public Producto buscarPorSku(String sku) {
        return repository.findBySku(sku);
    }

    // Buscamos productos por categoría
    public List<Producto> buscarPorCategoria(String categoria) {
        return repository.findByCategoria(categoria);
    }

    // Buscamos productos por desconsolidación
    public List<Producto> buscarPorDesconsolidacion(Long idDesconsolidacion) {
        return repository.findByIdDesconsolidacion(idDesconsolidacion);
    }

    // Buscamos productos por nombre
    public List<Producto> buscarPorNombre(String nombreProducto) {
        return repository.findByNombreProducto(nombreProducto);
    }

    // Eliminamos producto
    @Transactional
    public void eliminar(String sku) {
        repository.deleteById(sku);
    }
}