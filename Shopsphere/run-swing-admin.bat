@echo off
title ShopSphere - Swing Desktop Administration
echo ===================================================
echo Starting ShopSphere Desktop Admin Application...
echo RTU Advanced Java Syllabus - Swing MVC Architecture
echo ===================================================
java -cp "swing-admin\target\shopsphere-swing-1.0.0.jar;shopsphere-common\target\shopsphere-common-1.0.0.jar;%USERPROFILE%\.m2\repository\com\mysql\mysql-connector-j\8.3.0\mysql-connector-j-8.3.0.jar" com.shopsphere.swing.SwingAdminApp
pause
