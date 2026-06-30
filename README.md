<div align="center">

<h1 style="color:#2563eb;">
Royal Logistics
</h1>

<h3>
Sistema de gestión logística basado en arquitectura de microservicios
</h3>

</div>

---

<h2 style="color:#2563eb;">📦 Descripción</h2>

<p>
Sistema de gestión logística basado en arquitectura de microservicios, orientado a operaciones Fulfillment, administración de Warehouse y control de inventario dentro de una cadena logística moderna.
</p>

<p>
El proyecto busca simular el funcionamiento de un centro de distribución similar a operaciones utilizadas actualmente por empresas fulfillment, donde distintos vendedores almacenan productos dentro de una misma red logística, manteniendo control, seguimiento y trazabilidad sobre cada operación realizada.
</p>

---

<h2 style="color:#2563eb;">🎯 Objetivo del proyecto</h2>

<p>
El objetivo principal del sistema consiste en desarrollar una plataforma logística modular capaz de administrar procesos de:
</p>

<ul>
<li>recepción de mercadería</li>
<li>ingreso de facturas</li>
<li>desconsolidación de productos</li>
<li>almacenamiento en warehouse</li>
<li>control de stock</li>
<li>movimientos internos</li>
<li>seguimiento operacional</li>
</ul>

<p>
Todo esto mediante arquitectura de microservicios utilizando Spring Boot y APIs REST.
</p>

---

<h2 style="color:#2563eb;">📍 Flujo logístico general</h2>

<pre>
Agendamiento de recepción
        ↓
Llegada de camión
        ↓
Asignación de andén
        ↓
Recepción de factura
        ↓
Descarga de pallets / cajas
        ↓
Desconsolidación
        ↓
Registro de productos
        ↓
Asignación de ubicación
        ↓
Warehouse
        ↓
Actualización de stock
        ↓
Movimientos internos
        ↓
Seguimiento y trazabilidad
</pre>

---

<h2 style="color:#2563eb;">🏗️ Arquitectura de microservicios</h2>

<p>
El sistema fue diseñado bajo arquitectura de microservicios, donde cada módulo cumple una responsabilidad específica dentro de la cadena logística.
</p>

<p>
Cada microservicio posee:
</p>

<ul>
<li>lógica independiente</li>
<li>endpoints REST</li>
<li>base de datos desacoplada</li>
<li>responsabilidades separadas</li>
</ul>

---

<h2 style="color:#2563eb;">⚙️ Tecnologías utilizadas</h2>

<h3>Backend</h3>

<ul>
<li>Java 21</li>
<li>Spring Boot</li>
<li>Spring Data JPA</li>
<li>Hibernate</li>
<li>Maven</li>
<li>Lombok</li>
<li>Jakarta Validation</li>
</ul>

<h3>Arquitectura</h3>

<ul>
<li>Microservices</li>
<li>REST APIs</li>
<li>Eureka Server</li>
</ul>

<h3>Base de datos</h3>

<ul>
<li>MySQL</li>
<li>Oracle SQL Data Modeler</li>
</ul>

<h3>Herramientas/Software</h3>

<ul>
<li>Postman</li>
<li>Visual Studio Code</li>
<li>IntelliJ IDEA</li>
</ul>

---

<h2 style="color:#2563eb;">🗄️ Modelado de base de datos</h2>

<p>
Para visualizar el modelado relacional utilizado dentro del proyecto:
</p>

<ul>

<li>
Descargar Oracle SQL Data Modeler desde:
<a href="https://www.oracle.com/database/sqldeveloper/technologies/sql-data-modeler/download/" target="_blank">
Oracle SQL Data Modeler
</a>
</li>

<li>
Descargar y extraer la carpeta 
<a href="./modelado%20microservicios">
<b>modelado microservicios</b>
</a>
</li>

<li>
Abrir el proyecto desde Oracle SQL Data Modeler
</li>

</ul>

<p>
El modelado fue desarrollado previamente para mantener coherencia entre el flujo logístico y la arquitectura de microservicios.
</p>

---

<h2 style="color:#2563eb;">⭐ Características principales del sistema</h2>

<ul>
<li>Arquitectura desacoplada</li>
<li>Separación de responsabilidades</li>
<li>Gestión de Warehouse</li>
<li>Control de inventario</li>
<li>Seguimiento operacional</li>
<li>Gestión de ubicaciones</li>
<li>Registro de recepciones</li>
<li>Administración de productos</li>
<li>Escalabilidad modular</li>
<li>Trazabilidad logística</li>
</ul>

---

<h2 style="color:#2563eb;">📊 Estado actual del proyecto</h2>

