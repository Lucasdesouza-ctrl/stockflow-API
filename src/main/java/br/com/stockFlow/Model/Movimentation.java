package br.com.stockFlow.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "TB_MOVIMENTATION")
public class Movimentation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "MOV_ID",  nullable = false)
    UUID movimentationId;

    @Column(name = "MOV_QUANTITY",  nullable = false)
    Integer quantity;

    @Column(name = "MOV_DATE",  nullable = false)
    LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MOV_PRODUCT_ID",  nullable = false)
    Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MOV_USER_ID", nullable = false)
    User user;
}
