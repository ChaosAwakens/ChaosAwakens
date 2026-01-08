@echo off
setlocal enabledelayedexpansion

:: Get current directory name for the zip file name
for %%i in (.) do set "CURRENT_DIR=%%~nxi"
set "ZIP_NAME=%CURRENT_DIR%_%date:~-4,4%%date:~-10,2%%date:~-7,2%.zip"
set "TEMP_DIR=%TEMP%\%CURRENT_DIR%_temp"

echo Creating temporary directory...
if exist "%TEMP_DIR%" rmdir /s /q "%TEMP_DIR%"
mkdir "%TEMP_DIR%"

echo Copying files (respecting .gitignore)...
:: Copy all files except those in .gitignore
robocopy . "%TEMP_DIR%" /E /XD .git build out .gradle /XF .gitignore /XD %TEMP_DIR%\.git /NP /NFL /NDL /NJH /NJS /nc /ns /np

:: Remove .git directory if it was copied
if exist "%TEMP_DIR%\.git" rmdir /s /q "%TEMP_DIR%\.git"

echo Creating archive: %ZIP_NAME%...
powershell -command "Compress-Archive -Path '%TEMP_DIR%\*' -DestinationPath '%CD%\%ZIP_NAME%' -Force"

echo Cleaning up...
rmdir /s /q "%TEMP_DIR%"

echo.
echo Archive created: %CD%\%ZIP_NAME%
pause