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

<h2 style="color:#2563eb;">📊 Estado del proyecto</h2>

<h3>Microservicios terminados</h3>

<ul>
<li>Eureka Server</li>
<li>Autenticación</li>
<li>Usuarios</li>
<li>Andén</li>
<li>Camión</li>
<li>Recepción</li>
<li>Factura</li>
<li>Desconsolidación</li>
<li>Producto</li>
<li>Warehouse</li>
<li>Movimiento</li>
<li>Stock</li>
</ul>

<h3>Arquitectura pendiente</h3>

<ul>
<li>API Gateway</li>
<li>MySQL Server centralizado</li>
<li>Eureka completo con integración total</li>
</ul>

---

<h2 style="color:#2563eb;">📑 Documentación de Endpoints y Colección</h2>

<p>
En esta sección se documentan los endpoints principales de cada microservicio y se incluye un ejemplo JSON del modelo utilizado. Los ejemplos pueden utilizarse como referencia para probar las rutas desde Postman.
</p>

---

<h3>Microservicio de Autenticación</h3>

**Base URL:** `/api/credenciales`

> Este controlador expone la ruta base del microservicio. Actualmente no define métodos públicos adicionales en el controller.

<details>
<summary><b>▶ Ver JSON del modelo Credencial</b></summary>

```json
{
  "id": 1,
  "usuario": "admin",
  "password": "123456",
  "is_active": true
}
```

</details>

---

<h3>Microservicio de Empleados / Usuarios</h3>

**Base URL:** `/api/empleados`

| Método | Endpoint | Descripción |
|---|---|---|
| **GET** | `/api/empleados` | Lista todos los empleados registrados. |
| **POST** | `/api/empleados` | Registra un nuevo empleado. |
| **GET** | `/api/empleados/activos` | Lista los empleados activos. |
| **GET** | `/api/empleados/rol/{rol}` | Busca empleados por rol. |
| **DELETE** | `/api/empleados/{id}` | Elimina un empleado por ID. |

<details>
<summary><b>▶ Ver JSON del modelo Empleado</b></summary>

```json
{
  "id": 1,
  "nombre": "Juan Pérez",
  "rut": "12345678-9",
  "correo": "juan.perez@royallogistics.cl",
  "cargo": "Operario de bodega",
  "rol": "OPERARIO",
  "turno": "Mañana",
  "activo": true
}
```

</details>

---

<h3>Microservicio de Andenes</h3>

**Base URL:** `/api/andenes`

| Método | Endpoint | Descripción |
|---|---|---|
| **GET** | `/api/andenes` | Lista todos los andenes. |
| **GET** | `/api/andenes/{nAnden}` | Busca un andén por número. |
| **POST** | `/api/andenes` | Registra un nuevo andén. |

<details>
<summary><b>▶ Ver JSON del modelo Anden</b></summary>

```json
{
  "numeroanden": 1,
  "estado": "Disponible"
}
```

</details>

---

<h3>Microservicio de Camiones</h3>

**Base URL:** `/api/camiones`

| Método | Endpoint | Descripción |
|---|---|---|
| **GET** | `/api/camiones` | Lista todos los camiones. |
| **GET** | `/api/camiones/patente/{patente}` | Busca un camión por patente. |
| **POST** | `/api/camiones` | Registra un nuevo camión. |
| **DELETE** | `/api/camiones/{patente}` | Elimina un camión por patente. |

<details>
<summary><b>▶ Ver JSON del modelo Camion</b></summary>

```json
{
  "patente": "ABCD12",
  "nombreConductor": "Carlos González",
  "rutConductor": "11222333-4"
}
```

</details>

---

<h3>Microservicio de Recepciones</h3>

**Base URL:** `/api/recepciones`

| Método | Endpoint | Descripción |
|---|---|---|
| **GET** | `/api/recepciones` | Lista todas las recepciones. |
| **GET** | `/api/recepciones/id/{idRecepcion}` | Busca una recepción por ID. |
| **POST** | `/api/recepciones` | Registra una nueva recepción. |
| **GET** | `/api/recepciones/estado/{estado}` | Busca recepciones por estado. |
| **GET** | `/api/recepciones/patente/{patente}` | Busca recepciones por patente de camión. |
| **GET** | `/api/recepciones/anden/{nAnden}` | Busca recepciones por número de andén. |
| **DELETE** | `/api/recepciones/{id}` | Elimina una recepción por ID. |

