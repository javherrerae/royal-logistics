@echo off
color 0C
echo ========================================================
echo   Apagando el Sistema de Logistica
echo ========================================================
echo.

call docker-compose down

echo.
echo [EXITO] Contenedores apagados y red eliminada.
pause