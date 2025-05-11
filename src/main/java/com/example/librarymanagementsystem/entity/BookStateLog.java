package com.example.librarymanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class BookStateLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bookId;
    private String oldState;
    private String newState;
    private Long operatorId;
    private String operatorName;
    private String notes;
}
