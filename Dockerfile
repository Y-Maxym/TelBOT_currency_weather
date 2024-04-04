FROM gradle:jdk17-alpine

WORKDIR /app

COPY . .

RUN chmod +x gradlew && ./gradlew build

CMD ["./gradlew", "bootJar"]