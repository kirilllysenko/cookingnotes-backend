package com.kirill.cookingnotes.recipe.entity;

import com.kirill.cookingnotes.profile.entity.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Date;

@Transactional
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "comments")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private int rating;

    @CreatedDate
    private Date date;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}
