package com.kirill.cookingnotes.recipe.entity;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.math.BigDecimal;

@Transactional
@Entity
@Data
@Table(name="ingredients")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Column(nullable = false)
    private BigDecimal amount;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}