<p>
El proyecto <b>Royal Logistics</b> se encuentra en estado defendible como ecosistema de microservicios. La versión actual incorpora configuración YAML, Eureka Server, API Gateway, documentación Swagger/OpenAPI, pruebas unitarias, Docker Compose, script de inicialización de base de datos y scripts de ejecución para Windows.
</p>

| Área | Estado | Observación |
|---|---:|---|
| Arquitectura de microservicios | Listo | Proyecto separado en módulos independientes. |
| Maven multi-módulo | Listo | Existe un `pom.xml` padre que centraliza módulos y dependencias. |
| Java 21 / Spring Boot | Listo | Los servicios están implementados con Spring Boot y Java 21. |
| Eureka Server | Listo | Disponible en `http://localhost:8761`. |
| API Gateway | Listo | Disponible en `http://localhost:8080`. |
| Swagger/OpenAPI | Listo | Accesible desde el API Gateway. |
| YAML | Listo | Los servicios usan `application.yml`. |
| Pruebas unitarias | Listo | Se validan con `mvn clean test`. |
| Docker Compose | Listo con observaciones | Levanta MySQL, Eureka, Gateway y microservicios logísticos principales. |
| Base de datos Docker | Listo | MySQL se ejecuta en contenedor y usa volumen persistente. |
| `docs/init.sql` | Listo | Crea las bases de datos necesarias al primer arranque de MySQL. |
| Autenticación | Parcial | Módulo creado, pero no forma parte del flujo logístico principal validado. |
| Creación de usuario | Parcial en Docker | Módulo creado y ruta Gateway configurada, pero no está incluido en el Docker Compose principal. |

---

<h2 style="color:#2563eb;">🐳 Puesta en marcha con Docker</h2>

## 1. Descripción general

Este documento explica cómo ejecutar el ecosistema de microservicios **Royal Logistics** utilizando **Docker** y **Docker Compose**.

Docker permite levantar los componentes principales del sistema en contenedores independientes, evitando iniciar manualmente cada microservicio desde el IDE. Cada servicio se ejecuta de forma aislada, pero todos se comunican mediante una red interna de Docker.

El despliegue actual considera los siguientes componentes:

```text
MySQL
Eureka Server
API Gateway
Microservicio de Andén
Microservicio de Camión
Microservicio de Recepción
Microservicio de Factura
Microservicio de Desconsolidación
Microservicio de Producto
Microservicio de Warehouse
Microservicio de Stock
Microservicio de Movimiento
```

> Nota: los módulos `autenticacion` y `creacion-usuario` existen dentro del proyecto, pero no forman parte del Docker Compose principal validado. Se mantienen como servicios complementarios o mejora futura del despliegue.

---

## 2. Objetivo del despliegue

El objetivo del despliegue con Docker es ejecutar Royal Logistics como un sistema distribuido compuesto por varios microservicios.

Con Docker Compose es posible levantar el ecosistema mediante un solo comando, incluyendo base de datos, servidor de descubrimiento, API Gateway y servicios logísticos.

El flujo general es:

```text
Usuario / Postman / Swagger
        ↓
API Gateway :8080
        ↓
Eureka Server :8761
        ↓
Microservicios logísticos
        ↓
MySQL en Docker
```

---

## 3. Importante: el ZIP no es una imagen Docker completa

El archivo ZIP del proyecto **no corresponde a una imagen Docker completa**.

El ZIP corresponde al código fuente y paquete de despliegue del sistema Royal Logistics. Docker utiliza los archivos del proyecto para construir las imágenes y levantar los contenedores necesarios.

El proyecto incluye:

```text
Código fuente de los microservicios.
Archivos Dockerfile para los servicios principales.
Archivo docker-compose.yml.
Archivo docs/init.sql.
Scripts Iniciar.bat y Cerrar.bat.
README con instrucciones de puesta en marcha.
```

El archivo principal de configuración del despliegue es:

```text
docker-compose.yml
```

En el `docker-compose.yml` se define:

```text
Qué servicios se ejecutan.
Qué Dockerfile se usa para construir cada servicio.
Qué puertos se exponen.
Qué variables de entorno se usan.
Qué red interna conecta los servicios.
Qué volumen se utiliza para MySQL.
Qué servicios dependen de otros.
```

Por lo tanto:

```text
El ZIP no reemplaza a Docker.
El docker-compose.yml define cómo Docker debe levantar el ecosistema.
Los .jar son generados por Maven en las carpetas target/ de cada microservicio.
```

---

## 4. Requisito obligatorio: Docker Desktop debe estar abierto

Antes de ejecutar el sistema, se debe abrir **Docker Desktop**.

Si Docker Desktop no está iniciado, los comandos de Docker no funcionarán.

Antes de levantar el sistema, se puede verificar Docker con:

```bash
docker --version
docker compose version
docker info
```

