<div align="center">

<h1 style="color:#2563eb;">
Sistema logístico
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
El modelado fue desarrollado previamente para mantener coherencia entre el flujo logístico y la arquitectura de microservicios implementada posteriormente.
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
<li>Stock</li>
</ul>

<h3>En proceso</h3>

<ul>
<li>Movimiento</li>
</ul>

<h3>Arquitectura pendiente</h3>

<ul>
<li>API Gateway</li>
<li>MySQL Server centralizado</li>
<li>Eureka completo con integración total</li>
</ul>

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

<h2 style="color:#2563eb;">📘 Consideraciones del proyecto</h2>

<p>
El presente proyecto fue desarrollado con enfoque académico para la asignatura Desarrollo Fullstack I de Duoc UC.
</p>

<p>
Su objetivo consiste en representar procesos reales utilizados actualmente dentro de operaciones fulfillment y administración de warehouse modernas, utilizando arquitectura de microservicios para mantener una solución modular, escalable y desacoplada.
</p>

<p>
El sistema busca integrar conceptos de logística, trazabilidad, control de inventario y desarrollo backend utilizando tecnologías como Spring Boot, APIs REST y modelado de bases de datos relacionales.
</p>
