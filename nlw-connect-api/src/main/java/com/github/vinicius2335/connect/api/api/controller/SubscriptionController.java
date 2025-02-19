package com.github.vinicius2335.connect.api.api.controller;


import com.github.vinicius2335.connect.api.domain.event.EventNotFoundException;
import com.github.vinicius2335.connect.api.domain.subscription.SubscriptionConflictException;
import com.github.vinicius2335.connect.api.domain.subscription.SubscriptionResponse;
import com.github.vinicius2335.connect.api.domain.subscription.services.CreateSubscriptionService;
import com.github.vinicius2335.connect.api.domain.user.UserNotFoundException;
import com.github.vinicius2335.connect.api.domain.user.UserSubscriptionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/subscription")
@RestController
public class SubscriptionController {
    private final CreateSubscriptionService createSubscriptionService;

    @PostMapping({"/{prettyName}", "/{prettyName}/{userId}"})
    public ResponseEntity<SubscriptionResponse> createSubscription(
            @PathVariable String prettyName,
            @PathVariable(required = false) Integer userId,
            @RequestBody UserSubscriptionRequest request
    ) throws EventNotFoundException, SubscriptionConflictException, UserNotFoundException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createSubscriptionService.execute(prettyName, request, userId));
    }
}
