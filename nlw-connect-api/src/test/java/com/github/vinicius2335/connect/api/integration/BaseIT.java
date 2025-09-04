package com.github.vinicius2335.connect.api.integration;


import com.github.vinicius2335.connect.api.domain.event.Event;
import com.github.vinicius2335.connect.api.domain.event.EventRepository;
import com.github.vinicius2335.connect.api.domain.subscription.Subscription;
import com.github.vinicius2335.connect.api.domain.subscription.SubscriptionRepository;
import com.github.vinicius2335.connect.api.domain.user.User;
import com.github.vinicius2335.connect.api.domain.user.UserRepository;
import com.github.vinicius2335.connect.api.utils.creator.EventCreator;
import com.github.vinicius2335.connect.api.utils.creator.SubscriptionCreator;
import com.github.vinicius2335.connect.api.utils.creator.UserCreator;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@AutoConfigureTestDatabase
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseIT {

    @LocalServerPort
    protected int port;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    void setupBase() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        // Ativa logging automático somente quando o teste falhar
        //RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()

        // Log sempre: request + response
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    protected final String messageInvalidFields = "One or more fields are invalid";
    protected final String messageEntityNotFound = "Entity not found";
    protected final String messageBadRequest = "Bad Request";

    // config Rest Assured
    protected void configRestAssured(String basePath){
        RestAssured.basePath = basePath;
    }

    protected User addUser(){
        User user = UserCreator.mockUser();
        return userRepository.save(user);
    }

    protected Event addEvent(){
        Event event = EventCreator.mockEvent();
        return eventRepository.save(event);
    }

    protected Subscription addSubscription(
            User subscriber, Event event
    ){
        Subscription subscription = SubscriptionCreator.mockSubscription(
                subscriber, event
        );
        return subscriptionRepository.save(subscription);
    }

    protected Subscription addSubscriptionWithReferrer(
            User subscriber, Event event, User referrer
    ){
        Subscription subscription = SubscriptionCreator.mockSubscription(
                subscriber, event, referrer
        );
        return subscriptionRepository.save(subscription);
    }
}
