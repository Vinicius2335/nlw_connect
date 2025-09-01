package com.github.vinicius2335.connect.api.domain.user;

import com.github.vinicius2335.connect.api.utils.creator.UserCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DisplayName("Unit test to UserRepository")
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    private User user;

    @BeforeEach
    void setUp() {
        user = UserCreator.mockUser();
    }

    @Test
    void findByEmail_returnUser_whenSuccessfully() {
        // given
        User savedUser = getSavedUser();
        // when
        Optional<User> result = underTest.findByEmail(savedUser.getEmail());
        // then
        Assertions.assertThat(result)
                .isPresent()
                .map(User::getEmail)
                .hasValue(user.getEmail());
    }

    private User getSavedUser() {
        return underTest.save(user);
    }
}