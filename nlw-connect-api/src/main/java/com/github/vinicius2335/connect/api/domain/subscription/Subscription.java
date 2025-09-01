package com.github.vinicius2335.connect.api.domain.subscription;

import com.github.vinicius2335.connect.api.domain.event.Event;
import com.github.vinicius2335.connect.api.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer subscriptionNumber;

    // 1 usuário(one) pode realizar * inscriçoes(many)
    // 1 inscrição pertence a um único usuário
    @ManyToOne(optional = false)
    @JoinColumn(name = "subscribed_user_id", nullable = false)
    private User subscribe;

    // 1 usuário pode realizar 0 ou * indicaçoes
    // 1 inscrição pode ter 0 ou 1 indicação
    @ManyToOne
    @JoinColumn(name = "indication_user_id")
    private User indication;

    // 1 evento pode ter * inscritos
    // cada inscrição pertence a 1 único evento
    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
}
