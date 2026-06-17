<div align="center">

<h1 style="color:#2563eb;">
📑 CHANGELOG
</h1>

<h3>
Historial de actualizaciones del proyecto Royal Logistics
</h3>

</div>
<h2 style="color:#2563eb;">Versión 0.7 — 16/06/2026</h2>

<ul>
<li>Documentación Swagger</li>
    <li>Se comenta cada End-Point por Micro-servicios</li>
    <li>Se corrigen relaciones entre End-Points e interfaz de Swagger</li>
</ul>


<h2 style="color:#2563eb;">Versión 0.6 — 13/06/2026</h2>

<ul>
<li>Finalización e implementación completa de API Gateway</li>
</ul>

<ul>
<li>Creación POM parent</li>
</ul>

<ul>
<li>Documentación Swagger</li>
    <li>- Swagger individual por microservicio</li>
    <li>- Swagger general por puerto gateway: http://localhost:8080/swagger-ui/index.html</li>
</ul>

<h2 style="color:#2563eb;">Versión 0.5 — 18/05/2026</h2>

<h3>Nuevas funcionalidades</h3>

<ul>
<li>Finalización del microservicio Movimiento</li>
</ul>

<h3>Documentación y presentación</h3>

<ul>
<li>Creación de presentación del proyecto en formato PPT</li>
<li>Actualización de material de apoyo para la exposición final</li>
</ul>


---

<h2 style="color:#2563eb;">Versión 0.4.5 — 17/05/2026</h2>

<h3>Estado general</h3>

<p>
El sistema se encuentra funcional en su estado base. Se esperan futuras integraciones de nuevos microservicios, principalmente el microservicio de movimiento y API Gateway, junto con mejoras relacionadas a autenticación y gestión de usuarios.
</p>

<h3>Integración de microservicios</h3>

<p>
Tras aplicar esta actualización, se ejecutaron correctamente las pruebas de integración en Postman, permitiendo validación de información entre microservicios mediante consultas a bases de datos externas a las propias de cada servicio.
</p>

<p>
Además, se logró la inicialización reactiva y en cascada del registro de inventario dentro del microservicio de stock desacoplado.
</p>

<h3>Nuevas funcionalidades</h3>

<ul>
<li>Actualización de los microservicios Producto, Andén, Camión, Recepción, Factura, Desconsolidación y Warehouse</li>
<li>Finalización de integración entre microservicios utilizando FeignClient</li>
<li>Validación cruzada de entidades entre distintos servicios</li>
<li>Conexión lógica del flujo operacional fulfillment</li>
</ul>

<h3>Corrección de errores</h3>

<ul>
<li>Downgrade de Java 25 a Java 21 en los archivos pom.xml de Camión, Creación-Usuario, Recepción, Factura, Desconsolidación y Warehouse</li>
<li>Corrección de anotación @Service faltante en AndenService</li>
<li>Corrección de errores generales relacionados con JavaBeans y discrepancias entre mayúsculas/minúsculas en JPA</li>
</ul>

---

<h2 style="color:#2563eb;">Versión 0.3.6 — 16/05/2026</h2>

<h3>Nuevas funcionalidades</h3>

<ul>
<li>Actualización del microservicio Producto</li>
<li>Finalización del microservicio Desconsolidación</li>
<li>Finalización del microservicio Warehouse</li>
</ul>

<h3>Documentación e ingeniería</h3>

<ul>
<li>Desarrollo del PDF de Ingeniería y Flujo Logístico</li>
<li>Explicación teórica del proyecto</li>
<li>Análisis operacional fulfillment</li>
<li>Justificación de arquitectura de microservicios</li>
<li>Desarrollo inicial de presentación del proyecto</li>
</ul>

---

<h2 style="color:#2563eb;">Versión 0.3.5 — 14/05/2026</h2>

<h3>Nuevas funcionalidades</h3>

<ul>
<li>Finalización del microservicio Camión</li>
<li>Finalización del microservicio Recepción</li>
<li>Finalización del microservicio Factura</li>
</ul>

---

<h2 style="color:#2563eb;">Versión 0.3.4 — 14/05/2026</h2>

<h3>Base de datos</h3>

<ul>
<li>Corrección y carga final del modelado relacional</li>
<li>Diseño completo de entidades en Oracle SQL Developer Data Modeler</li>
</ul>

---

<h2 style="color:#2563eb;">Versión 0.3.3 — 13/05/2026</h2>

<h3>Nuevas funcionalidades</h3>

<ul>
<li>Finalización del microservicio Andén</li>
<li>Finalización del modelado relacional de base de datos</li>
</ul>

---

<h2 style="color:#2563eb;">Versión 0.3.2 — 11/05/2026</h2>

<h3>Nuevas funcionalidades</h3>

<ul>
<li>Finalización del microservicio Producto</li>
<li>Finalización del microservicio Creación-Usuario</li>
</ul>

---

<h2 style="color:#2563eb;">Versión 0.3.1 — 06/05/2026</h2>

<h3>Base de datos</h3>

<ul>
<li>Inicio de modelado relacional utilizando Oracle SQL Developer Data Modeler</li>
</ul>

---

<h2 style="color:#2563eb;">Versión 0.3 — 04/05/2026</h2>

<h3>Nuevas funcionalidades</h3>

<ul>
<li>Finalización del microservicio Autenticación</li>
</ul>

---

<h2 style="color:#2563eb;">Versión 0.1 — 01/05/2026</h2>

<h3>Nuevas funcionalidades</h3>

<ul>
<li>Inicialización y configuración de Eureka Server</li>
<li>Inicio de arquitectura de microservicios</li>
</ul>
