@echo off
start "MyTool" javaw -jar "%~dp0MyTool\MyTool.jar" --spring.config.location="%~dp0MyTool\application.yml"