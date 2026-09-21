# Step 1: Maven ka use karke project ko build karna
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Java runtime environment taiyar karna
FROM openjdk:17-jdk-slim
COPY --from=build /target/*.jar app.jar

# Hugging Face Spaces port 7860 par chalta hai
EXPOSE 7860
ENV PORT=7860

# Server port ko bind karke application run karna
ENTRYPOINT ["java","-Dserver.port=7860","-jar","/app.jar"]