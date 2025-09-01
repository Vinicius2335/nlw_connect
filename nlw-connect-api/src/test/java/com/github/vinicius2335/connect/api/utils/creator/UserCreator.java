package com.github.vinicius2335.connect.api.utils.creator;

import com.github.vinicius2335.connect.api.domain.user.User;
import lombok.experimental.UtilityClass;

import static com.github.vinicius2335.connect.api.utils.FakerUtils.FAKER;

@UtilityClass
public class UserCreator {

    public static User mockUser(){
        return User.builder()
                .name(FAKER.name().name())
                .email(FAKER.internet().emailAddress())
                .build();
    }

}
