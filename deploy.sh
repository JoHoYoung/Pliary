# mvn package spring-boot:repackage -Dmaven.test.skip=true
# java -jar ./target/myapp-0.0.1.jar
mvn package spring-boot:repackage -Dmaven.test.skip=true
docker rm -f groot
docker image rm groot
docker build -t groot:0.1 .
docker run -p 8080:8080 -d --name groot groot:0.1