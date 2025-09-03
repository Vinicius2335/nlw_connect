package com.github.vinicius2335.connect.api.domain.event.services;

import com.github.vinicius2335.connect.api.domain.event.Event;
import com.github.vinicius2335.connect.api.domain.event.exceptions.EventNotFoundException;
import com.github.vinicius2335.connect.api.domain.event.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindEventByIdService {
    private final EventRepository eventRepository;

    public Event execute(Integer eventId) throws EventNotFoundException {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found by id: " + eventId));
    }
}
