package br.com.stockFlow.Model;

import br.com.stockFlow.enums.RolesEnum;
import jakarta.persistence.*;

import java.util.UUID;

import jakarta.persistence.Id;
import lombok.*;


@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "TB_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID", nullable = false)
    UUID id;

    @Column(name = "USER_NAME", nullable = false)
    String name;

    @Column(name = "USER_PASSWORD", nullable = false)
    String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_ROLE", nullable = false)
    RolesEnum role;
}
