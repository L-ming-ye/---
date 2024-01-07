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
    echo ��������Ч�����
    goto :end
)

:menu
echo ��ѡ�������
echo 1. ��� frpc �Ƿ�����
echo 2. ��̨���� frpc
echo 3. �ر� frpc
echo 4. �˳�

set /p choice=�����������ţ�

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
    echo ��������Ч�Ĳ�����ţ�
    goto :menu
)

:check
cd /d %frpcPath%
tasklist /FI "IMAGENAME eq pc.exe" /NH | find /i "pc.exe" >nul
if not errorlevel 1 (
    echo ��������
) else (
    echo δ����
)

pause
goto :eof

:startSilent
cd /d %frpcPath%
tasklist /FI "IMAGENAME eq pc.exe" /NH | findstr /i "pc.exe" >nul
if not errorlevel 1 (
    echo �Ѿ������У������ٴ�����
    goto :eof
)

"%nircmdPath%" exec hide pc.exe -c %frpcConfig%

timeout /t 3 >nul

tasklist /FI "IMAGENAME eq pc.exe" /NH | findstr /i "pc.exe" >nul
if not errorlevel 1 (
    echo �����ɹ�
) else (
    echo ����ʧ��
)

pause
goto :eof

:shutdown
taskkill /f /im pc.exe >nul
tasklist /FI "IMAGENAME eq pc.exe" /NH | findstr /i "pc.exe" >nul
if errorlevel 1 (
    echo �رճɹ�
) else (
    echo �ر�ʧ��
)
pause
goto :eof

:showHelp
echo �ṩ���õ����������
echo /check: ��� frpc �Ƿ�����
echo /start: ��̨���� frpc
echo /shutdown: �ر� frpc
goto :eof

:end
