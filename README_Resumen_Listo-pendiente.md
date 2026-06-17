# Diagnóstico del Proyecto de Microservicios - Royal Logistics

## 1. Resumen ejecutivo

Este documento presenta una revisión técnica del proyecto de microservicios desarrollado para **Royal Logistics**, contrastando el estado actual del proyecto con las directrices de la Unidad III, la pauta de evaluación, los apuntes de clase y los criterios esperados para una arquitectura basada en microservicios.

El proyecto cuenta con una base técnica defendible: posee estructura multi-módulo Maven, servidor Eureka, API Gateway, múltiples microservicios, comunicación entre servicios mediante Feign Client, configuración Swagger/OpenAPI base y separación general en capas Controller, Service y Repository. Además, se identifican reglas de negocio distribuidas en distintos servicios, principalmente relacionadas con productos, stock, recepción, facturación, desconsolidación, warehouse y movimientos.

Sin embargo, existen brechas importantes que deben ser abordadas antes de una entrega o defensa final. Las principales brechas detectadas corresponden a pruebas unitarias reales, uso de archivos YAML, documentación técnica completa en Swagger, despliegue local/remoto documentado, README general del proyecto y corrección de inconsistencias entre la documentación y la configuración real de los servicios.

En términos generales, el proyecto **sí cumple parcialmente con la arquitectura esperada**, pero todavía requiere ajustes para alcanzar un nivel sólido según la pauta, especialmente en los indicadores asociados a testing, documentación, despliegue y configuración.

---

## 2. Estado general del proyecto

| Área evaluada | Estado | Comentario |
|---|---:|---|
| Proyecto multi-módulo Maven | Listo | Existe un `pom.xml` padre con módulos para Eureka, microservicios y API Gateway. |
| Cantidad mínima de microservicios | Listo | Se identifican más de 10 microservicios. |
| Eureka Server | Listo | Existe servidor Eureka configurado en puerto `8761`. |
| Registro de servicios en Eureka | Listo/parcial | Los servicios tienen configuración para registrarse en Eureka. |
| API Gateway | Listo/parcial | Existe Gateway en puerto `8080` con rutas hacia los servicios. |
| Comunicación REST entre microservicios | Listo/parcial | Hay comunicación con Feign Client en varios módulos. |
| Patrón Controller-Service-Repository | Mayormente listo | La mayoría de los módulos respeta la separación por capas. |
| Swagger/OpenAPI | Parcial | Está instalado, pero falta documentación detallada mediante anotaciones. |
| Pruebas unitarias | Pendiente crítico | Solo existen pruebas `contextLoads()`, sin pruebas unitarias reales. |
| YAML | Pendiente crítico | La configuración está en `.properties`, no en `.yml` o `.yaml`. |
| Despliegue Docker/Render/Railway | No evidenciado | No se encontraron archivos de despliegue. |
| README general | Pendiente | Solo se encontró documentación específica del Gateway. |
| Autenticación | Incompleto | El microservicio existe, pero no expone endpoints funcionales visibles. |

---

## 3. Elementos que están listos

### 3.1 Proyecto multi-módulo Maven

El proyecto está organizado como una solución multi-módulo Maven. Esto permite administrar de forma centralizada las dependencias, versiones y módulos asociados a cada microservicio.

Se identifican módulos para:

- Eureka Server.
- API Gateway.
- Microservicio de autenticación.
- Microservicio de producto.
- Microservicio de creación de usuario.
- Microservicio de andén.
- Microservicio de camión.
- Microservicio de recepción.
- Microservicio de factura.
- Microservicio de desconsolidación.
- Microservicio de warehouse.
- Microservicio de stock.
- Microservicio de movimiento.

Este punto cumple con la expectativa de una arquitectura distribuida basada en servicios independientes.

---

### 3.2 Cantidad mínima de microservicios

El proyecto supera el mínimo de 10 microservicios requerido para la actividad. Se identifican 11 microservicios funcionales o definidos, sin contar Eureka Server ni API Gateway.

Microservicios identificados:

