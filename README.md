# TASKAPPSERVER
# Task App Server

This is a Spring Boot RESTful API for managing tasks, supporting CRUD operations with PostgreSQL as the backend database. The project includes robust validation, integration tests, and utility functions for handling task data.

## Features

- **Create, Read, Update, Delete (CRUD) Tasks**  
  Endpoints for creating, retrieving (by ID and all), updating, and deleting tasks.

- **UUID Validation**  
  Task IDs are validated using a strict UUID regex to ensure data integrity.

- **Date Utilities**  
  Includes utility methods for date formatting and manipulation.

- **Integration Tests**  
  Comprehensive integration tests using JUnit and MockMvc to ensure endpoint reliability.

- **PostgreSQL Stored Procedures/Functions**  
  Database operations are performed via stored procedures/functions for better performance and maintainability. The DDLS for the Table(TASK), Functions and Procedure Signatures are included in the Data.sql file in the ```src/resource``` folder

- **CORS Support**  
  Configured to allow cross-origin requests for easy frontend integration.

## API Endpoints

| Method | Endpoint                | Description                  |
|--------|-------------------------|------------------------------|
| POST   | `/api/tasks/create`     | Create a new task            |
| GET    | `/api/tasks/getAll`     | Retrieve all tasks           |
| GET    | `/api/tasks/{id}`       | Retrieve a task by ID        |
| PUT    | `/api/tasks/{id}`       | Update a task by ID          |
| DELETE | `/api/tasks/{id}`       | Delete a task by ID          |

## Task Model

A task includes:
- `taskId` (UUID string)
- `taskName` (String)
- `taskDescription` (String)
- `taskStatus` (String)
- `taskDueDate` (String, format: `yyyy-MM-dd`)

## Validation

- Task IDs are validated to match UUID format.
- Dates are validated and can be reversed (e.g., `2025-06-28` to `28-06-2025`).

## Running the Project

1. **Configure Database**  
   Update your `src/main/resources/application.properties` with your PostgreSQL credentials.

2. **Build and Run**  
   ```
   ./mvnw spring-boot:run
   ```

3. **API Usage**  
   Use tools like Postman or curl to interact with the endpoints.

## Testing

- Run all tests with:
  ```
  ./mvnw test
  ```
- Integration tests cover all main endpoints and utility functions

Live Production Testing : http://3.10.228.46:8080/api/tasks/getAll

## Api Documentation
    The details about each api can be retrieved from the Swagger page, by launching the link below once the application is running. 

    http://localhost:8080/swagger-ui/index.html#/
    http://localhost:8080/v3/api-docs
    http://3.10.228.46:8080/swagger-ui/index.html#/
    http://3.10.228.46:8080//v3/api-docs

## Example: Create Task Request

```json
POST /api/tasks/create
Content-Type: application/json

{
  "taskName": "Sample Task",
  "taskDescription": "This is a test task",
  "taskStatus": "PENDING",
  "taskDueDate": "2025-07-01"
}
```

## Example: Validate Task ID

The following regex is used to validate UUIDs:
```
^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$
```

## Contributing

Pull requests are welcome! Please add tests for any new features or bug fixes.

---

**Author:**  
Bolaji Adesina  

