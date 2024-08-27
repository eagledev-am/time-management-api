package com.eagledev.todoapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class ListCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime creationDate;

    private LocalDateTime modifiedDate;

    private String avatarUrl;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "list" , cascade = CascadeType.ALL)
    private List<Task> tasks;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false , cascade = CascadeType.PERSIST , fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