| N° | Microservicio | Función general |
|---:|---|---|
| 1 | autenticacion | Gestión de credenciales/autenticación. |
| 2 | producto | Gestión de productos. |
| 3 | creacion-usuario | Gestión de empleados/usuarios. |
| 4 | anden | Gestión de andenes. |
| 5 | camion | Gestión de camiones. |
| 6 | recepcion | Gestión de recepciones. |
| 7 | factura | Gestión de facturas asociadas a recepciones. |
| 8 | desconsolidacion | Gestión de procesos de desconsolidación. |
| 9 | warehouse | Gestión de ubicaciones o bodegas. |
| 10 | stock | Gestión de stock de productos. |
| 11 | movimiento | Registro de movimientos logísticos. |

Este punto está listo.

---

### 3.3 Eureka Server

El proyecto incluye un servidor Eureka configurado en el puerto `8761`. Esto permite que los microservicios se registren dinámicamente y que otros componentes, como el API Gateway, puedan descubrirlos por nombre lógico.

Estado: **listo**.

Relevancia técnica:

- Permite service discovery.
- Reduce acoplamiento por direcciones físicas.
- Facilita el enrutamiento mediante `lb://nombre-servicio`.
- Permite observar los servicios activos desde el panel de Eureka.

---

### 3.4 API Gateway

Existe un API Gateway configurado en el puerto `8080`. El Gateway define rutas hacia distintos microservicios mediante Spring Cloud Gateway y utiliza Eureka para resolver los nombres lógicos de los servicios.

Ejemplo conceptual de rutas encontradas:

```properties
spring.cloud.gateway.routes[1].id=producto
spring.cloud.gateway.routes[1].uri=lb://producto
spring.cloud.gateway.routes[1].predicates[0]=Path=/api/productos/**
```

Esto indica que las solicitudes dirigidas a `/api/productos/**` son derivadas al microservicio `producto`.

Estado: **listo/parcial**.

Está listo porque el Gateway existe y cumple su función principal, pero se considera parcial porque la documentación no coincide completamente con la configuración real de puertos y rutas.

---

### 3.5 Comunicación entre microservicios

Se identificó comunicación entre microservicios mediante Feign Client. Esto es importante porque demuestra interoperabilidad y colaboración entre servicios independientes.

Ejemplos de comunicación detectada:

| Servicio origen | Servicio destino | Propósito probable |
|---|---|---|
| producto | stock | Validar o consultar stock asociado. |
| producto | desconsolidacion | Asociar producto a proceso logístico. |
| stock | producto | Consultar existencia o datos del producto. |
| recepcion | camion | Validar camión asociado a una recepción. |
| recepcion | anden | Validar andén asociado a una recepción. |
| factura | recepcion | Validar recepción asociada a una factura. |
| desconsolidacion | factura | Validar factura asociada. |
| movimiento | warehouse | Validar ubicación o bodega. |
| movimiento | producto | Validar producto involucrado. |
| movimiento | desconsolidacion | Asociar movimiento a proceso logístico. |

Estado: **listo/parcial**.

El punto está bien encaminado, pero conviene documentar claramente estas relaciones en el README y, si es posible, con un diagrama simple de comunicación.

---

### 3.6 Separación por capas Controller-Service-Repository

La mayoría de los microservicios sigue una estructura similar a:

```text
controller/
service/
repository/
model/
```

Esto permite separar responsabilidades:

- `Controller`: expone endpoints REST.
- `Service`: contiene reglas de negocio.
- `Repository`: accede a la base de datos.
- `Model`: representa entidades del dominio.

Estado: **mayormente listo**.

Detalle pendiente:

Existen algunas inconsistencias de nombres entre paquetes con mayúscula y minúscula, por ejemplo:

```text
Controller
Service
Repository
Model
```

versus:

```text
controller
service
repository
model
```

Esto no necesariamente impide la ejecución, pero afecta la prolijidad y consistencia del proyecto.

---

### 3.7 Reglas de negocio básicas

Se identifican reglas de negocio en distintos servicios, por ejemplo:

