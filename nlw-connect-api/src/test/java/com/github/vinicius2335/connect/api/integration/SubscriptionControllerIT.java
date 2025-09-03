package com.github.vinicius2335.connect.api.integration;

import com.github.vinicius2335.connect.api.domain.event.Event;
import com.github.vinicius2335.connect.api.domain.user.User;
import com.github.vinicius2335.connect.api.domain.user.requests.UserSubscriptionRequest;
import com.github.vinicius2335.connect.api.utils.creator.UserCreator;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("Integration test to SubscriptionController")
class SubscriptionControllerIT extends BaseIT{

    @BeforeEach
    void setUp() {
        configRestAssured("/subscriptions");
    }

    @Test
    void createSubscription_return201AndSubscriptionResponse_whenSubscriberExists_andReferrerExists() {
        User subscriber = addUser();
        User referrer = addUser();
        Event event = addEvent();

        UserSubscriptionRequest request = new UserSubscriptionRequest(
                subscriber.getName(), subscriber.getEmail()
        );

        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParams("prettyName", event.getPrettyName())
                .queryParam("referrer", referrer.getUserId())
                .body(request)
                .when()
                .post("/{prettyName}")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("subscriberId", Matchers.equalTo(subscriber.getUserId()));
    }

    @Test
    void createSubscription_return201AndSubscriptionResponse_whenSubscriberExists_andReferrerIsNull() {
        User subscriber = addUser();
        Event event = addEvent();

        UserSubscriptionRequest request = new UserSubscriptionRequest(
                subscriber.getName(), subscriber.getEmail()
        );

        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParams("prettyName", event.getPrettyName())
                .body(request)
                .when()
                .post("/{prettyName}")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("subscriberId", Matchers.equalTo(subscriber.getUserId()));
    }

    @Test
    void createSubscription_return201AndSubscriptionResponse_whenSubscriberNoExists_andReferrerExists() {
        User newSubscriber = UserCreator.mockUser();
        User referrer = addUser();
        Event event = addEvent();

        String designation = "http://localhost:3000/" + event.getPrettyName();

        UserSubscriptionRequest request = new UserSubscriptionRequest(
                newSubscriber.getName(), newSubscriber.getEmail()
        );

        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParams("prettyName", event.getPrettyName())
                .queryParam("referrer", referrer.getUserId())
                .body(request)
                .when()
                .post("/{prettyName}")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("designation", Matchers.containsString(designation));
    }

    @Test
    void createSubscription_return201AndSubscriptionResponse_whenSubscriberNoExists_andReferrerNoExists() {
        User newSubscriber = UserCreator.mockUser();
        Event event = addEvent();

        String designation = "http://localhost:3000/" + event.getPrettyName();

        UserSubscriptionRequest request = new UserSubscriptionRequest(
                newSubscriber.getName(), newSubscriber.getEmail()
        );

        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParams("prettyName", event.getPrettyName())
                .body(request)
                .when()
                .post("/{prettyName}")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("designation", Matchers.containsString(designation));
    }

    @Test
    void createSubscription_return400AndProblemDetails_whenRequestHaveInvalidFields() {
        User referrer = addUser();
        Event event = addEvent();

        UserSubscriptionRequest request = new UserSubscriptionRequest(
                null, null
        );

        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParams("prettyName", event.getPrettyName())
                .queryParam("referrer", referrer.getUserId())
                .body(request)
                .when()
                .post("/{prettyName}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", Matchers.equalTo(messageInvalidFields));
    }

    @Test
    void createSubscription_return404AndProblemDetails_whenEventNotFoundByPrettyName() {
        User subscriber = addUser();
        User referrer = addUser();

        UserSubscriptionRequest request = new UserSubscriptionRequest(
                subscriber.getName(), subscriber.getEmail()
        );

        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParams("prettyName", "lalalala")
                .queryParam("referrer", referrer.getUserId())
                .body(request)
                .when()
                .post("/{prettyName}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("title", Matchers.equalTo(messageEntityNotFound));
    }

    @Test
    void createSubscription_return409AndProblemDetails_whenSubscriberHasAlreadyRegisteredForTheEvent() {
        User subscriber = addUser();
        User referrer = addUser();
        Event event = addEvent();
        addSubscription(subscriber, event);

        UserSubscriptionRequest request = new UserSubscriptionRequest(
                subscriber.getName(), subscriber.getEmail()
        );

        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParams("prettyName", event.getPrettyName())
                .queryParam("referrer", referrer.getUserId())
                .body(request)
                .when()
                .post("/{prettyName}")
                .then()
                .statusCode(HttpStatus.CONFLICT.value())
                .body("title", Matchers.equalTo("Conflict"));
    }

    @Test
    void generateRankingByEvent_return200AndSubscriptionRankingItemList_whenSuccessfully() {
        User subscriber = addUser();
        User referrer = addUser();
        Event event = addEvent();
        addSubscriptionWithReferrer(subscriber, event, referrer);

        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParams("prettyName", event.getPrettyName())
                .when()
                .get("/{prettyName}/ranking")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", Matchers.hasSize(1));
    }

    @Test
    void generateRankingByEvent_return200AndEmptySubscriptionRankingItemList_whenNoUserHasMadeAnyIndication() {
        User subscriber = addUser();
        Event event = addEvent();
        addSubscription(subscriber, event);

        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParams("prettyName", event.getPrettyName())
                .when()
                .get("/{prettyName}/ranking")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", Matchers.empty());
    }

    @Test
    void generateRankingByEvent_return404AndProblemDetails_whenEventNotFoundByPrettyName() {

        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParams("prettyName", "lalalala")
                .when()
                .get("/{prettyName}/ranking")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("title", Matchers.is(messageEntityNotFound));
    }

    @Test
    void generateRankingByUser_return200AndUserSubscriptionRanking_whenSuccessfully() {
        User subscriber = addUser();
        User referrer = addUser();
        Event event = addEvent();
        addSubscriptionWithReferrer(subscriber, event, referrer);

        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParams("prettyName", event.getPrettyName())
                .pathParams("userId", referrer.getUserId())
                .when()
                .get("/{prettyName}/ranking/{userId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("item.userName", Matchers.is(referrer.getName()));
    }

    @Test
    void generateRankingByUser_return404AndProblemDetails_whenEventNotFoundByPrettyName() {
        User user = addUser();
        String prettyName = "lalala";

        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParams("prettyName", prettyName)
                .pathParams("userId", user.getUserId())
                .when()
                .get("/{prettyName}/ranking/{userId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("title", Matchers.is(messageEntityNotFound))
                .body("detail", Matchers.is("Event not found by pretty name: lalala"));
    }

    @Test
    void generateRankingByUser_return404AndProblemDetails_whenUserNotFoundById() {
        int userId = 99;
        Event event = addEvent();

        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParams("prettyName", event.getPrettyName())
                .pathParams("userId", userId)
                .when()
                .get("/{prettyName}/ranking/{userId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("title", Matchers.is(messageEntityNotFound))
                .body("detail", Matchers.is("User not found by id: " + userId));
    }

}