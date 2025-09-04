package com.github.vinicius2335.connect.api;

import com.github.vinicius2335.connect.api.utils.creator.EventCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static com.github.vinicius2335.connect.api.utils.FakerUtils.FAKER;

class RandomTest {

    @Test
    void faker_Test() {
        Assertions.assertThatCode(
                () -> {
                    int i = FAKER.number().randomDigitNotZero();

                    System.out.println(i);

                    double v = Double.parseDouble(FAKER.commerce().price().replace(",", "."));
                    System.out.println(v);
                }
        ).doesNotThrowAnyException();
    }

    @Test
    void faker_future_date() {
        Assertions.assertThatCode(
                () -> {
                    String futureDateString = FAKER.timeAndDate()
                            .future(
                                    30,
                                    TimeUnit.DAYS,
                                    "yyyy-MM-dd"
                            );

                    LocalDate futureDate = LocalDate.parse(
                            futureDateString,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    );

                    //System.out.println("Generated Future Date (LocalDate): " + futureDate);

                    LocalDate now = LocalDate.now();
                    int lastDayOfMonth = now.getMonth().maxLength();
                    int minDayOfMonth = now.getDayOfMonth();
                    //System.out.println(minDayOfMonth);

                    LocalDate futureDate2 = FAKER.timeAndDate().future(
                                    lastDayOfMonth,
                                    minDayOfMonth,
                                    TimeUnit.DAYS
                            )
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();

                    //System.out.println("data futura " + futureDate2);

                    System.out.println(EventCreator.mockCreateEventRequest());
                    System.out.println(EventCreator.mockCreateEventRequest());
                    System.out.println(EventCreator.mockCreateEventRequest());
                    System.out.println(EventCreator.mockCreateEventRequest());
                    System.out.println(EventCreator.mockCreateEventRequest());
                    System.out.println(EventCreator.mockCreateEventRequest());
                }
        ).doesNotThrowAnyException();
    }
}