Si aparece un error similar a:

```text
Cannot connect to the Docker daemon
```

or:

```text
open //./pipe/DockerDesktopLinuxEngine: The system cannot find the file specified
```

significa que Docker Desktop no está abierto o que el motor de Docker todavía no ha iniciado.

Solución:

```text
1. Abrir Docker Desktop.
2. Esperar a que Docker quede iniciado.
3. Volver a ejecutar el comando o el script Iniciar.bat.
```

---

## 5. Requisitos previos

Antes de ejecutar el sistema, se debe contar con:

```text
Docker Desktop instalado.
Docker Desktop abierto y funcionando.
Java 21 instalado.
Maven instalado y disponible en la terminal.
Carpeta del proyecto Royal Logistics.
Archivo docker-compose.yml.
Carpeta docs con archivo init.sql.
Scripts Iniciar.bat y Cerrar.bat.
```

En laboratorio o en un equipo local, se debe verificar que Docker Desktop esté abierto antes de ejecutar el sistema.

---

## 6. Estructura de carpetas esperada

La estructura principal del proyecto es:

```text
gestion_inv/
├── 0. eureka-server/
│   └── Dockerfile
├── 1. autenticacion/
├── 2. producto/
│   └── Dockerfile
├── 3. creacion-usuario/
├── 4. anden/
│   └── Dockerfile
├── 5. camion/
│   └── Dockerfile
├── 6. recepcion/
│   └── Dockerfile
├── 7. factura/
│   └── Dockerfile
├── 8. desconsolidacion/
│   └── Dockerfile
├── 9. warehouse/
│   └── Dockerfile
├── 10. stock/
│   └── Dockerfile
├── 11. movimiento/
│   └── Dockerfile
├── 12. api-gateway/
│   └── Dockerfile
├── docs/
│   └── init.sql
├── docker-compose.yml
├── Iniciar.bat
├── Cerrar.bat
├── pom.xml
└── README_Royal_Logistics.md
```

A diferencia del ejemplo de despliegue basado en carpeta `apps/`, este proyecto construye las imágenes Docker a partir de los `Dockerfile` de cada microservicio. Los `.jar` se generan automáticamente al ejecutar Maven.

---

## 7. Archivos `.jar` generados por Maven

Antes de construir las imágenes Docker, Maven empaqueta los microservicios con:

```bash
mvn clean package -DskipTests
```

Este comando genera archivos `.jar` dentro de la carpeta `target/` de cada módulo.

Ejemplos:

```text
0. eureka-server/target/eureka-server-1.0.0-SNAPSHOT.jar
2. producto/target/producto-1.0.0-SNAPSHOT.jar
4. anden/target/anden-1.0.0-SNAPSHOT.jar
5. camion/target/camion-1.0.0-SNAPSHOT.jar
6. recepcion/target/recepcion-1.0.0-SNAPSHOT.jar
7. factura/target/factura-1.0.0-SNAPSHOT.jar
8. desconsolidacion/target/desconsolidacion-1.0.0-SNAPSHOT.jar
9. warehouse/target/warehouse-1.0.0-SNAPSHOT.jar
10. stock/target/stock-1.0.0-SNAPSHOT.jar
11. movimiento/target/movimiento-1.0.0-SNAPSHOT.jar
12. api-gateway/target/api-gateway-1.0.0-SNAPSHOT.jar
```

Los Dockerfile copian estos `.jar` y los ejecutan dentro de cada contenedor mediante Java.

---

## 8. Archivo `docs/init.sql`

El archivo:

```text
docs/init.sql
```

permite crear las bases de datos necesarias cuando el contenedor MySQL se inicia por primera vez con un volumen nuevo.

Bases consideradas en el script:

```text
bd_camion
bd_productos
bd_facturas
bd_desconsolidaciones
bd_stock
bd_anden
bd_warehouses
bd_recepciones
bd_movimientos
bd_credenciales
bd_usuarios
bd_camiones
bd_movimiento
```

El archivo está referenciado desde `docker-compose.yml` mediante:

```yaml
volumes:
  - mysql_data:/var/lib/mysql
  - ./docs/init.sql:/docker-entrypoint-initdb.d/init.sql:ro
```

Esto significa que Docker toma el archivo local `docs/init.sql` y lo monta dentro del contenedor MySQL para ejecutarlo durante la primera inicialización.

Importante:

```text
El init.sql se ejecuta automáticamente solo la primera vez que se crea el volumen de MySQL.
Si el volumen mysql_data ya existe, MySQL no vuelve a ejecutar este archivo automáticamente.
```

Si se necesita recrear todo desde cero:

```bash
docker compose down -v
docker compose up -d --build
```

Advertencia:

```text
No usar docker compose down -v si se desea conservar información de la base de datos.
Ese comando elimina también el volumen persistente de MySQL.
```

---

## 9. Componentes del sistema

| Componente | Descripción | Puerto |
|---|---|---:|
| MySQL | Base de datos en contenedor Docker | 3307 externo / 3306 interno |
| Eureka Server | Servidor de descubrimiento de microservicios | 8761 |
| API Gateway | Punto de entrada central del sistema | 8080 |
| Producto | Gestión de productos | 8086 |
| Stock | Control de stock | 8088 |
| Movimiento | Registro de movimientos internos | 8089 |
| Andén | Gestión de andenes | 8083 |
| Camión | Gestión de camiones | 8082 |
| Recepción | Registro de recepciones | 8081 |
| Factura | Registro de facturas | 8085 |
| Desconsolidación | Desconsolidación de productos | 8084 |
| Warehouse | Gestión de ubicaciones de bodega | 8087 |
| Autenticación | Servicio complementario | Puerto dinámico / no incluido en Docker Compose principal |
| Creación usuario | Servicio complementario | 8092 / no incluido en Docker Compose principal |

---

## 10. Orden lógico de arranque

Aunque Docker Compose puede levantar todo el sistema con un solo comando, el orden lógico del ecosistema es:

```text
1. MySQL
2. Eureka Server
3. Microservicios logísticos
4. API Gateway
```

En esta configuración:

```text
MySQL debe estar disponible para que los microservicios se conecten a sus bases de datos.
Eureka debe estar disponible para que los servicios se registren.
API Gateway utiliza Eureka para encontrar los microservicios mediante nombres lógicos.
```

---

## 11. Levantar el sistema completo

Ubicarse en la carpeta raíz del proyecto, donde está el archivo `docker-compose.yml`.

Ejemplo:

```bash
cd /d RUTA\gestion_inv
```

Luego ejecutar:

```bash
docker compose up -d --build
```

También se puede usar el script incluido:

```text
Iniciar.bat
```

El script realiza el siguiente flujo:

```text
1. Ejecuta mvn clean package -DskipTests.
2. Construye y levanta los contenedores con Docker Compose.
3. Espera unos segundos para permitir el arranque de los servicios.
4. Abre Eureka Server en el navegador.
```

---

## 12. Revisar el estado de los contenedores

Ejecutar:

```bash
docker ps
```

or:

```bash
docker compose ps
```

Resultado esperado:

```text
mysql-db              Up
logistica-eureka      Up
logistica-gateway     Up
logistica-producto    Up
logistica-stock       Up
logistica-movimiento  Up
logistica-anden       Up
logistica-camion      Up
logistica-recepcion   Up
logistica-factura     Up
logistica-desconsolidacion Up
logistica-warehouse   Up
```

Los nombres exactos pueden variar dependiendo de Docker Compose, pero todos los servicios principales deberían quedar en estado `Up` o `running`.

---

## 13. Levantar servicios de forma progresiva

Si se desea diagnosticar errores, se pueden levantar servicios por separado:

```bash
docker compose up -d mysql-db
docker compose up -d eureka-server
docker compose up -d camion
docker compose up -d anden
docker compose up -d warehouse
docker compose up -d recepcion
docker compose up -d factura
docker compose up -d desconsolidacion
docker compose up -d producto
docker compose up -d stock
docker compose up -d movimiento
docker compose up -d api-gateway
```

Después de cada servicio, revisar:

```bash
docker compose ps
```

y revisar logs del servicio levantado.

---

## 14. Revisar logs

Para revisar todos los logs:

```bash
docker compose logs -f
```

Para revisar un servicio específico:

```bash
docker compose logs -f eureka-server
docker compose logs -f api-gateway
docker compose logs -f producto
docker compose logs -f stock
docker compose logs -f movimiento
docker compose logs -f mysql-db
```

Para salir de la visualización de logs:

```text
Ctrl + C
```

Esto no detiene los contenedores, solo cierra la visualización de logs.

---

## 15. Accesos principales

Una vez levantado el sistema, se pueden revisar los siguientes accesos:

```text
Eureka Server:
http://localhost:8761
```

```text
API Gateway:
http://localhost:8080
```

```text
Swagger / OpenAPI centralizado:
http://localhost:8080/swagger-ui/index.html
```

```text
Actuator Health del API Gateway:
http://localhost:8080/actuator/health
```

Nota importante:

```text
Estos accesos funcionan mientras Docker esté corriendo y los contenedores estén activos.
Si Docker se detiene, el sistema deja de estar disponible.
```

---

## 16. Verificación en Eureka

Abrir en el navegador:

```text
http://localhost:8761
```

Se espera visualizar servicios registrados como:

