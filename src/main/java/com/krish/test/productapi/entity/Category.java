package com.krish.test.productapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "category")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
    private Long categoryId;

    private String categoryName;

    private String categoryDescription;

    @OneToMany
    @JoinColumn(name = "item_category")
    private List<Item> itemList;

}