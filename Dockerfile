FROM openjdk:17
COPY rest/target/payment-rest.jar payment.jar
EXPOSE 8083
CMD ["java", "-jar", "payment.jar"]