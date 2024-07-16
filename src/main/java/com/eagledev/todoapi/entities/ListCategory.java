package com.eagledev.todoapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String category;

    @Column(columnDefinition = "TEXT")
    String description;

    @OneToMany(mappedBy = "list" , cascade = CascadeType.ALL)
    List<Task> tasks;

    @JsonIgnore
    @ManyToOne(optional = false , cascade = CascadeType.PERSIST , fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    User user;
}
