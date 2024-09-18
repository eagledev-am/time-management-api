package com.eagledev.timemanagement.entities;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.HashCodeExclude;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ToString.Exclude
    @HashCodeExclude
    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment")
    private Set<Comment> replies;

    @ToString.Exclude
    @HashCodeExclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" )
    User user;
}
