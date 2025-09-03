package com.github.vinicius2335.connect.api.api.openapi;

import com.github.vinicius2335.connect.api.domain.event.exceptions.EventNotFoundException;
import com.github.vinicius2335.connect.api.domain.subscription.exceptions.SubscriptionConflictException;
import com.github.vinicius2335.connect.api.domain.subscription.dtos.UserSubscriptionRanking;
import com.github.vinicius2335.connect.api.domain.subscription.dtos.SubscriptionRankingItem;
import com.github.vinicius2335.connect.api.domain.subscription.dtos.SubscriptionResponse;
import com.github.vinicius2335.connect.api.domain.user.exceptions.UserNotFoundException;
import com.github.vinicius2335.connect.api.domain.user.requests.UserSubscriptionRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Subscription", description = "API for managing subscriptions")
public interface SubscriptionControllerOpenApi {

    @Operation(
            summary = "Create a subscription",
            description = "Creates a new subscription for a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Subscription created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubscriptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Event or User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Subscription conflict",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<SubscriptionResponse> createSubscription(
            @Parameter(description = "The friendly name of the event for which the registration will be created.", required = true) String prettyName,
            @Parameter(description = "Optional user ID that indicated the registration", required = false) Integer referrer,
            @RequestBody(description = "User registration details, including name and email.", required = true) UserSubscriptionRequest request
    ) throws EventNotFoundException, SubscriptionConflictException, UserNotFoundException;



    @Operation(
            summary = "Generate event ranking",
            description = "Generates a ranking of subscriptions for an event.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ranking generated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubscriptionRankingItem.class))),
            @ApiResponse(responseCode = "404", description = "Event not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<List<SubscriptionRankingItem>> generateRankingByEvent(
            @Parameter(description = "The friendly name of the event for which the ranking will be generated.", required = true) String prettyName
    ) throws EventNotFoundException;



    @Operation(
            summary = "Generate user ranking",
            description = "Generates a ranking for a user in an event.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User ranking generated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserSubscriptionRanking.class))),
            @ApiResponse(responseCode = "404", description = "Event or User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<UserSubscriptionRanking> generateRankingByUser(
            @Parameter(description = "The friendly name of the event for which the ranking will be generated.", required = true) String prettyName,
            @Parameter(description = "The user ID for which the ranking will be generated.", required = true) Integer userId
    ) throws UserNotFoundException, EventNotFoundException;


}