<details>
<summary><b>▶ Ver JSON del modelo Recepcion</b></summary>

```json
{
  "idRecepcion": 1,
  "numeroAnden": 1,
  "patente": "ABCD12",
  "fechaHoraRecepcion": "2026-06-03T10:30:00",
  "estado": "RECIBIDA"
}
```

</details>

---

<h3>Microservicio de Facturas</h3>

**Base URL:** `/api/facturas`

| Método | Endpoint | Descripción |
|---|---|---|
| **GET** | `/api/facturas` | Lista todas las facturas. |
| **GET** | `/api/facturas/{numeroFactura}` | Busca una factura por número. |
| **POST** | `/api/facturas` | Registra una nueva factura. |
| **GET** | `/api/facturas/estado/{estado}` | Busca facturas por estado. |
| **GET** | `/api/facturas/proveedor/{proveedor}` | Busca facturas por proveedor. |
| **GET** | `/api/facturas/recepcion/{idRecepcion}` | Busca facturas por ID de recepción. |
| **DELETE** | `/api/facturas/{nFactura}` | Elimina una factura por número. |

<details>
<summary><b>▶ Ver JSON del modelo Factura</b></summary>

```json
{
  "numeroFactura": "FAC00001",
  "idRecepcion": 1,
  "proveedor": "Proveedor Central",
  "fechaFactura": "2026-06-03",
  "cantidadCajas": 25,
  "estado": "REGISTRADA"
}
```

</details>

---

<h3>Microservicio de Desconsolidaciones</h3>

**Base URL:** `/api/desconsolidaciones`

| Método | Endpoint | Descripción |
|---|---|---|
| **GET** | `/api/desconsolidaciones` | Lista todas las desconsolidaciones. |
| **GET** | `/api/desconsolidaciones/{idDesconsolidacion}` | Busca una desconsolidación por ID. |
| **POST** | `/api/desconsolidaciones` | Registra una nueva desconsolidación. |
| **GET** | `/api/desconsolidaciones/factura/{numeroFactura}` | Busca desconsolidaciones por número de factura. |
| **GET** | `/api/desconsolidaciones/cantidad/{cantidadProductos}` | Busca desconsolidaciones por cantidad de productos. |
| **DELETE** | `/api/desconsolidaciones/{idDesconsolidacion}` | Elimina una desconsolidación por ID. |

<details>
<summary><b>▶ Ver JSON del modelo Desconsolidacion</b></summary>

```json
{
  "idDesconsolidacion": 1,
  "numeroFactura": "FAC00001",
  "cantidadProductos": 50
}
```

</details>

---

<h3>Microservicio de Productos</h3>

**Base URL:** `/api/productos`

| Método | Endpoint | Descripción |
|---|---|---|
| **GET** | `/api/productos` | Lista todos los productos disponibles. |
| **GET** | `/api/productos/sku/{sku}` | Busca un producto por SKU. |
| **POST** | `/api/productos` | Registra un nuevo producto. |
| **GET** | `/api/productos/categoria/{categoria}` | Busca productos por categoría. |
| **GET** | `/api/productos/desconsolidacion/{idDesconsolidacion}` | Busca productos por ID de desconsolidación. |
| **GET** | `/api/productos/nombre/{nombreProducto}` | Busca productos por nombre. |
| **DELETE** | `/api/productos/{sku}` | Elimina un producto por SKU. |

<details>
<summary><b>▶ Ver JSON del modelo Producto</b></summary>

```json
{
  "sku": "PROD-10023",
  "idDesconsolidacion": 1,
  "nombreProducto": "Pallet Madera Premium",
  "categoria": "Almacenamiento",
  "fecFabricacion": "2026-05-20",
  "fecCaducidad": "2028-05-20"
}
```

</details>

---

<h3>Microservicio de Warehouse</h3>

**Base URL:** `/api/warehouse`