```text
API-GATEWAY
ANDEN
CAMION
DESCONSOLIDACION
FACTURA
MOVIMIENTO
PRODUCTO
RECEPCION
STOCK
WAREHOUSE
```

Los nombres se originan desde la propiedad:

```yaml
spring:
  application:
    name: nombre-servicio
```

---

## 17. Swagger / OpenAPI

La documentación centralizada se puede revisar desde:

```text
http://localhost:8080/swagger-ui/index.html
```

Desde el selector de Swagger se puede consultar la documentación de los servicios expuestos, por ejemplo:

```text
/producto/v3/api-docs
/stock/v3/api-docs
/movimiento/v3/api-docs
/factura/v3/api-docs
/recepcion/v3/api-docs
```

Los controllers principales incluyen documentación con:

```text
@Tag
@Operation
@ApiResponses
@ApiResponse
```

Esto permite visualizar endpoints, descripciones y códigos de respuesta esperados.

---

## 18. Pruebas mediante API Gateway

Las rutas principales se prueban mediante el API Gateway en el puerto `8080`.

Ejemplos:

```text
http://localhost:8080/api/productos
http://localhost:8080/api/stock
http://localhost:8080/api/movimientos
http://localhost:8080/api/andenes
http://localhost:8080/api/camiones
http://localhost:8080/api/recepciones
http://localhost:8080/api/facturas
http://localhost:8080/api/desconsolidaciones
http://localhost:8080/api/warehouse
```

Rutas configuradas pero no incluidas en el Docker Compose principal validado:

```text
http://localhost:8080/api/empleados
http://localhost:8080/api/credenciales
```

---

## 19. Rutas principales del API Gateway

| Ruta Gateway | Servicio destino | Estado |
|---|---|---:|
| `/api/productos/**` | `lb://producto` | Listo |
| `/api/stock/**` | `lb://stock` | Listo |
| `/api/movimientos/**` | `lb://movimiento` | Listo |
| `/api/andenes/**` | `lb://anden` | Listo |
| `/api/camiones/**` | `lb://camion` | Listo |
| `/api/recepciones/**` | `lb://recepcion` | Listo |
| `/api/facturas/**` | `lb://factura` | Listo |
| `/api/desconsolidaciones/**` | `lb://desconsolidacion` | Listo |
| `/api/warehouse/**` | `lb://warehouse` | Listo |
| `/api/empleados/**` | `lb://creacion-usuario` | Configurado / fuera del Compose principal |
| `/api/credenciales/**` | `lb://autenticacion` | Configurado / parcial |

---

## 20. Verificación de bases de datos

Para verificar que las bases de datos fueron creadas dentro del contenedor MySQL:

```bash
docker exec -it mysql-db mysql -u root -proot -e "SHOW DATABASES;"
```

Resultado esperado:

```text
bd_camion
bd_productos
bd_facturas
bd_desconsolidaciones
bd_stock
bd_anden
bd_warehouses
bd_recepciones
bd_movimientos
bd_credenciales
bd_usuarios
bd_camiones
bd_movimiento
```

Para revisar tablas de una base específica:

```bash
docker exec -it mysql-db mysql -u root -proot -e "USE bd_productos; SHOW TABLES;"
```

---

## 21. Pruebas unitarias

El proyecto cuenta con pruebas unitarias y pruebas de controller en los microservicios principales.

Para ejecutar las pruebas:

```bash
mvn clean test
```

La ejecución valida el reactor Maven y los módulos principales del proyecto.

Se utilizan herramientas como:

```text
JUnit 5
Mockito
MockMvc
assertEquals
assertThrows
verify
when
```

Servicios con pruebas detectadas:

```text
Producto
Stock
Movimiento
Andén
Camión
Recepción
Factura
Desconsolidación
Warehouse
Eureka Server básico
```

---

## 22. Comandos principales

| Acción | Comando |
|---|---|
| Ver versión de Docker | `docker --version` |
| Ver versión de Docker Compose | `docker compose version` |
| Ver información de Docker | `docker info` |
| Compilar y probar | `mvn clean test` |
| Empaquetar sin pruebas | `mvn clean package -DskipTests` |
| Levantar todo el sistema | `docker compose up -d --build` |
| Levantar con script Windows | `Iniciar.bat` |
| Revisar contenedores | `docker ps` |
| Revisar contenedores del compose | `docker compose ps` |
| Ver todos los logs | `docker compose logs -f` |
| Ver logs de un servicio | `docker compose logs -f nombre-servicio` |
| Detener sistema | `docker compose down` |
| Detener con script Windows | `Cerrar.bat` |
| Detener y borrar volumen | `docker compose down -v` |
| Ver bases de datos | `docker exec -it mysql-db mysql -u root -proot -e "SHOW DATABASES;"` |