- Validación de SKU duplicado.
- Validación de stock negativo.
- Validación de stock insuficiente.
- Validación de existencia de recepción.
- Validación de existencia de factura.
- Validación de existencia de andén.
- Validación de existencia de camión.
- Validación de estados permitidos.

Estado: **parcialmente listo**.

Las reglas existen, pero deben quedar respaldadas con pruebas unitarias reales. Sin pruebas, la pauta puede considerar que la lógica no está suficientemente validada.

---

### 3.8 Swagger/OpenAPI base

Los microservicios incluyen dependencias relacionadas con Springdoc OpenAPI. El Gateway también cuenta con configuración para visualizar documentación centralizada de distintos servicios.

Estado: **parcial**.

Está instalado y configurado, pero falta enriquecer la documentación de endpoints con anotaciones específicas.

---

### 3.9 JARs generados

Se encontraron archivos `.jar` dentro de carpetas `target`, lo que indica que los módulos fueron compilados previamente.

Estado: **listo como evidencia parcial**.

Sin embargo, no reemplaza la necesidad de documentar cómo compilar, ejecutar y probar el proyecto desde cero.

---

## 4. Elementos parciales o con observaciones

### 4.1 API Gateway con documentación inconsistente

El Gateway existe y está configurado, pero el README específico del Gateway contiene información que no coincide completamente con los puertos reales definidos en los archivos de configuración.

Por ejemplo, en la configuración real se observa una distribución similar a:

| Servicio | Puerto real detectado |
|---|---:|
| api-gateway | 8080 |
| eureka-server | 8761 |
| recepcion | 8081 |
| camion | 8082 |
| anden | 8083 |
| desconsolidacion | 8084 |
| factura | 8085 |
| producto | 8086 |
| warehouse | 8087 |
| stock | 8088 |
| movimiento | 8089 |
| creacion-usuario | 8092 |
| autenticacion | dinámico, `server.port=0` |

La documentación debe actualizarse para reflejar esta configuración real.

#### Acción recomendada

Actualizar el README para que los puertos, rutas y nombres de servicios coincidan con el código.

---

### 4.2 Ruta de autenticación inconsistente

El Gateway enruta autenticación mediante una ruta asociada a `/api/credenciales/**`, pero la documentación menciona `/api/autenticacion/**`.

Además, el controller de autenticación aparece definido con:

```java
@RequestMapping("/api/credenciales")
```

Sin embargo, no se identifican métodos como:

```java
@GetMapping
@PostMapping
@PutMapping
@DeleteMapping
```

Estado: **incompleto**.

#### Opciones de corrección

Opción A: completar el microservicio de autenticación con endpoints funcionales.

Opción B: no defender autenticación como servicio principal, ya que el proyecto ya cuenta con más de 10 microservicios.

---

### 4.3 Swagger automático, pero no suficientemente documentado

Actualmente Swagger puede mostrar endpoints automáticamente gracias a Springdoc, pero la pauta normalmente exige documentación técnica clara.

Faltan anotaciones como:

```java
@Operation
@ApiResponse
@ApiResponses
@Parameter
@Schema
```

#### Ejemplo recomendado

```java
@Operation(
    summary = "Registrar producto",
    description = "Crea un producto validando SKU, fechas y proceso de desconsolidación asociado."
)
@ApiResponses({
    @ApiResponse(responseCode = "201", description = "Producto creado correctamente"),
    @ApiResponse(responseCode = "400", description = "Datos inválidos o regla de negocio incumplida"),
    @ApiResponse(responseCode = "404", description = "Recurso asociado no encontrado")
})
@PostMapping
public ResponseEntity<?> registrar(@RequestBody Producto producto) {
    // lógica del controlador
}
```

Estado: **parcial**.

---

## 5. Elementos pendientes críticos

### 5.1 Pruebas unitarias reales

Este es el punto más crítico del proyecto.

Se encontraron clases de prueba generadas por Spring Boot, como:

```java
@SpringBootTest
class ProductoApplicationTests {

    @Test
    void contextLoads() {
    }
}
```

Estas pruebas solo validan que el contexto de Spring pueda cargar. No prueban reglas de negocio, no usan mocks y no contienen asserts.

