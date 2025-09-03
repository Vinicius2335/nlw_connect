package com.github.vinicius2335.connect.api.api.openapi;

import com.github.vinicius2335.connect.api.domain.event.requests.CreateEventRequest;
import com.github.vinicius2335.connect.api.domain.event.Event;
import com.github.vinicius2335.connect.api.domain.event.exceptions.EventNotFoundException;
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

@Tag(name = "Event", description = "API for managing events")
public interface EventControllerOpenApi {

    @Operation(
            summary = "Create a new event",
            description = "Creates a new event with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class)))
    })
    ResponseEntity<Event> createNewEvent(
            @RequestBody(description = "Containing the details of the event to be created.", required = true) CreateEventRequest request
    );




    @Operation(
            summary = "Get all events",
            description = "Retrieves a list of all events.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of events retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class)))
    })
    ResponseEntity<List<Event>> findAllEvents();




    @Operation(
            summary = "Find event by pretty name",
            description = "Finds an event by its pretty name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "404", description = "Event not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<Event> findEventByPrettyName(
            @Parameter(description = "Friendly name of the event to be sought.", required = true) String prettyName
    ) throws EventNotFoundException;
}