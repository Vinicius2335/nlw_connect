package com.github.vinicius2335.connect.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.github.vinicius2335.connect.api.utils.FakerUtils.FAKER;

class RandomTest {

    @Test
    void faker_Test(){
        Assertions.assertThatCode(
                () -> {
                    int i = FAKER.number().randomDigitNotZero();

                    System.out.println(i);

                    double v = Double.parseDouble(FAKER.commerce().price().replace(",", "."));
                    System.out.println(v);
                }
        ).doesNotThrowAnyException();
    }
}
