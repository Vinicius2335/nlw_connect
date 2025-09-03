package com.github.vinicius2335.connect.api.domain.user;

import com.github.vinicius2335.connect.api.domain.user.requests.UserSubscriptionRequest;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer userId;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    public User(UserSubscriptionRequest request){
        this.name = request.name();
        this.email = request.email();
    }
}
