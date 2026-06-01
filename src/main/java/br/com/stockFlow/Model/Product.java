package br.com.stockFlow.Model;


import br.com.stockFlow.enums.RolesEnum;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "PRODUCT_ID")
    UUID id;

    @Column(name = "PRODUCT_SKU")
    String productSku;

    @Column(name = "PRODUCT_BARCODE")
    String barcode;

    @Column(name = "PRODUCT_QUANTITY")
    Integer quantity;

    @Column(name = "PRODUCT_CATEGORY")
    String category;


}
