
#!/bin/bash
mvn clean package && cp target/runner.war ~/apache-tomcat-9.0.102/webapps/
