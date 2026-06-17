# API Gateway - Royal Logistics

## Descripción

API Gateway centralizado para el sistema de gestión logística **Royal Logistics**. Actúa como punto de entrada único para todos los microservicios.

## Características

- ✅ **Enrutamiento dinámico** a través de Spring Cloud Gateway
- ✅ **Descubrimiento de servicios** mediante Eureka
- ✅ **Load Balancing** automático con `lb://` (Load Balancer)
- ✅ **Filtros de rutas** para manipulación de solicitudes
- ✅ **Monitoreo** a través de Actuator endpoints

## Tecnologías

- Java 21
- Spring Boot 3.5.14
- Spring Cloud Gateway
- Netflix Eureka Client
- Maven

## Puerto

**Puerto por defecto:** `8080`

## Rutas Configuradas

El API Gateway expone todas las rutas de los microservicios bajo un punto de entrada único:

| Ruta | Microservicio | Puerto Original |
|------|---------------|-----------------|
| `/api/autenticacion/**` | autenticacion | 8082 |
| `/api/productos/**` | producto | 8081 |
| `/api/usuarios/**` | creacion-usuario | 8084 |
| `/api/andenes/**` | anden | 8083 |
| `/api/camiones/**` | camion | 8085 |
| `/api/recepciones/**` | recepcion | 8086 |
| `/api/facturas/**` | factura | 8087 |
| `/api/desconsolidaciones/**` | desconsolidacion | 8088 |
| `/api/warehouse/**` | warehouse | 8089 |
| `/api/stock/**` | stock | 8090 |
| `/api/movimientos/**` | movimiento | 8091 |

## Cómo ejecutar

### Requisitos previos

- Java 21 instalado
- Maven instalado o mvnw disponible
- Eureka Server ejecutándose en `http://localhost:8761`

### Comando de ejecución

Desde la carpeta `api-gateway`:

```bash
# Con Maven
mvn spring-boot:run

# O compilar y ejecutar el JAR
mvn clean package
java -jar target/api-gateway-0.0.1-SNAPSHOT.jar
```

## Endpoints de Monitoreo

Una vez iniciado el API Gateway, accede a:

- **Estado del Gateway:** `http://localhost:8080/actuator/health`
- **Información:** `http://localhost:8080/actuator/info`
- **Rutas configuradas:** `http://localhost:8080/actuator/gateway/routes`

## Flujo de funcionamiento

```
Cliente
   ↓
API Gateway (8080)
   ↓
[Eureka descubre servicios]
   ↓
Microservicios específicos
```

## Configuración

Todos los parámetros se encuentran en:

```
src/main/resources/application.properties
```

### Parámetros principales

- `server.port=8080` - Puerto del Gateway
- `eureka.client.service-url.defaultZone=http://localhost:8761/eureka` - URL de Eureka
- `spring.cloud.gateway.routes[X]` - Definición de rutas

## Agregar nuevas rutas

Para agregar un nuevo microservicio al Gateway, añade en `application.properties`:

```properties
spring.cloud.gateway.routes[N].id=nombre-servicio
spring.cloud.gateway.routes[N].uri=lb://nombre-servicio
spring.cloud.gateway.routes[N].predicates[0]=Path=/api/ruta/**
spring.cloud.gateway.routes[N].filters[0]=StripPrefix=1
```

Donde:
- `N` es el siguiente número de ruta disponible
- `nombre-servicio` es el `spring.application.name` registrado en Eureka
- `/api/ruta/**` es la ruta que los clientes usarán

## Troubleshooting

| Problema | Solución |
|----------|----------|
| No puedo conectar al Gateway | Verifica que el puerto 8080 esté disponible |
| "Service not found" | Asegúrate de que Eureka Server está activo y los microservicios registrados |
| Las rutas no funcionan | Revisa que los nombres en `routes[].id` coincidan con `spring.application.name` |
| Gateway lento | Verifica logs en `logging.level.org.springframework.cloud.gateway=DEBUG` |

## Logs

Los logs del Gateway se muestran con nivel DEBUG para gateway y INFO para otros componentes. Verifica la salida de consola al ejecutar.

---

**Próximos pasos:**
- Configurar autenticación/autorización en el Gateway
- Implementar rate limiting
- Agregar circuit breaker con Resilience4j
- Implementar CORS policies
