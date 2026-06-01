package br.com.stockFlow.Model;

import br.com.stockFlow.enums.RolesEnum;
import jakarta.persistence.*;

import java.util.UUID;

import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;


@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "TB_USER")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    UUID id;

    @Column(name = "USER_NAME")
     String name;

    @Column(name = "USER_PASSWORD")
     String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_ROLE")
    RolesEnum role;
}
