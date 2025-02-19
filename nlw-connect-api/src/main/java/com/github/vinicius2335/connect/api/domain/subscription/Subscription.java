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

    // 1 usuário pode se inscrever em * eventos diferentes
    @ManyToOne(optional = false)
    @JoinColumn(name = "subscribed_user_id", nullable = false)
    private User subscribe;

    // 1 usuário pode indicar 0 ou * usuários
    @ManyToOne
    @JoinColumn(name = "indication_user_id")
    private User indication;

    // 1 evento possui * usuários inscritos
    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
}
