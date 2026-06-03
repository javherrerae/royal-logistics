<div align="center">

<h1 style="color:#2563eb;">
Royal Logistics
</h1>

<h3>
Sistema de gesti贸n log铆stica basado en arquitectura de microservicios
</h3>

</div>

---

<h2 style="color:#2563eb;">馃摝 Descripci贸n</h2>

<p>
Sistema de gesti贸n log铆stica basado en arquitectura de microservicios, orientado a operaciones Fulfillment, administraci贸n de Warehouse y control de inventario dentro de una cadena log铆stica moderna.
</p>

<p>
El proyecto busca simular el funcionamiento de un centro de distribuci贸n similar a operaciones utilizadas actualmente por empresas fulfillment, donde distintos vendedores almacenan productos dentro de una misma red log铆stica, manteniendo control, seguimiento y trazabilidad sobre cada operaci贸n realizada.
</p>

---

<h2 style="color:#2563eb;">馃幆 Objetivo del proyecto</h2>

<p>
El objetivo principal del sistema consiste en desarrollar una plataforma log铆stica modular capaz de administrar procesos de:
</p>

<ul>
<li>recepci贸n de mercader铆a</li>
<li>ingreso de facturas</li>
<li>desconsolidaci贸n de productos</li>
<li>almacenamiento en warehouse</li>
<li>control de stock</li>
<li>movimientos internos</li>
<li>seguimiento operacional</li>
</ul>

<p>
Todo esto mediante arquitectura de microservicios utilizando Spring Boot y APIs REST.
</p>

---

<h2 style="color:#2563eb;">馃搷 Flujo log铆stico general</h2>

<pre>
Agendamiento de recepci贸n
        鈫?Llegada de cami贸n
        鈫?Asignaci贸n de and茅n
        鈫?Recepci贸n de factura
        鈫?Descarga de pallets / cajas
        鈫?Desconsolidaci贸n
        鈫?Registro de productos
        鈫?Asignaci贸n de ubicaci贸n
        鈫?Warehouse
        鈫?Actualizaci贸n de stock
        鈫?Movimientos internos
        鈫?Seguimiento y trazabilidad
</pre>

---

<h2 style="color:#2563eb;">馃彈锔?Arquitectura de microservicios</h2>

<p>
El sistema fue dise帽ado bajo arquitectura de microservicios, donde cada m贸dulo cumple una responsabilidad espec铆fica dentro de la cadena log铆stica.
</p>

<p>
Cada microservicio posee:
</p>

<ul>
<li>l贸gica independiente</li>
<li>endpoints REST</li>
<li>base de datos desacoplada</li>
<li>responsabilidades separadas</li>
</ul>

---

<h2 style="color:#2563eb;">鈿欙笍 Tecnolog铆as utilizadas</h2>

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

<h2 style="color:#2563eb;">馃梽锔?Modelado de base de datos</h2>

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
El modelado fue desarrollado previamente para mantener coherencia entre el flujo log铆stico y la arquitectura de microservicios.
</p>

---

<h2 style="color:#2563eb;">猸?Caracter铆sticas principales del sistema</h2>

<ul>
<li>Arquitectura desacoplada</li>
<li>Separaci贸n de responsabilidades</li>
<li>Gesti贸n de Warehouse</li>
<li>Control de inventario</li>
<li>Seguimiento operacional</li>
<li>Gesti贸n de ubicaciones</li>
<li>Registro de recepciones</li>
<li>Administraci贸n de productos</li>
<li>Escalabilidad modular</li>
<li>Trazabilidad log铆stica</li>
</ul>

---

<h2 style="color:#2563eb;">馃搳 Estado del proyecto</h2>

<h3>Microservicios terminados</h3>

<ul>
<li>Eureka Server</li>
<li>Autenticaci贸n</li>
<li>Usuarios</li>
<li>And茅n</li>
<li>Cami贸n</li>
<li>Recepci贸n</li>
<li>Factura</li>
<li>Desconsolidaci贸n</li>
<li>Producto</li>
<li>Warehouse</li>
<li>Movimiento</li>
<li>Stock</li>

</ul>


<h3>Arquitectura pendiente</h3>

<ul>
<li>API Gateway</li>
<li>MySQL Server centralizado</li>
<li>Eureka completo con integraci贸n total</li>
</ul>

---
<h2 style="color:#2563eb;">鈿欙笍 Documentación y Colección</h2>
### Microservicio de Productos

** GET ** `/api/productos`: Lista todos los productos disponibles

<details>
    <summary><b>? Ver JSON</b></summary>

    ```json
    [
      {
        "id": 1,
        "sku": "PROD-10023",
        "nombreProducto": "Pallet Madera Premium",
        "categoria": "Almacenamiento",
        "idDesconsolidacion": 12
      }
    ]
    ```

  </details>
---

** GET ** `/api/productos/sku/{sku}`: Busca productos por código de SKU único

<details>
      <summary><b>? Ver JSON</b></summary>

```json
      {
        "id": 1,
        "sku": "PROD-10023",
        "nombreProducto": "Pallet Madera Premium",
        "categoria": "Almacenamiento",
        "idDesconsolidacion": 12
      }
      ```
    </details>
---

** POST ** `/api/productos`: Registrar un nuevo producto

<details>
    <summary><b>? Ver JSON</b></summary>

```json
    {
      "sku": "PROD-10024",
      "nombreProducto": "Caja Cartón Corrugado",
      "categoria": "Embalaje",
      "idDesconsolidacion": 15
    }
    ```
  </details>
---
** GET ** `/api/productos/categoria/{categoria}`: Buscar producto por categoría

** GET ** `/api/productos/desconsolidacion/{idDesconsolidacion}`: Buscar producto por id de desconsolidación

** GET ** `/api/productos/nombre/{nombreProducto}`: Buscar producto por nombre

** DELETE ** `/api/productos/{sku}`: Elimina un producto filtrado por el código sku


---

<h2 style="color:#2563eb;">馃懆鈥嶐煉?Equipo</h2>

<ul>
<li>Javier Herrera</li>
<li>Acxel Gonz谩lez</li>
</ul>

<p>
<b>Profesor:</b><br>
Ricardo Mauricio Gonz谩lez Vejar
</p>

---

<h2 style="color:#2563eb;">馃摌 Consideraciones del proyecto</h2>

<p>
El presente proyecto fue desarrollado con enfoque acad茅mico para la asignatura Desarrollo Fullstack I de Duoc UC.
</p>

<p>
Su objetivo consiste en representar procesos reales utilizados actualmente dentro de operaciones fulfillment y administraci贸n de warehouse modernas, utilizando arquitectura de microservicios para mantener una soluci贸n modular, escalable y desacoplada.
</p>

<p>
El sistema busca integrar conceptos de log铆stica, trazabilidad, control de inventario y desarrollo backend utilizando tecnolog铆as como Spring Boot, APIs REST y modelado de bases de datos relacionales.
</p>
