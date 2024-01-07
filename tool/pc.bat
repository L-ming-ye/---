@echo off
setlocal enabledelayedexpansion

set frpcPath=%~dp0\frp\3389
set frpcConfig=frpc.ini
set nircmdPath=%~dp0nircmd.exe

if "%~1"=="" (
    goto :menu
) else if "%~1"=="/?" (
    call :showHelp
    goto :end
) else if "%~1"=="/help" (
    call :showHelp
    goto :end
) else if "%~1"=="/check" (
    call :check
    goto :end
) else if "%~1"=="/start" (
    call :startSilent
    goto :end
) else if "%~1"=="/shutdown" (
    call :shutdown
    goto :end
) else (
    echo 请输入有效的命令！
    goto :end
)

:menu
echo 请选择操作：
echo 1. 检查 frpc 是否运行
echo 2. 后台运行 frpc
echo 3. 关闭 frpc
echo 4. 退出

set /p choice=请输入操作编号：

if "%choice%"=="1" (
    call :check
    goto :menu
) else if "%choice%"=="2" (
    call :startSilent
    goto :menu
) else if "%choice%"=="3" (
    call :shutdown
    goto :menu
) else if "%choice%"=="4" (
    goto :end
) else (
    echo 请输入有效的操作编号！
    goto :menu
)

:check
cd /d %frpcPath%
tasklist /FI "IMAGENAME eq pc.exe" /NH | find /i "pc.exe" >nul
if not errorlevel 1 (
    echo 正在运行
) else (
    echo 未运行
)

pause
goto :eof

:startSilent
cd /d %frpcPath%
tasklist /FI "IMAGENAME eq pc.exe" /NH | findstr /i "pc.exe" >nul
if not errorlevel 1 (
    echo 已经在运行，无需再次启动
    goto :eof
)

"%nircmdPath%" exec hide pc.exe -c %frpcConfig%

timeout /t 3 >nul

tasklist /FI "IMAGENAME eq pc.exe" /NH | findstr /i "pc.exe" >nul
if not errorlevel 1 (
    echo 启动成功
) else (
    echo 启动失败
)

pause
goto :eof

:shutdown
taskkill /f /im pc.exe >nul
tasklist /FI "IMAGENAME eq pc.exe" /NH | findstr /i "pc.exe" >nul
if errorlevel 1 (
    echo 关闭成功
) else (
    echo 关闭失败
)
pause
goto :eof

:showHelp
echo 提供可用的命令参数：
echo /check: 检查 frpc 是否运行
echo /start: 后台运行 frpc
echo /shutdown: 关闭 frpc
goto :eof

:end
