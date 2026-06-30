@echo off
color 0A
echo ========================================================
echo   Iniciando el Sistema de Logistica (Microservicios)
echo ========================================================
echo.

echo [1/3] Empaquetando el codigo Java con Maven...
call mvn clean package -DskipTests
if %errorlevel% neq 0 (
    echo [ERROR] Fallo la compilacion de Maven. Revisa el codigo.
    pause
    exit /b %errorlevel%
)

echo.
echo [2/3] Levantando contenedores e infraestructura en Docker...
call docker-compose up -d --build

echo.
echo [3/3] Esperando a que los servicios arranquen...
:: Espera 15 segundos para darle tiempo a Eureka a encender
timeout /t 15 /nobreak > NUL

echo.
echo [EXITO] ¡Ecosistema levantado! Abriendo el directorio Eureka...
start http://localhost:8761

echo.
pause