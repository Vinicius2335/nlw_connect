# Event Management API

This project is a RESTful API for managing events. It allows users to create, retrieve, and manage event data. The API is built using Java and Spring Boot, leveraging JPA for database interactions.

## Features

- **Create Event**: Add new events with details such as title, location, price, and schedule.
- **List Events**: Retrieve a list of all events.
- **Find Event by Pretty Name**: Search for a specific event using a human-readable identifier.

## Technologies Used

- Java
- Spring Boot
- JPA (Java Persistence API)
- Lombok
- Jakarta Persistence

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- A database (e.g., MySQL, PostgreSQL)

### Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/event-management-api.git
   cd event-management-api
    ```

2. **Configure the database**: Update the application.properties file with your database connection details.

3. **Build the project**:
``mvn clean install``

4. **Run the application**:
``mvn spring-boot:run``

### API Endpoints
- POST /events: Create a new event.
- GET /events: Retrieve all events.
- GET /events/{prettyName}: Retrieve an event by its pretty name.

### Error Handling
The API uses custom exceptions to handle errors, such as EventNotFoundException for cases where an event cannot be found.

### Contributing
Contributions are welcome! Please fork the repository and submit a pull request for any enhancements or bug fixes.

### License
This project is licensed under the MIT License.

### Contact
For questions or support, please contact your-email@example.com.

