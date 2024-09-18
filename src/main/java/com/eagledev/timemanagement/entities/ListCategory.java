package com.eagledev.timemanagement.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

    @CreatedDate
    private Instant creationDate;

    @LastModifiedDate
    private Instant modifiedDate;


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "list" , cascade = CascadeType.ALL)
    private Set<Task> tasks;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false , fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