---

## 23. Detener el sistema

Para detener los contenedores sin eliminar los datos persistentes:

```bash
docker compose down
```

También se puede usar:

```text
Cerrar.bat
```

Este comando detiene y elimina los contenedores, pero mantiene el volumen de MySQL.

---

## 24. Detener y eliminar datos persistentes

Para detener el sistema y eliminar también el volumen de MySQL:

```bash
docker compose down -v
```

Advertencia:

```text
Este comando elimina los datos persistentes de MySQL.
Se debe usar solo cuando se quiera reiniciar la base de datos desde cero.
```

Para detener el sistema sin borrar datos, usar:

```bash
docker compose down
```

---

## 25. Persistencia de datos de MySQL

El sistema utiliza MySQL dentro de Docker con un volumen persistente:

```yaml
volumes:
  mysql_data:
```

Y en el servicio MySQL:

```yaml
volumes:
  - mysql_data:/var/lib/mysql
```

Esto permite que los datos se mantengan aunque los contenedores se detengan o se vuelvan a crear.

Los datos sí pueden perderse si se elimina el volumen con:

```bash
docker compose down -v
```

---

## 26. Acceso desde otros equipos de la red

Si se desea acceder desde otro computador de la misma red, no se debe usar `localhost`.

Se debe usar la IP del equipo donde corre Docker.

Ejemplo:

```text
http://192.168.1.50:8080
http://192.168.1.50:8761
```

Importante:

```text
El firewall de Windows debe permitir el acceso a los puertos utilizados.
La red debe permitir conexiones hacia el equipo que ejecuta Docker.
Docker Desktop debe permanecer iniciado.
Los contenedores deben estar en ejecución.
```

---

## 27. Uso de dominio

Si el sistema se publica usando un dominio, se deben reemplazar las direcciones locales por el dominio correspondiente.

Ejemplo:

```text
http://localhost:8080
```

podría cambiar a:

```text
https://api.royallogistics.cl
```

En ese caso, se deben revisar especialmente:

```text
1. Configuración DNS del dominio.
2. Puertos expuestos públicamente.
3. Configuración de firewall.
4. Configuración CORS.
5. API Gateway.
6. Certificados HTTPS.
7. Proxy inverso, si corresponde.
```

---

## 28. Errores comunes

### 28.1 Docker Desktop no está abierto

Mensaje posible:

```text
Cannot connect to the Docker daemon
```

Solución:

```text
Abrir Docker Desktop.
Esperar a que esté iniciado.
Volver a ejecutar Iniciar.bat o docker compose up -d --build.
```

---

### 28.2 Puerto ocupado

Mensaje posible:

```text
port is already allocated
```

Causa:

```text
Otro proceso está usando el puerto.
```

Solución:

```text
Cambiar el puerto externo en docker-compose.yml o cerrar el proceso que ocupa el puerto.
```

Ejemplo:

```yaml
ports:
  - "8086:8080"
```

Esto significa:

```text
Puerto 8086 del computador -> Puerto 8080 del contenedor
```

---

### 28.3 Microservicio no conecta a MySQL

Mensajes posibles:

```text
Communications link failure
Connection refused
Access denied for user
Unknown database
```

Revisar:

```text
Que mysql-db esté running.
Que la base de datos exista.
Que el usuario y contraseña sean root/root en Docker.
Que el microservicio use jdbc:mysql://mysql-db:3306/...
```

Dentro de Docker no se debe usar:

```text
localhost
```

Debe usarse:

```text
mysql-db
```

---

### 28.4 Microservicio no aparece en Eureka

Revisar que el microservicio tenga configurado dentro de Docker:

```text
EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/
```

Dentro de Docker no debe apuntar a:

```text
http://localhost:8761/eureka/
```

Debe apuntar a:

```text
http://eureka-server:8761/eureka/
```

---

### 28.5 API Gateway no enruta

Posibles causas:

```text
El microservicio destino no está registrado en Eureka.
El nombre del servicio no coincide con spring.application.name.
La ruta del Gateway está mal configurada.
El Gateway inició antes de que los microservicios estuvieran disponibles.
```

Solución:

```text
Verificar Eureka.
Revisar logs de api-gateway.
Revisar configuración del Gateway.
Reiniciar api-gateway si es necesario.
```

---

### 28.6 El archivo .jar no existe

Mensaje posible:

```text
Unable to access jarfile
```

Causa probable:

```text
No se ejecutó mvn clean package -DskipTests antes de construir la imagen.
```

Solución:

```bash
mvn clean package -DskipTests
docker compose up -d --build
```

O ejecutar:

```text
Iniciar.bat
```

---

### 28.7 MySQL no ejecuta `init.sql`

