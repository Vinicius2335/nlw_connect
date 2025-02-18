package com.github.vinicius2335.connect.api.domain.event.services;

import com.github.vinicius2335.connect.api.domain.event.Event;
import com.github.vinicius2335.connect.api.domain.event.EventRepository;
import com.github.vinicius2335.connect.api.domain.event.dtos.CreateEventRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateEventService {
    private final EventRepository eventRepository;

    @Transactional
    public Event execute(CreateEventRequest request){
        Event event = new Event(request);

        return eventRepository.save(event);
    }
}
