Note Service API (Spring Boot + MongoDB)

This project provides a REST API for managing notes: creating notes, listing them with pagination and sorting, and filtering by tags.
It is built with Spring Boot, Spring Web, and Spring Data MongoDB.
API documentation is available via Swagger UI.

✅ How to Run

Requirements:

Docker installed and running

Steps:

Option 1: Using your IDE
Just run the docker-compose.yml file from the IDE.
Wait a few seconds or minutes until all services start.

Option 2: Using terminal

docker compose up -d


After the application starts, open:

Swagger UI → http://localhost:8080/swagger-ui.html
📌 Project Structure Overview

**Package**  
controller  
Handles incoming HTTP requests

service  
Business logic layer

repository  
Data access layer to MongoDB

documents  
MongoDB document models

dto  
Data transfer objects

The codebase includes Javadoc and Swagger annotations where necessary to clarify API behavior.


🧪 Testing

Controller tests are implemented with MockMvc.
Mongo repository tests use Testcontainers to run against a real MongoDB environment. 