La pauta exige pruebas más completas, idealmente con:

- JUnit.
- Mockito.
- Given-When-Then.
- `assertEquals`.
- `assertThrows`.
- `verify`.
- Cobertura cercana o superior al 80%.

Estado: **pendiente crítico**.

#### Pruebas recomendadas por servicio

##### ProductoService

Casos sugeridos:

- Registrar producto nuevo correctamente.
- Rechazar SKU duplicado.
- Rechazar fecha de caducidad anterior a fecha de fabricación.
- Rechazar producto asociado a desconsolidación inexistente.

##### StockService

Casos sugeridos:

- Inicializar stock con cantidad válida.
- Rechazar stock negativo.
- Descontar stock correctamente.
- Rechazar descuento por stock insuficiente.

##### RecepcionService

Casos sugeridos:

- Registrar recepción con camión y andén existentes.
- Rechazar recepción con camión inexistente.
- Rechazar recepción con andén inexistente.
- Rechazar estado inválido.

##### FacturaService

Casos sugeridos:

- Registrar factura con recepción existente.
- Rechazar factura con recepción inexistente.
- Rechazar estado inválido.

---

### 5.2 Archivos YAML

La configuración actual está en archivos `.properties`. La pauta y las directrices de la unidad solicitan trabajar con archivos YAML.

Estado: **pendiente crítico**.

#### Recomendación

Migrar los archivos:

```text
application.properties
```

a:

```text
application.yml
```

Se recomienda comenzar por:

1. API Gateway.
2. Eureka Server.
3. Producto.
4. Stock.
5. Recepción.
6. Factura.

Luego migrar el resto de los servicios para mantener consistencia.

#### Ejemplo conceptual para API Gateway

```yaml
server:
  port: 8080

spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      routes:
        - id: producto
          uri: lb://producto
          predicates:
            - Path=/api/productos/**
        - id: stock
          uri: lb://stock
          predicates:
            - Path=/api/stock/**

 eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
    register-with-eureka: true
    fetch-registry: true
```

> Nota: revisar indentación antes de copiar este ejemplo. YAML es sensible a espacios.

---

### 5.3 README general del proyecto

Actualmente falta un README general en la raíz del repositorio. Este archivo es importante porque será la primera documentación visible en GitHub.

Estado: **pendiente crítico**.

El README general debería incluir:

- Nombre del proyecto.
- Descripción general.
- Contexto de negocio.
- Integrantes.
- Arquitectura del sistema.
- Listado de microservicios.
- Tabla de puertos.
- Tabla de rutas del Gateway.
- Endpoints principales.
- Requisitos técnicos.
- Instrucciones de ejecución.
- Orden de levantamiento.
- Acceso a Eureka.
- Acceso a Swagger centralizado.
- Instrucciones para ejecutar pruebas.
- Información de despliegue.
- Estado actual del proyecto.
- Pendientes conocidos.

---

### 5.4 Despliegue local o remoto

No se encontraron archivos como:

```text
Dockerfile
docker-compose.yml
render.yaml
railway.json
```

Por lo tanto, el despliegue no está evidenciado.

Estado: **pendiente**.

#### Alternativas válidas

Opción A: documentar ejecución local de todos los servicios.

Opción B: agregar Dockerfile por servicio.

Opción C: agregar `docker-compose.yml` para levantar Eureka, Gateway, base de datos y servicios principales.

Opción D: documentar despliegue en Render o Railway.

Si queda poco tiempo, al menos se recomienda dejar documentado de forma clara el despliegue local.

---

### 5.5 Documentación Swagger detallada

Aunque Swagger está instalado, falta documentar los endpoints de manera explícita.

Estado: **pendiente parcial**.

Se recomienda documentar al menos los endpoints principales de cada microservicio:

- `GET /api/productos`
- `POST /api/productos`
- `GET /api/stock`
- `POST /api/stock`
- `POST /api/recepciones`
- `POST /api/facturas`
- `POST /api/movimientos`
- `GET /api/warehouse`

Para cada endpoint relevante, agregar:

