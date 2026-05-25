FROM tomcat:9-jdk17-openjdk-slim
LABEL authors="Michael Stöbich"

COPY  ./target/game2048.war /usr/local/tomcat/webapps/ROOT.war

RUN apt update && apt upgrade -y && chmod 777 /usr/local/tomcat/webapps

EXPOSE 8080

CMD ["catalina.sh", "run"]