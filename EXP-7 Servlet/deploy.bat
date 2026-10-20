@echo off
set "CATALINA_HOME=C:\Users\nikhi\Downloads\apache-tomcat-11.0.26-windows-x64\apache-tomcat-11.0.26"

echo 1. Building WAR file with Maven...
call mvn clean package
if %ERRORLEVEL% NEQ 0 (
    echo Maven build failed!
    pause
    exit /b %ERRORLEVEL%
)

echo 2. Deploying servlet.war to Tomcat webapps...
copy /Y "target\servlet.war" "%CATALINA_HOME%\webapps\servlet.war"

echo 3. Opening student.jsp in browser...
start http://localhost:8080/servlet/student.jsp

echo Done! If Tomcat is not started yet, run %CATALINA_HOME%\bin\startup.bat
