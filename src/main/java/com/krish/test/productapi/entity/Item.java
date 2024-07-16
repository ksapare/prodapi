package com.krish.test.productapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {

    @Id
    private Long itemId;

    private String itemName;

    private String itemDescription;

    private float itemPrice;
}