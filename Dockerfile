# =====================================================
# BOSQICH 1: BUILD (Loyihani .jar faylga yig'ish)
# =====================================================
# Maven va JDK 17 bilan rasmiy Docker image
FROM maven:3.9-eclipse-temurin-17 AS build

# Konteyner ichida ishlash papkasini belgilash
WORKDIR /app

# Avval faqat pom.xml ni ko'chiramiz (tezlik uchun - bog'liqliklar cache'lanadi)
COPY pom.xml .

# Barcha bog'liqliklarni (dependencies) yuklab olamiz
RUN mvn dependency:go-offline -B

# Keyin asosiy kod fayllarini ko'chiramiz
COPY src ./src

# Loyihani .jar faylga yig'amiz (-DskipTests - testlarsiz, tezroq)
RUN mvn clean package -DskipTests

# =====================================================
# BOSQICH 2: RUN (Faqat .jar faylni ishga tushirish)
# =====================================================
# Kichkina JRE image (JDK kerak emas, faqat ishga tushirish uchun)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Build bosqichidan faqat tayyor .jar faylni olamiz
COPY --from=build /app/target/*.jar app.jar

# Render.com PORT muhit o'zgaruvchisini ishlatadi
EXPOSE 8080

# Ilovani ishga tushirish buyrug'i
ENTRYPOINT ["java", "-jar", "app.jar"]
