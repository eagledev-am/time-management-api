package com.eagledev.todoapi.entities;

import com.eagledev.todoapi.entities.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(updatable = false)
    private LocalDateTime creationDate;

    private LocalDateTime dueDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "list_id")
    private ListCategory list;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = {CascadeType.PERSIST , CascadeType.REMOVE})
    @JoinColumn(name = "task_id")
    private Set<Comment> comments;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = {CascadeType.PERSIST , CascadeType.REMOVE})
    @JoinColumn(name = "task_id")
    private Set<Attachment> attachments;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = {CascadeType.PERSIST , CascadeType.REMOVE})
    @JoinColumn(name = "task_id")
    private Set<Tag> tags;



}
