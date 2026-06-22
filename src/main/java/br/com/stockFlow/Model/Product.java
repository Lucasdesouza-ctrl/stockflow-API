package br.com.stockFlow.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "TB_PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PROD_ID")
    UUID id;

    @Column(name = "PROD_NAME")
    String name;

    @Column(name = "PROD_SKU", nullable = false)
    String sku;

    @Column(name = "PROD_BARCODE", nullable = false)
    String barcode;

    @Column(name = "PROD_QUANTITY", nullable = false)
    Integer quantity;

    @Column(name = "PROD_CATEGORY", nullable = false)
    String category;
}
