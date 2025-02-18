package com.github.vinicius2335.connect.api.domain.event;

import com.github.vinicius2335.connect.api.domain.event.dtos.CreateEventRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventId;

    @Column(nullable = false)
    private String title;

    @Column(length = 50, nullable = false, unique = true)
    private String prettyName;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Double price;

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public Event(CreateEventRequest request) {
        this.title = request.name();
        this.prettyName = request.name().toLowerCase().replaceAll(" ", "-");
        this.location = request.location();
        this.price = request.price();
        this.startDate = LocalDate.parse(request.startDate());
        this.endDate = LocalDate.parse(request.endDate());
        this.startTime = LocalTime.parse(request.startTime());
        this.endTime = LocalTime.parse(request.endTime());
    }
}
