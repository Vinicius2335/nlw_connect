package com.github.vinicius2335.connect.api.domain.event.services;

import com.github.vinicius2335.connect.api.domain.event.Event;
import com.github.vinicius2335.connect.api.domain.event.EventNotFoundException;
import com.github.vinicius2335.connect.api.domain.event.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindEventByPrettyNameService {
    private final EventRepository eventRepository;

    public Event execute(String prettyName) throws EventNotFoundException {
        return eventRepository.findByPrettyName(prettyName)
                .orElseThrow(() -> new EventNotFoundException("Event not found by pretty name: " + prettyName));
    }
}
