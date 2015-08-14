# talk
A small java based Jabber client to substitute Gtalk

###### How to install JGoodies library to the local repository?

This app is using JGoodies Forms, however the latest available version cannot be found in the central Maven repository. However you can install them to the local repository for now by the following steps

1. Visit http://www.jgoodies.com/downloads/archive/ page
2. Download JGoodies Forms 1.9.0 and JGoodies Common 1.8.1
3. Unzip them into a temporary folder
4. Open up a command line tool 
5. Go to jgoodies-common-1.8.1 folder
6. Execute this maven command: `mvn org.apache.maven.plugins:maven-install-plugin:2.5.2:install-file  -Dfile=jgoodies-common-1.8.1.jar -DpomFile=pom.xml -Dsources=jgoodies-common-1.8.1-sources.jar -Djavadoc=jgoodies-common-1.8.1-javadoc.jar` 
7. Go to jgoodies-forms-1.9.0 folder
8. Execute this maven command: `mvn org.apache.maven.plugins:maven-install-plugin:2.5.2:install-file  -Dfile=jgoodies-forms-1.9.0.jar -DpomFile=pom.xml -Dsources=jgoodies-forms-1.9.0-sources.jar -Djgoodies-forms-1.9.0-javadoc.jar`

 