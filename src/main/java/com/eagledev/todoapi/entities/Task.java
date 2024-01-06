package com.eagledev.todoapi.entities;

import com.eagledev.todoapi.entities.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String title;

    @Column(columnDefinition = "TEXT")
    String description;

    @Enumerated(EnumType.STRING)
    Status status;

    @Column(updatable = false)
    Date creationDate;

    Date dueDate;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn( name = "list_id" , referencedColumnName = "id" , updatable = false)
    ListCategory list;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id" , referencedColumnName = "id" , updatable = false , nullable = false)
    User user;
}
