package com.kirill.cookingnotes.recipe.entity;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Transactional
@Table(name="steps")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    private byte[] image;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}
