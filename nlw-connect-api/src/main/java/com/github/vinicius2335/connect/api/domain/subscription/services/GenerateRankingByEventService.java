package com.github.vinicius2335.connect.api.domain.subscription.services;

import com.github.vinicius2335.connect.api.domain.event.Event;
import com.github.vinicius2335.connect.api.domain.event.exceptions.EventNotFoundException;
import com.github.vinicius2335.connect.api.domain.event.services.FindEventByPrettyNameService;
import com.github.vinicius2335.connect.api.domain.subscription.SubscriptionRepository;
import com.github.vinicius2335.connect.api.domain.subscription.dtos.SubscriptionRankingItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenerateRankingByEventService {
    private final SubscriptionRepository subscriptionRepository;
    private final FindEventByPrettyNameService findEventByPrettyNameService;

    /**
     * <p>
     * Retorna uma lista, onde cada item contêm o nome de um usuário e o total de indicações que ele fez,
     * dado um evento específico.
     * </p>
     * <p>
     * OSB: Usuário que não fizeram nenhuma indicação, não são contabilizados
     * </p>
     *
     * @param prettyName nome do evento formatado
     * @return Ranking dos três usuários que mais fizeram indicações
     * @throws EventNotFoundException quando evento não encontrado pelo {@code prettyName}
     */
    public List<SubscriptionRankingItem> execute(String prettyName)
            throws EventNotFoundException {
        Event event = findEventByPrettyNameService.execute(prettyName);

        return subscriptionRepository.generateRanking(event.getEventId());
    }
}
