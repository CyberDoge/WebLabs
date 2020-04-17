#!/usr/bin/env bash
sh /opt/tomcat9/bin/catalina.sh stop
./gradlew explodedWar
rm -rf /opt/tomcat9/webapps/web-3
mv build/exploded/web-3 /opt/tomcat9/webapps
sh /opt/tomcat9/bin/catalina.sh jpda start