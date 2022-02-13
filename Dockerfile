# FROM maven:3.8.4 AS builder
# COPY [".", "/root/"]
# WORKDIR /root
# RUN cd /root/mandarin-common && mvn package && \
#     cd /root/mandarin-back   && mvn package && \
#     cd /root/mandarin-front  && mvn package && \
#     cd /root/mandarin-runner && mvn package
#
# FROM openjdk:11.0-jre
# WORKDIR /root
# COPY --from=builder ["/root/mandarin-runner/target/mandarin-runner-0.0.1-SNAPSHOT.jar", "./runner.jar"]
# CMD ["java", "-jar", "./runner.jar" ]

FROM openjdk:11.0-jre
WORKDIR /root
COPY ["./mandarin-runner/mandarin-runner.jar", "./runner.jar"]
COPY ["./mandarin-runner/src/main/resources/application.properties", "./application.properties"]
CMD ["java", "-jar", "./runner.jar", "-Dspring.datasource.data=file:application.properties" ]
