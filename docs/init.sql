-- Bases usadas por los microservicios logísticos en Docker Compose
CREATE DATABASE IF NOT EXISTS bd_camion CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_productos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_facturas CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_desconsolidaciones CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_stock CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_anden CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_warehouses CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_recepciones CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_movimientos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bases de servicios complementarios definidos en application.yml
CREATE DATABASE IF NOT EXISTS bd_credenciales CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_usuarios CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bases alternativas detectadas en application.yml local.
-- Se incluyen para evitar inconsistencias entre ejecución local y Docker.
CREATE DATABASE IF NOT EXISTS bd_camiones CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_movimiento CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Permisos para root desde cualquier host dentro de la red Docker.
-- En este proyecto los microservicios usan root/root en Docker Compose.
GRANT ALL PRIVILEGES ON bd_camion.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON bd_productos.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON bd_facturas.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON bd_desconsolidaciones.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON bd_stock.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON bd_anden.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON bd_warehouses.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON bd_recepciones.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON bd_movimientos.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON bd_credenciales.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON bd_usuarios.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON bd_camiones.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON bd_movimiento.* TO 'root'@'%';

FLUSH PRIVILEGES;
