mvn package spring-boot:repackage -Dmaven.test.skip=true
java -jar -Dspring.profiles.active=${ENV} ./target/myapp-0.0.1.jar
