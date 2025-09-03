package com.github.vinicius2335.connect.api.api.controller;

import com.github.vinicius2335.connect.api.api.openapi.EventControllerOpenApi;
import com.github.vinicius2335.connect.api.domain.event.Event;
import com.github.vinicius2335.connect.api.domain.event.exceptions.EventNotFoundException;
import com.github.vinicius2335.connect.api.domain.event.requests.CreateEventRequest;
import com.github.vinicius2335.connect.api.domain.event.services.CreateEventService;
import com.github.vinicius2335.connect.api.domain.event.services.FindAllEventsService;
import com.github.vinicius2335.connect.api.domain.event.services.FindEventByPrettyNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/events")
@RestController
public class EventController implements EventControllerOpenApi {
    private final CreateEventService createEventService;
    private final FindAllEventsService findAllEventsService;
    private final FindEventByPrettyNameService findEventByPrettyNameService;

    /**
     * Cria um novo evento com base nos dados fornecidos no corpo da requisição.
     *
     * @param request Objeto CreateEventRequest contendo os detalhes do evento a ser criado.
     * @return ResponseEntity contendo o evento criado e o status HTTP 201 (Created).
     */
    @PostMapping
    public ResponseEntity<Event> createNewEvent(@RequestBody CreateEventRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createEventService.execute(request));
    }

    /**
     * Retorna uma lista de todos os eventos disponíveis.
     *
     * @return ResponseEntity contendo a lista de eventos e o status HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Event>> findAllEvents(){
        return ResponseEntity.ok(findAllEventsService.execute());
    }

    /**
     * Retorna um evento com base no 'prettyName' fornecido.
     *
     * @param prettyName Nome amigável do evento a ser buscado.
     * @return ResponseEntity contendo o evento encontrado e o status HTTP 200 (OK).
     * @throws EventNotFoundException se nenhum evento for encontrado com o 'prettyName' fornecido.
     */
    @GetMapping("/{prettyName}")
    public ResponseEntity<Event> findEventByPrettyName(
            @PathVariable String prettyName
    ) throws EventNotFoundException {
        return ResponseEntity.ok(findEventByPrettyNameService.execute(prettyName));
    }
}

