@echo off
start "MyTool" java -jar "%~dp0MyTool\MyTool.jar" --spring.config.location="%~dp0MyTool\application.yml"