FROM adoptopenjdk/openjdk11:jre-11.0.9.1_1-alpine
EXPOSE 8080
ADD target/craft-leaderboard-1.jar craft-leaderboard-1.jar
ENTRYPOINT ["java","-jar","/craft-leaderboard-1.jar"]
ENV JAVA_VER 11
ENV JAVA_HOME ${JAVA_HOME}