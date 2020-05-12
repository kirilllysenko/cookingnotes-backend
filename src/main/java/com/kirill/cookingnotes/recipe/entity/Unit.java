package com.kirill.cookingnotes.recipe.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.transaction.Transactional;

@Transactional
@Entity
@Table(name = "units")
@NoArgsConstructor
@Data
public class Unit {
    @Id
    private int id;

    private String name;
}
