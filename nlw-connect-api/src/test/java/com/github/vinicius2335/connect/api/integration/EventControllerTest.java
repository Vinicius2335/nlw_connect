package com.github.vinicius2335.connect.api.integration;

import com.github.vinicius2335.connect.api.domain.event.Event;
import com.github.vinicius2335.connect.api.domain.event.requests.CreateEventRequest;
import com.github.vinicius2335.connect.api.utils.creator.EventCreator;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@DisplayName("Integration test to EventController")
class EventControllerTest extends BaseIT {

    @BeforeEach
    void setUp() {
        configRestAssured("/events");
    }

    @Test
    void createNewEvent_return201AndEvent_whenSuccessfully() {
        CreateEventRequest request = EventCreator.mockCreateEventRequest();

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("title", Matchers.is(request.name()));
    }

    @Test
    void createNewEvent_return400AndProblemDetails_whenRequestHaveInvalidFields() {
        CreateEventRequest request = EventCreator.mockInvalidCreateEventRequest();

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", Matchers.is(messageInvalidFields));
    }

    @Test
    void createNewEvent_return400AndProblemDetails_whenRequestHaveInvalidDateTimeFormat() {
        CreateEventRequest request = EventCreator.mockInvalidDateAndTimeCreateEventRequest();

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", Matchers.is(messageBadRequest));
    }

    @Test
    void findAllEvents_return200AndEventList_whenSuccessfully() {
        Event event = addEvent();

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", Matchers.hasSize(1))
                .body("[0].prettyName", Matchers.is(event.getPrettyName()));
    }

    @Test
    void findEventByPrettyName_return200AndEvent_whenSuccessfully() {
        Event event = addEvent();
        String prettyName = event.getPrettyName();

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("prettyName", prettyName)
                .when()
                .get("/{prettyName}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("prettyName", Matchers.is(event.getPrettyName()));
    }

    @Test
    void findEventByPrettyName_return404AndProblemDetails_whenEventNotFoundByPrettyName() {
        String prettyName = "lalalala";

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("prettyName", prettyName)
                .when()
                .get("/{prettyName}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("title", Matchers.is(messageEntityNotFound));
    }
}