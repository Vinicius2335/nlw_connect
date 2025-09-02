package com.github.vinicius2335.connect.api.domain.subscription.services;

import com.github.vinicius2335.connect.api.domain.event.Event;
import com.github.vinicius2335.connect.api.domain.event.EventNotFoundException;
import com.github.vinicius2335.connect.api.domain.event.services.FindEventByPrettyNameService;
import com.github.vinicius2335.connect.api.domain.subscription.Subscription;
import com.github.vinicius2335.connect.api.domain.subscription.SubscriptionConflictException;
import com.github.vinicius2335.connect.api.domain.subscription.SubscriptionRepository;
import com.github.vinicius2335.connect.api.domain.subscription.dtos.SubscriptionResponse;
import com.github.vinicius2335.connect.api.domain.user.User;
import com.github.vinicius2335.connect.api.domain.user.UserNotFoundException;
import com.github.vinicius2335.connect.api.domain.user.UserRepository;
import com.github.vinicius2335.connect.api.domain.user.UserSubscriptionRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateSubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final FindEventByPrettyNameService findEventByPrettyNameService;

    @Transactional
    public SubscriptionResponse execute(
            String eventPrettyName,
            UserSubscriptionRequest request,
            Integer indicatorId
    ) throws EventNotFoundException, SubscriptionConflictException, UserNotFoundException {
        Event event = findEventByPrettyNameService.execute(eventPrettyName);

        // se o usuáro não esteja cadastrado, save o usuário
        User subscriber = getSubscriberOrSave(request);

        // se o user já esta escrito no evento laça um conflict
        validateIfSubscriveAlreadyRegistered(subscriber, event);

        // cria o subscription e depois salva se não houver nenhuma exception
        Subscription subscription = new Subscription();
        subscription.setEvent(event);
        subscription.setSubscribe(subscriber);

        validateIfHaveIndication(indicatorId, subscription);

        Subscription subscriptionSaved = subscriptionRepository.save(subscription);
        String destination = "http://localhost:3000/" + subscriptionSaved.getEvent().getPrettyName()
                + "?referrer=" + subscriptionSaved.getSubscribe().getUserId();

        return new SubscriptionResponse(
                subscriptionSaved.getSubscriptionNumber(),
                subscriptionSaved.getSubscribe().getUserId(),
                destination
        );
    }

    private User getSubscriberOrSave(UserSubscriptionRequest request) {
        return userRepository.findByEmail(request.email())
                .orElseGet(() -> {
                    User newUser = new User(request);
                    return userRepository.save(newUser);
                });
    }

    private void validateIfSubscriveAlreadyRegistered(User subscribe, Event event)
            throws SubscriptionConflictException {
        boolean subscribeAlreadyRegistered = subscriptionRepository.findBySubscribeUserIdAndEventEventId(
                        subscribe.getUserId(), event.getEventId()
                )
                .isPresent();

        if (subscribeAlreadyRegistered) {
            throw new SubscriptionConflictException("User " + subscribe.getName() + " is already registered for the event " + event.getTitle());
        }
    }

    private void validateIfHaveIndication(Integer userId, Subscription subscription)
            throws UserNotFoundException {
        if (userId != null) {
            User indication = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found by id: " + userId));
            subscription.setIndication(indication);
        }
    }
}