| Método | Endpoint | Descripción |
|---|---|---|
| **GET** | `/api/warehouse` | Lista todas las ubicaciones de bodega. |
| **GET** | `/api/warehouse/ubicacion/{idUbicacion}` | Busca una ubicación por ID. |
| **POST** | `/api/warehouse` | Registra una nueva ubicación. |
| **GET** | `/api/warehouse/pasillo/{pasillo}` | Busca ubicaciones por pasillo. |
| **GET** | `/api/warehouse/rack/{rack}` | Busca ubicaciones por rack. |
| **GET** | `/api/warehouse/nivel/{nivel}` | Busca ubicaciones por nivel. |
| **DELETE** | `/api/warehouse/{idUbicacion}` | Elimina una ubicación por ID. |

<details>
<summary><b>▶ Ver JSON del modelo Warehouse</b></summary>

```json
{
  "idUbicacion": "U001",
  "pasillo": "P01",
  "rack": "R01",
  "nivel": 1
}
```

</details>

---

<h3>Microservicio de Stock</h3>

**Base URL:** `/api/stock`

| Método | Endpoint | Descripción |
|---|---|---|
| **GET** | `/api/stock` | Lista todo el stock disponible. |
| **POST** | `/api/stock/inicializar` | Inicializa stock desde otro microservicio. |
| **POST** | `/api/stock` | Registra o actualiza stock. |
| **GET** | `/api/stock/producto/sku/{sku}` | Busca stock por SKU de producto. |
| **GET** | `/api/stock/ubicacion/{idUbicacion}` | Busca stock por ubicación. |

<details>
<summary><b>▶ Ver JSON del modelo Stock</b></summary>

```json
{
  "idStock": 1,
  "idUbicacion": "U001",
  "sku": "PROD-10023",
  "cantDisponibles": 100
}
```

</details>

---

<h3>Microservicio de Movimientos</h3>

**Base URL:** `/api/movimientos`

| Método | Endpoint | Descripción |
|---|---|---|
| **GET** | `/api/movimientos` | Lista todos los movimientos. |
| **POST** | `/api/movimientos` | Registra un nuevo movimiento. |
| **GET** | `/api/movimientos/sku/{sku}` | Busca movimientos por SKU. |
| **GET** | `/api/movimientos/ubicacion/{idUbicacion}` | Busca movimientos por ubicación. |
| **GET** | `/api/movimientos/tipo/{tipoMovimiento}` | Busca movimientos por tipo. |
| **GET** | `/api/movimientos/desconsolidacion/{idDesconsolidacion}` | Busca movimientos por ID de desconsolidación. |
| **DELETE** | `/api/movimientos/{idMovimiento}` | Elimina un movimiento por ID. |

<details>
<summary><b>▶ Ver JSON del modelo Movimiento</b></summary>

```json
{
  "idMovimiento": 1,
  "idDesconsolidacion": 1,
  "sku": "PROD-10023",
  "idUbicacion": "U001",
  "tipoMovimiento": "INGRESO",
  "cantidad": 50,
  "destino": "Warehouse"
}
```

</details>

---

<h3>Colección de Postman</h3>

<p>
Para probar los microservicios, crear una colección en Postman separando las carpetas por módulo:
</p>

<ul>
<li>Autenticación</li>
<li>Empleados / Usuarios</li>
<li>Andenes</li>
<li>Camiones</li>
<li>Recepciones</li>
<li>Facturas</li>
<li>Desconsolidaciones</li>
<li>Productos</li>
<li>Warehouse</li>
<li>Stock</li>
<li>Movimientos</li>
</ul>

<p>
Cada request debe utilizar el método HTTP correspondiente, la URL del endpoint y, cuando aplique, el JSON del modelo mostrado anteriormente en el body con formato <code>raw</code> y tipo <code>JSON</code>.
</p>

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
Su objetivo consiste en representar procesos reales utilizados actualmente dentro de operaciones fulfillment y administración de warehouse modernas, utilizando arquitectura de microservicios para mantener una soluci贸n modular, escalable y desacoplada.
</p>

<p>
El sistema busca integrar conceptos de logística, trazabilidad, control de inventario y desarrollo backend utilizando tecnologías como Spring Boot, APIs REST y modelado de bases de datos relacionales.
</p>
