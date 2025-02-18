package com.github.vinicius2335.connect.api.api.controller;

import com.github.vinicius2335.connect.api.domain.event.Event;
import com.github.vinicius2335.connect.api.domain.event.EventNotFoundException;
import com.github.vinicius2335.connect.api.domain.event.dtos.CreateEventRequest;
import com.github.vinicius2335.connect.api.domain.event.services.CreateEventService;
import com.github.vinicius2335.connect.api.domain.event.services.FindAllEventsService;
import com.github.vinicius2335.connect.api.domain.event.services.FindEventByPrettyNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/events")
@RestController
public class EventController {
    private final CreateEventService createEventService;
    private final FindAllEventsService findAllEventsService;
    private final FindEventByPrettyNameService findEventByPrettyNameService;

    @PostMapping
    public ResponseEntity<Event> createNewEvent(@RequestBody CreateEventRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createEventService.execute(request));
    }

    @GetMapping
    public ResponseEntity<List<Event>> findAllEvents(){
        return ResponseEntity.ok(findAllEventsService.execute());
    }

    @GetMapping("/{prettyName}")
    public ResponseEntity<Event> findEventByPrettyName(
            @PathVariable String prettyName
    ) throws EventNotFoundException {
        return ResponseEntity.ok(findEventByPrettyNameService.execute(prettyName));
    }
}
