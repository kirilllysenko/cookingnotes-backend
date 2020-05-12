package com.kirill.cookingnotes.recipe.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Transactional
@Entity
@Table(name = "categories")
@NoArgsConstructor
@Data
public class Category {
    @Id
    private int id;

    @ToString.Exclude
    @ManyToMany(mappedBy = "categories")
    private List<Recipe> recipes;

    private String name;
}
