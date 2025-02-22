# NLW Connect API

This project is a RESTful API for managing subscriptions in events. The API is built using Java and Spring Boot, leveraging JPA for database interactions.

## Features

- **Create Event**: Add new events with details such as title, location, price, and schedule.
- **List Events**: Retrieve a list of all events.
- **Find Event by Pretty Name**: Search for a specific event using a human-readable identifier.
- **Create Subscription**: Allows users to subscribe to events.
- **Generate Event Ranking**: Provides a ranking of the top subscribers for a specific event.
- **Generate User Ranking**: Provides a ranking for a specific user in an event.
- **Create Multiple Subscriptions**: Facilitates testing by allowing the creation of multiple subscriptions at once.

## Technologies Used

- **Java**: The primary programming language used for the API.
- **Spring Boot**: Framework used to build the API.
- **Swagger**: Used for API documentation.
- **Lombok**: Reduces boilerplate code.
- **Log4j2**: Logging framework.

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

## API Endpoints

### Create a new event
- POST /events

### Find All Events
- GET /events: 
  - Retrieve all events

### Find Event By Pretty Name
- GET /events/{prettyName}
  - Retrieve an event by its pretty name.


### Create Subscription
- POST /subscriptions/{prettyName} or /subscriptions/{prettyName}/{userId}
    - Creates a new subscription for a specified event.

### Generate Event Ranking
- GET /subscriptions/{prettyName}/ranking
    - Retrieves the top three subscribers for a specified event.

### Generate User Ranking
- GET /subscriptions/{prettyName}/ranking/{userId}
    - Retrieves the ranking of a specific user for a specified event.

### Create Multiple Subscriptions
- POST /subscriptions/{prettyName}/{userId}/test/addAll
    - Creates multiple subscriptions for testing purposes.

## Error Handling

The API handles various exceptions, including:

- EventNotFoundException: Thrown when an event is not found.
- SubscriptionConflictException: Thrown when a user is already subscribed to an event.
- UserNotFoundException: Thrown when a user is not found.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request for any enhancements or bug fixes.

## License

This project is licensed under the MIT License.
