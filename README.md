                                        Sistema de gestión de inventario y logística.

Nota Acxel: Quizás sea buena idea crear un archivo con Changelog para dejar el Readme limpio

---------------------- CHANGELOG -------------------------
[17-05-2027] Actualización v1.0:
El sistema se encuentra funcional en su estado base. Se esperan futuras integraciones de más microservicios (principalmente el microservicio de movimiento y el de api-gateway), junto con la finalización de la gestión de usuarios y autenticación.

Nota de integración: Tras aplicar esta actualización, se ejecutaron con éxito el flujo de pruebas en Postman, logrando que los microservicios validen información en bases de datos externas a las suyas. Además de lograr la inicialización reactiva y en cascada del registro de cero unidades dentro del inventario aislado de stock.

Nuevo:
- Actualización de microservicios producto, anden, camion, recepcion, factura, desconsolidacion y warehouse.
- Finalización de integración de inter-conexión de microservicios mediante FeignClient.

Corrección de errores:
- Downgrade de la propiedad 'java.version' de Java 25 a Java 21 en pom.xml de camion, creacion-usuario, recepcion, factura,   desconsolidacion y warehouse.
- Se añadió la notacion @Service omitida en la clase AndenService.
- Fix de bugs generales de JavaBeans (Discrepancia entre mayúsculas y minúsculas en JPA).




[16-05-2026] Actualización v0.3.6:
- Actualización del microservicio `Producto`:
- Finalización del microservicio `Desconsolidacion`
- Finalización del microservicio `Warehouse`
- PDF Ingenieria: Sistema y flujo logístico:
  - Informe y eplicación de Ingeniería de proyecto:
    - Explicación teórica, comprensión operacional, análisis de flujo logístico, justificación de microservicios  
- Avance presentación: (Pendiente de subir)
  - Presentación proyecto
  - Flujo logístico
  - Ingeniería *PDF*
  - Arquitectura

[14-05-2026] Actualización v0.3.5:
- Finalización del microservicio `Camion`
- Finalización del microservicio `Recepcion`
- Finalización del microservicio `Factura`

[14-05-2026] Actualización v0.3.4:
- Corrección y carga final modelado de Base de Datos: Diseño del modelo relacional en Oracle SQL Developer Data Modeler

[13-05-2026] Actualización v0.3.3:
- Finalización del Microservicio `Anden`
- Finalización de modelado de Base de Datos: Diseño del modelo relacional en Oracle SQL Developer Data Modeler

[11-05-2026] Actualización v0.3.2:
- Finalización del microservicio `Producto`
- Finalización del microservicio `Creacion-usuario`

[06-05-2026] Actualización v0.3.1
- Inicio de modelado de Base de datos: Diseño del modelo relacional en Oracle SQL Developer Data Modeler

[04-05-2026] Actualización v0.3
- Finalización del microservicio `Autenticación`

[01-05-2026] Actualización v0.3
- Finalización del microservicio `Eureka-Server`


---------------------------------------------------------------------------------------------------------
<div align="center">

<h1 style="color:#2563eb;">
🚛 SmartWarehouse Logistics System
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
El sistema fue modelado previamente utilizando Oracle SQL Data Modeler, diseñando entidades relacionadas con:
</p>

<ul>
<li>usuarios</li>
<li>recepción</li>
<li>productos</li>
<li>warehouse</li>
<li>stock</li>
<li>movimientos</li>
<li>facturas</li>
<li>desconsolidación</li>
<li>camiones</li>
<li>andenes</li>
</ul>

<p>
El objetivo del modelado fue mantener coherencia entre la lógica logística y la arquitectura de microservicios implementada posteriormente.
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
</ul>

<h3>En proceso</h3>

<ul>
<li>Stock</li>
<li>Movimiento</li>
</ul>

Arquitectura pendiente:
_ Gateway
_ Mysql
_ Eureka

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
