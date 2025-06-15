FROM openjdk:17-jdk-slim

WORKDIR /api

COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .
COPY src ./src
COPY build ./build

# 애플리케이션이 사용할 포트 노출
EXPOSE 8080

CMD ["./gradlew", "bootRun", "--continuous"]

#RUN ./gradlew bootJar --no-daemon
# CMD ["java", "-jar", "build/libs/*.jar"] # 정확한 JAR 파일명 확인 필요