package com.kirill.cookingnotes.recipe.entity;

import com.kirill.cookingnotes.profile.entity.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Table(name = "recipes")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int portions;

    @Column(nullable = false)
    private int cookTime;

    private int prepTime;

    @CreatedDate
    private Date date;

    private int rating;

    @ToString.Exclude
    @Column(nullable = false)
    private byte[] image;

    @ToString.Exclude
    @Column(nullable = false)
    private byte[] imagePreview;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ToString.Exclude
    @ManyToMany(mappedBy = "favourites", cascade = CascadeType.DETACH)
    private List<User> favourites;

    @ToString.Exclude
    @ManyToMany(mappedBy = "madeit", cascade = CascadeType.DETACH)
    private List<User> madeit;

    @ManyToMany
    @JoinTable(
            name = "categories_relations",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @Column(nullable = false)
    private boolean isStepsWithImage;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private List<Step> steps;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private List<Ingredient> ingredients;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private List<Comment> comments;
}