Causa probable:

```text
El volumen mysql_data ya existía.
```

El script `init.sql` solo se ejecuta automáticamente al crear el volumen por primera vez.

Solución si se desea recrear desde cero:

```bash
docker compose down -v
docker compose up -d --build
```

Advertencia:

```text
Esto elimina los datos persistentes de MySQL.
```

---

### 28.8 El sistema funciona localmente, pero otros usuarios no pueden entrar

Posibles causas:

```text
Los usuarios están usando localhost en sus computadores.
El firewall bloquea el acceso.
El computador donde corre Docker no está en la misma red.
Los puertos no están expuestos.
Docker no está corriendo.
El computador servidor está apagado.
```

Solución:

```text
Usar la IP del equipo donde corre Docker.
Revisar firewall.
Verificar docker compose ps.
Mantener Docker en ejecución.
```

---

## 29. Evidencia recomendada para revisión

Para demostrar que el sistema está funcionando con Docker, se recomienda guardar capturas de:

```text
1. docker --version.
2. docker compose version.
3. mvn clean test con BUILD SUCCESS.
4. docker ps o docker compose ps.
5. Eureka Server con servicios registrados.
6. Swagger UI desde API Gateway.
7. Endpoint funcionando mediante API Gateway.
8. Logs de un microservicio iniciado correctamente.
9. MySQL en ejecución.
10. SHOW DATABASES dentro del contenedor MySQL.
```

---

## 30. Estado final esperado

Al finalizar correctamente el proceso, se espera:

```text
MySQL ejecutándose en Docker.
Eureka Server disponible en http://localhost:8761.
Microservicios logísticos registrados en Eureka.
API Gateway disponible en http://localhost:8080.
Swagger disponible en http://localhost:8080/swagger-ui/index.html.
Endpoints principales disponibles mediante API Gateway.
Volumen de MySQL configurado para persistencia.
Carpeta docs/init.sql disponible para inicialización de bases.
```

---

## 31. Resumen importante

```text
Docker no reemplaza Spring Boot.
Docker entrega el entorno controlado donde Spring Boot se ejecuta.
El ZIP no es una imagen Docker completa.
Docker Compose configura y levanta el ecosistema.
Docker Desktop debe estar abierto antes de ejecutar el sistema.
Si Docker se detiene, el sistema deja de estar disponible.
Los .jar se generan con Maven dentro de las carpetas target/.
MySQL corre dentro de un contenedor Docker.
Dentro de Docker, los servicios deben usar mysql-db y eureka-server, no localhost.
Si se usa localhost, solo funciona en el equipo local.
Si se usa IP, puede funcionar dentro de la red.
Si se usa dominio, se deben ajustar DNS, CORS, Gateway y HTTPS.
Si se usa volumen Docker, los datos no deberían perderse al reiniciar.
Los datos sí pueden perderse si se elimina el volumen con docker compose down -v.
```

---

<h2 style="color:#2563eb;">📑 Documentación de endpoints principales</h2>

## Microservicio de Andenes

**Endpoint base:** `/api/andenes`

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/andenes` | Lista todos los andenes. |
| GET | `/api/andenes/{nAnden}` | Busca un andén por número. |
| POST | `/api/andenes` | Registra un nuevo andén. |

---

## Microservicio de Camiones

**Endpoint base:** `/api/camiones`

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/camiones` | Lista todos los camiones. |
| GET | `/api/camiones/patente/{patente}` | Busca un camión por patente. |
| POST | `/api/camiones` | Registra un nuevo camión. |
| DELETE | `/api/camiones/{patente}` | Elimina un camión por patente. |

---

## Microservicio de Recepciones

**Endpoint base:** `/api/recepciones`

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/recepciones` | Lista todas las recepciones. |
| GET | `/api/recepciones/id/{idRecepcion}` | Busca una recepción por ID. |
| POST | `/api/recepciones` | Registra una nueva recepción. |
| GET | `/api/recepciones/estado/{estado}` | Busca recepciones por estado. |
| GET | `/api/recepciones/patente/{patente}` | Busca recepciones por patente de camión. |
| GET | `/api/recepciones/anden/{nAnden}` | Busca recepciones por número de andén. |
| DELETE | `/api/recepciones/{id}` | Elimina una recepción por ID. |

---

## Microservicio de Facturas

**Endpoint base:** `/api/facturas`

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/facturas` | Lista todas las facturas. |
| GET | `/api/facturas/{numeroFactura}` | Busca una factura por número. |
| POST | `/api/facturas` | Registra una nueva factura. |
| GET | `/api/facturas/estado/{estado}` | Busca facturas por estado. |
| GET | `/api/facturas/proveedor/{proveedor}` | Busca facturas por proveedor. |
| GET | `/api/facturas/recepcion/{idRecepcion}` | Busca facturas por ID de recepción. |
| DELETE | `/api/facturas/{numeroFactura}` | Elimina una factura por número. |

