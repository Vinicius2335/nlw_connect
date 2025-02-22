package com.github.vinicius2335.connect.api.api.controller;

import com.github.vinicius2335.connect.api.api.openapi.SubscriptionControllerOpenApi;
import com.github.vinicius2335.connect.api.domain.event.EventNotFoundException;
import com.github.vinicius2335.connect.api.domain.subscription.SubscriptionConflictException;
import com.github.vinicius2335.connect.api.domain.subscription.dtos.SubscriptionRankingByUser;
import com.github.vinicius2335.connect.api.domain.subscription.dtos.SubscriptionRankingItem;
import com.github.vinicius2335.connect.api.domain.subscription.dtos.SubscriptionResponse;
import com.github.vinicius2335.connect.api.domain.subscription.services.CreateSubscriptionService;
import com.github.vinicius2335.connect.api.domain.subscription.services.GenerateRankingByEventService;
import com.github.vinicius2335.connect.api.domain.subscription.services.GenerateRankingByUserService;
import com.github.vinicius2335.connect.api.domain.user.UserNotFoundException;
import com.github.vinicius2335.connect.api.domain.user.UserSubscriptionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/subscriptions")
@RestController
@Log4j2
public class SubscriptionController implements SubscriptionControllerOpenApi {
    private final CreateSubscriptionService createSubscriptionService;
    private final GenerateRankingByEventService generateRankingByEventService;
    private final GenerateRankingByUserService generateRankingByUserService;

    /**
     * Cria uma nova inscrição para um evento especificado pelo nome amigável.
     *
     * @param prettyName O nome amigável do evento para o qual a inscrição será criada.
     * @param userId O ID do usuário que indicou a inscrição, opcional.
     * @param request Os detalhes da inscrição do usuário, incluindo nome e e-mail.
     * @return ResponseEntity contendo a resposta da inscrição criada.
     * @throws EventNotFoundException Se o evento não for encontrado pelo nome amigável.
     * @throws SubscriptionConflictException Se o usuário já estiver inscrito no evento.
     * @throws UserNotFoundException Se o usuário indicado não for encontrado.
     */
    @PostMapping({"/{prettyName}", "/{prettyName}/{userId}"})
    public ResponseEntity<SubscriptionResponse> createSubscription(
            @PathVariable String prettyName,
            @PathVariable(required = false) Integer userId,
            @RequestBody UserSubscriptionRequest request
    ) throws EventNotFoundException, SubscriptionConflictException, UserNotFoundException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createSubscriptionService.execute(prettyName, request, userId));
    }

    /**
     * Gera o ranking dos três principais inscritos para um evento especificado pelo nome amigável.
     *
     * @param prettyName O nome amigável do evento para o qual o ranking será gerado.
     * @return ResponseEntity contendo uma lista dos três principais itens de ranking de inscrição.
     * @throws EventNotFoundException Se o evento não for encontrado pelo nome amigável.
     */
    @GetMapping("/{prettyName}/ranking")
    public ResponseEntity<List<SubscriptionRankingItem>> generateRankingByEvent(
            @PathVariable String prettyName
    ) throws EventNotFoundException {
        return ResponseEntity.ok(generateRankingByEventService.execute(prettyName).subList(0, 3));
    }

    /**
     * Gera o ranking de um usuário específico para um evento especificado pelo nome amigável.
     *
     * @param prettyName O nome amigável do evento para o qual o ranking será gerado.
     * @param userId O ID do usuário para o qual o ranking será gerado.
     * @return ResponseEntity contendo o ranking do usuário especificado.
     * @throws UserNotFoundException Se o usuário não for encontrado pelo ID fornecido.
     * @throws EventNotFoundException Se o evento não for encontrado pelo nome amigável.
     */
    @GetMapping("/{prettyName}/ranking/{userId}")
    public ResponseEntity<SubscriptionRankingByUser> generateRankingByUser(
            @PathVariable String prettyName,
            @PathVariable Integer userId
    ) throws UserNotFoundException, EventNotFoundException {
        return ResponseEntity.ok(generateRankingByUserService.execute(prettyName, userId));
    }

    // Endpoint para ajudar a popular o Bando de Dados
    // Ajudar nos testes da funcionalidade de Ranking por exemplo
    @PostMapping("/{prettyName}/{userId}/test/addAll")
    public ResponseEntity<List<SubscriptionResponse>> createMultipleSubscriptions(
            @PathVariable String prettyName,
            @PathVariable(required = false) Integer userId,
            @RequestBody List<UserSubscriptionRequest> requests
    ) {
        List<SubscriptionResponse> responseList = new ArrayList<>();

        requests.forEach(request -> {
            SubscriptionResponse response = null;
            try {
                response = createSubscriptionService.execute(prettyName, request, userId);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            responseList.add(response);
        });

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseList);
    }
}
