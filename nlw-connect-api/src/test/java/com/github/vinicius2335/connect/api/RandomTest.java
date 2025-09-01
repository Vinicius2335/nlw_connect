package com.github.vinicius2335.connect.api;

import com.github.vinicius2335.connect.api.utils.FakerUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class RandomTest {

    @Test
    void faker_Test(){
        Assertions.assertThatCode(
                () -> {
                    int i = FakerUtils.FAKER.number().randomDigitNotZero();

                    System.out.println(i);
                }
        ).doesNotThrowAnyException();
    }
}