- resumen;
- descripción;
- códigos de respuesta;
- cuerpo esperado;
- errores posibles.

---

## 6. Pendientes recomendados, pero menos urgentes

### 6.1 HATEOAS

HATEOAS aparece en las guías de clase, pero no se identificó implementación en el proyecto.

Estado: **no implementado**.

No se recomienda priorizarlo antes que pruebas, YAML, README o despliegue, salvo que la pauta lo exija explícitamente con alto puntaje.

---

### 6.2 DataFaker

No se identificó uso de DataFaker para generación de datos de prueba.

Estado: **no implementado**.

Puede servir como mejora, pero no es tan prioritario como testing real o documentación.

---

### 6.3 Normalización de nombres y paquetes

Se recomienda estandarizar nombres de paquetes en minúscula:

```text
controller
service
repository
model
```

También se recomienda revisar nombres poco claros, por ejemplo:

```java
StockServiceServ
```

El nombre sugerido sería:

```java
StockService
```

Estado: **mejora recomendada**.

---

## 7. Orden recomendado de corrección

Si el tiempo es limitado, se recomienda trabajar en este orden:

### Prioridad 1: pruebas unitarias reales

Crear pruebas con JUnit y Mockito para los servicios más importantes:

- ProductoService.
- StockService.
- RecepcionService.
- FacturaService.

Este punto puede impactar directamente la nota y la defensa.

---

### Prioridad 2: README general y corrección del README Gateway

Crear un README raíz para GitHub y corregir las inconsistencias del README del Gateway.

Debe quedar claro:

- qué hace el sistema;
- qué servicios existen;
- cómo se ejecuta;
- qué puertos usa;
- cómo acceder a Eureka;
- cómo acceder a Swagger;
- cómo se prueban los servicios.

---

### Prioridad 3: migración a YAML

Migrar los archivos `.properties` a `.yml`, comenzando por los servicios principales.

El ideal es que todos los módulos usen `application.yml`.

---

### Prioridad 4: Swagger detallado

Agregar anotaciones OpenAPI a los controllers principales.

No es necesario documentar todo al máximo si el tiempo es poco, pero sí los endpoints más representativos.

---

### Prioridad 5: despliegue

Agregar evidencia de despliegue o documentar claramente la ejecución local.

Mínimo aceptable sugerido:

- instrucciones locales paso a paso;
- orden de arranque;
- comandos Maven;
- URLs de prueba;
- acceso a Eureka;
- acceso a Swagger.

Ideal:

- Dockerfile por servicio;
- `docker-compose.yml`;
- instrucciones de ejecución con Docker.

---

### Prioridad 6: autenticación

Completar el microservicio de autenticación o no presentarlo como servicio principal en la defensa.

Si se decide completarlo, se recomienda agregar al menos:

- crear credencial;
- listar credenciales;
- buscar credencial por ID;
- validar login básico;
- eliminar o desactivar credencial.

---

## 8. Matriz de cumplimiento contra la pauta

| Indicador | Estado estimado | Observación |
|---|---:|---|
| IE 1.2.1 - Patrón CSR | Bueno | La mayoría de servicios tiene Controller, Service y Repository. |
| IE 2.2.1 - Reglas de negocio | Aceptable | Hay reglas, pero falta validarlas con pruebas. |
| IE 2.4.1 - Comunicación REST | Aceptable | Existen Feign Clients entre varios servicios. |
| IE 2.5.1 - GitHub | No evaluable desde ZIP | Falta revisar repositorio real. |
| IE 2.5.2 - Trello o gestión ágil | No evidenciado | No se encontró evidencia en el ZIP. |
| IE 3.1.1 - Pruebas unitarias | Bajo | Solo existen pruebas `contextLoads()`. |
| IE 3.2.1 - Swagger/OpenAPI | Parcial | Está instalado, pero poco documentado. |
| IE 3.3.1 - Despliegue | No evidenciado | No se encontró Docker, Render, Railway o similar. |
| IE 3.3.2 - API Gateway | Aceptable | Existe Gateway y rutas configuradas. |
| IE 3.3.3 - Interoperabilidad Gateway | Aceptable | Gateway enruta hacia servicios mediante Eureka. |
| IE 3.3.4 - YAML | No logrado | La configuración está en `.properties`. |

