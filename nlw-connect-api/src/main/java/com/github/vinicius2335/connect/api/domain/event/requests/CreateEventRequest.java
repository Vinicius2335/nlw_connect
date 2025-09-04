package com.github.vinicius2335.connect.api.domain.event.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateEventRequest(
        @NotBlank(message = "VALIDATION.EVENT.NAME.NOT_BLANK")
        String name,

        @NotBlank(message = "VALIDATION.EVENT.LOCATION.NOT_BLANK")
        String location,

        @NotNull(message = "VALIDATION.EVENT.PRICE.NOT_NULL")
        @PositiveOrZero(message = "VALIDATION.EVENT.PRICE.POSITIVE_OR_ZERO")
        Double price,

        @NotBlank(message = "VALIDATION.EVENT.START_DATE.NOT_BLANK")
        String startDate,

        @NotBlank(message = "VALIDATION.EVENT.END_DATE.NOT_BLANK")
        String endDate,

        @NotBlank(message = "VALIDATION.EVENT.START_TIME.NOT_BLANK")
        String startTime,

        @NotBlank(message = "VALIDATION.EVENT.END_TIME.NOT_BLANK")
        String endTime
) {
}
