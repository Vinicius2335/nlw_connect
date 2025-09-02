package com.github.vinicius2335.connect.api.domain.subscription.services;

import com.github.vinicius2335.connect.api.domain.subscription.dtos.UserSubscriptionRanking;
import com.github.vinicius2335.connect.api.domain.subscription.dtos.SubscriptionRankingItem;
import com.github.vinicius2335.connect.api.domain.user.User;
import com.github.vinicius2335.connect.api.domain.user.UserNotFoundException;
import com.github.vinicius2335.connect.api.domain.user.UserRepository;
import com.github.vinicius2335.connect.api.utils.creator.UserCreator;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.github.vinicius2335.connect.api.utils.FakerUtils.FAKER;
import static org.mockito.Mockito.times;

@DisplayName("Unit test to GenerateRankingByUserService")
@ExtendWith(SpringExtension.class)
class GenerateRankingByUserServiceTest {

    @InjectMocks
    private GenerateRankingByUserService underTest;

    @Mock
    private UserRepository userRepository;
    @Mock
    private GenerateRankingByEventService generateRankingByEventService;

    private User user;

    private Integer userId;
    private String prettyName;
    private UserSubscriptionRanking expected;
    private SubscriptionRankingItem subscriptionRankingItem;

    @BeforeEach
    void setUp() {
        user = UserCreator.mockUser();
        user.setUserId(FAKER.number().randomDigitNotZero());

        userId = user.getUserId();
        prettyName = "pretty-name";

        subscriptionRankingItem = new SubscriptionRankingItem(5L, user.getName());
        expected = new UserSubscriptionRanking(subscriptionRankingItem, 1);
    }

    @SneakyThrows
    @Test
    void execute_returnUserSubscriptionRanking_whenSuccessfully() {
        // given
        Mockito.when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));
        Mockito.when(generateRankingByEventService.execute(prettyName))
                .thenReturn(List.of(subscriptionRankingItem));

        // when
        UserSubscriptionRanking result = underTest.execute(prettyName, userId);

        // then
        Assertions.assertThat(result)
                .isNotNull()
                .isEqualTo(expected);

        Mockito.verify(userRepository, times(1))
                .findById(userId);
        Mockito.verify(generateRankingByEventService, times(1))
                .execute(prettyName);
    }

    @SneakyThrows
    @Test
    void execute_throwsEventNotFoundException_whenUserNotFoundById() {
        // given
        Mockito.when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        // when
        Assertions.assertThatThrownBy(
                        () -> underTest.execute(prettyName, userId)
                ).isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found by id: " + userId);

        // then
        Mockito.verify(userRepository, times(1))
                .findById(userId);
        Mockito.verify(generateRankingByEventService, times(0))
                .execute(prettyName);
    }

    @SneakyThrows
    @Test
    void execute_throwsEventNotFoundException_whenUserNoEntriesWithUserIndications() {
        // given
        user.setName("no entries");

        Mockito.when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));
        Mockito.when(generateRankingByEventService.execute(prettyName))
                .thenReturn(List.of(subscriptionRankingItem));

        // when
        Assertions.assertThatThrownBy(
                        () -> underTest.execute(prettyName, userId)
                ).isInstanceOf(UserNotFoundException.class)
                .hasMessage("There are no entries with user indications: " + userId);

        // then
        Mockito.verify(userRepository, times(1))
                .findById(userId);
        Mockito.verify(generateRankingByEventService, times(1))
                .execute(prettyName);
    }
}