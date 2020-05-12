package com.kirill.cookingnotes.profile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kirill.cookingnotes.recipe.entity.Recipe;
import com.kirill.cookingnotes.security.AuthProvider;
import lombok.Data;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Transactional
@Entity
@Data
@ToString
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    private byte[] avatar;

    private byte[] cover;

    @Email
    @Column(nullable = false)
    private String email;

    private String firstName;

    private String secondName;

    private String displayName;

    private String country;

    private String city;

    private String about;

    private String blogLink;

    private String twitterLink;

    private String facebookLink;

    private String instagramLink;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Recipe> recipes;

    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(
            name = "favourites_relations",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id"))
    private List<Recipe> favourites;

    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(
            name = "madeit_relations",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id"))
    private List<Recipe> madeit;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(
            name = "followers_relations",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id"))
    private List<User> followers;

    @ManyToMany(mappedBy = "followers")
    private List<User> following;
}