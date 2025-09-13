# customer-data-pipeline
Sending data through microservices using Snowflow, Kafka and MongoDB

This project contains 2 microservices: 
1. micro1: Retrieves customer and address information from **Snowflake** using a **paginated SQL query**  -> **GET /api/customers/**. Retrives customer and address information by ID and sends the selected customer data to a **Kafka topic** using a **Kafka Producer**  -> **GET /api/customers/fetch/{id}**.
2. micro2: A **Kafka Consumer** listens to the topic and stores the received data in a MongoDB collection named "customer_data". Returns the list of customers stored in MongoDB   -> **GET /api/customers/mongo**.

Before running or compiling microservices you must have your environment ready:

1. Java 17.
2. Snowflake account For the purposes of demonstrating its use, customer data should be obtained from the `SNOWFLAKE_SAMPLE_DATA.TPCDS_SF100TCL`.
3. Create containers for Kafka and MongoDB servers.
4. Set Snowflake connection string into application properties (see READ file micro1).

To create the containers, follow these steps:

1. Install WSL ( Windows Subsystem for Linux ) -> follow these steps https://learn.microsoft.com/es-es/windows/wsl/install
2. Install podman -> follow these steps https://github.com/containers/podman/blob/main/docs/tutorials/podman-for-windows.md
3. Install Python to use Podman Compose with PIP3. Follow these steps https://www.python.org/downloads/
4. Start a new virtual machine with podman, on the command line you should run -> podman machine start
5. Only the first time we should install Podman Compose, on the command line you should run -> pip3 install podman-compose
6. On the command line open the git directory of this project. Example -> cd C:\GitHub\customer-data-pipeline
7. On the command line create the containers with the YARN file in this directory called docker-compose -> podman compose -f docker-compose.yml up

All the server have been configured and are ready to work with the microservices. You should know that Kafka configuration have 2 replicas and microservices will work with it.

There is a file called examen-recluit.postman_collection.json in the /scripts directory. These are examples of the uses of the CONTROLLERS (Endpoints) from Postman.
