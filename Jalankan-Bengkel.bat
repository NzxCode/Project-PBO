@echo off
cd /d "%~dp0"
if not exist bin mkdir bin
javac -encoding UTF-8 -d bin src\com\bengkel\*.java
if errorlevel 1 (
    echo.
    echo Gagal compile. Pastikan JDK sudah terpasang.
    pause
    exit /b 1
)
java -cp bin com.bengkel.App
pause