---

## Microservicio de Desconsolidaciones

**Endpoint base:** `/api/desconsolidaciones`

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/desconsolidaciones` | Lista todas las desconsolidaciones. |
| GET | `/api/desconsolidaciones/{idDesconsolidacion}` | Busca una desconsolidación por ID. |
| POST | `/api/desconsolidaciones` | Registra una nueva desconsolidación. |
| GET | `/api/desconsolidaciones/factura/{numeroFactura}` | Busca desconsolidaciones por número de factura. |
| GET | `/api/desconsolidaciones/cantidad/{cantidadProductos}` | Busca desconsolidaciones por cantidad de productos. |
| DELETE | `/api/desconsolidaciones/{idDesconsolidacion}` | Elimina una desconsolidación por ID. |

---

## Microservicio de Productos

**Endpoint base:** `/api/productos`

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/productos` | Lista todos los productos. |
| GET | `/api/productos/sku/{sku}` | Busca un producto por SKU. |
| POST | `/api/productos` | Registra un nuevo producto. |
| GET | `/api/productos/categoria/{categoria}` | Busca productos por categoría. |
| GET | `/api/productos/desconsolidacion/{idDesconsolidacion}` | Busca productos por ID de desconsolidación. |
| GET | `/api/productos/nombre/{nombreProducto}` | Busca productos por nombre. |
| DELETE | `/api/productos/{sku}` | Elimina un producto por SKU. |

---

## Microservicio de Warehouse

**Endpoint base:** `/api/warehouse`

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/warehouse` | Lista todas las ubicaciones de bodega. |
| GET | `/api/warehouse/ubicacion/{idUbicacion}` | Busca una ubicación por ID. |
| POST | `/api/warehouse` | Registra una nueva ubicación. |
| GET | `/api/warehouse/pasillo/{pasillo}` | Busca ubicaciones por pasillo. |
| GET | `/api/warehouse/rack/{rack}` | Busca ubicaciones por rack. |
| GET | `/api/warehouse/nivel/{nivel}` | Busca ubicaciones por nivel. |
| DELETE | `/api/warehouse/{idUbicacion}` | Elimina una ubicación por ID. |

---

## Microservicio de Stock

**Endpoint base:** `/api/stock`

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/stock` | Lista todo el stock disponible. |
| POST | `/api/stock/inicializar` | Inicializa stock desde otro microservicio. |
| POST | `/api/stock` | Registra o actualiza stock. |
| GET | `/api/stock/producto/sku/{sku}` | Busca stock por SKU de producto. |
| GET | `/api/stock/ubicacion/{idUbicacion}` | Busca stock por ubicación. |
| PUT | `/api/stock/descontar` | Descuenta stock. |
| GET | `/api/stock/critico/{limite}` | Busca stock bajo un límite crítico. |

---

## Microservicio de Movimientos

**Endpoint base:** `/api/movimientos`

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/movimientos` | Lista todos los movimientos. |
| POST | `/api/movimientos` | Registra un nuevo movimiento. |
| GET | `/api/movimientos/sku/{sku}` | Busca movimientos por SKU. |
| GET | `/api/movimientos/ubicacion/{idUbicacion}` | Busca movimientos por ubicación. |
| GET | `/api/movimientos/tipo/{tipoMovimiento}` | Busca movimientos por tipo. |
| GET | `/api/movimientos/desconsolidacion/{idDesconsolidacion}` | Busca movimientos por ID de desconsolidación. |
| DELETE | `/api/movimientos/{idMovimiento}` | Elimina un movimiento por ID. |

---

<h2 style="color:#2563eb;">👨‍💻 Equipo</h2>

<ul>
<li>Javier Herrera</li>
<li>Acxel González</li>
</ul>

<p>
<b>Profesor:</b><br>
Ricardo Mauricio González Vejar
</p>

---

<h2 style="color:#2563eb;">📌 Consideraciones del proyecto</h2>

<p>
El presente proyecto fue desarrollado con enfoque académico para la asignatura Desarrollo Fullstack I de Duoc UC.
</p>

<p>
Su objetivo consiste en representar procesos reales utilizados dentro de operaciones fulfillment y administración de warehouse modernas, utilizando arquitectura de microservicios para mantener una solución modular, escalable y desacoplada.
</p>

<p>
El sistema integra conceptos de logística, trazabilidad, control de inventario, despliegue con Docker, documentación OpenAPI, pruebas unitarias y arquitectura distribuida con Spring Boot, Eureka Server y API Gateway.
</p>
