package com.github.vinicius2335.connect.api.domain.event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {

    Optional<Event> findByPrettyName(String prettyName);
}