---

## 9. Recomendación para defensa oral

Durante la defensa, se recomienda explicar el sistema desde la arquitectura general hacia los servicios específicos.

Orden sugerido:

1. Presentar el problema logístico que resuelve Royal Logistics.
2. Explicar por qué se separó en microservicios.
3. Mostrar el `pom.xml` padre y los módulos.
4. Mostrar Eureka funcionando.
5. Mostrar API Gateway y sus rutas.
6. Mostrar Swagger centralizado.
7. Probar un flujo entre servicios.
8. Explicar un Feign Client.
9. Mostrar una regla de negocio en un Service.
10. Mostrar una prueba unitaria real.
11. Explicar pendientes o mejoras futuras.

Si preguntan por autenticación, responder con transparencia:

> El microservicio de autenticación está creado como parte de la arquitectura, pero todavía requiere completar sus endpoints funcionales. El sistema actualmente se enfoca en los servicios logísticos principales y supera el mínimo de microservicios exigido.

---

## 10. Veredicto final

El proyecto tiene una base técnica sólida para ser defendido como arquitectura de microservicios. Cuenta con múltiples servicios, Eureka, Gateway, comunicación REST entre servicios, estructura por capas y Swagger base.

No obstante, para cumplir de mejor manera la pauta de evaluación, todavía deben corregirse puntos importantes:

1. Crear pruebas unitarias reales con JUnit y Mockito.
2. Migrar configuración a YAML.
3. Crear README general del proyecto.
4. Corregir documentación de puertos y rutas.
5. Completar documentación Swagger con anotaciones.
6. Evidenciar despliegue local/remoto.
7. Completar o justificar el estado del microservicio de autenticación.

Con estas correcciones, el proyecto quedaría mucho más sólido tanto para la entrega en GitHub como para la defensa técnica.

---

## 11. Checklist final antes de entregar

### Arquitectura

- [x] Proyecto multi-módulo Maven.
- [x] Más de 10 microservicios.
- [x] Eureka Server.
- [x] API Gateway.
- [x] Comunicación mediante Feign Client.
- [x] Separación general Controller-Service-Repository.

### Documentación

- [ ] README general en la raíz.
- [ ] README Gateway corregido.
- [ ] Tabla de puertos actualizada.
- [ ] Tabla de rutas Gateway actualizada.
- [ ] Instrucciones de ejecución por servicio.
- [ ] Instrucciones para ejecutar pruebas.
- [ ] Capturas o evidencia de Eureka.
- [ ] Capturas o evidencia de Swagger.

### Testing

- [ ] Pruebas unitarias reales para ProductoService.
- [ ] Pruebas unitarias reales para StockService.
- [ ] Pruebas unitarias reales para RecepcionService.
- [ ] Pruebas unitarias reales para FacturaService.
- [ ] Uso de Mockito.
- [ ] Uso de asserts.
- [ ] Pruebas de errores con `assertThrows`.
- [ ] Reporte o evidencia de cobertura.

### Configuración

- [ ] Migrar `application.properties` a `application.yml`.
- [ ] Validar nombres de servicios en Eureka.
- [ ] Validar rutas `lb://` en Gateway.
- [ ] Validar puertos reales.

### Swagger

- [x] Dependencia Springdoc instalada.
- [x] Swagger centralizado en Gateway.
- [ ] Anotaciones `@Operation`.
- [ ] Anotaciones `@ApiResponse`.
- [ ] Descripción de errores.
- [ ] Ejemplos de request/response.

### Despliegue

- [ ] Documentar ejecución local.
- [ ] Agregar Dockerfile o docker-compose, si corresponde.
- [ ] Documentar despliegue Render/Railway, si corresponde.
- [ ] Indicar URLs finales.

### Mejoras de código

- [ ] Normalizar nombres de paquetes.
- [ ] Revisar nombres de clases poco claros.
- [ ] Completar autenticación.
- [ ] Revisar consistencia entre rutas, controllers y Gateway.
