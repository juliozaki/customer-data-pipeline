# customer-data-pipeline
Sending data through microservices using Snowflow, Kafka and MongoDB.

1. To run this microservice, first, you should set SnowFlake connection Strin into application properties: customer-data-pipeline\micro1\src\main\resources\application.properties.
2. You must modify user.db.snowflake.connection and put your connection data, follow these steps https://docs.snowflake.com/en/developer-guide/jdbc/jdbc-configure.
3. When you have opened the project in your IDE add a environment variable in the run configuration: JDK_JAVA_OPTIONS= --add-opens=java.base/java.nio=ALL-UNNAMED
4. Run this project.
