ENV=$1
HOST="15.164.169.58"
if [ $ENV == "dev" ]
then
    mvn package spring-boot:repackage -Dmaven.test.skip=true
    java -jar -Dspring.profiles.active=${ENV} ./target/myapp-0.0.1.jar
elif [ $ENV == "prod" ]
then
    mvn package spring-boot:repackage -Dmaven.test.skip=true
    docker -H ${HOST} rm -f groot
    docker -H ${HOST} image rm groot:0.1
    docker -H ${HOST} build --no-cache -t groot:0.1 .
    docker -H ${HOST} run -p 8080:8080 -d --name groot groot:0.1
fi