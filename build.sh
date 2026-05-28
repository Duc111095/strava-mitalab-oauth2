
#!/bin/bash
mvn clean package && echo "Clean successfully!" && cp target/runner.war ~/apache-tomcat-9.0.102/webapps/ && echo "Copy successfully!"
