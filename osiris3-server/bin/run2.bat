@echo off
echo JAVA_HOME: %JAVA_HOME% 

rem This will be the run directory
set RUN_DIR=%cd%
rem Move up...
cd ..
rem This will be the base directory
set BASE_DIR=%cd%

set JAVA_OPT="-Xmx256m -Dosiris.run.dir=%RUN_DIR% -Dosiris.base.dir=%BASE_DIR%"

"%JAVA_HOME%/bin/java" "%JAVA_OPT%" -cp lib/*;. com.rameses.main.bootloader.MainBootLoader
pause
