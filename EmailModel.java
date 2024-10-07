package com.yourcompany.model;

import javax.persistence.*;
import java.io.Serializable;

// INCORRECT MODEL - this is from DB which is not used here in this project
// find other model
@Entity
public class Email implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    
    @Column(length = 1000)
    private String body;
    
    @ManyToOne
    private Receiver receiver;
    
    @Enumerated(EnumType.STRING)
    private EmailType type;

    // Getters and setters
}

public enum EmailType {
    NOTIFICATION, REMINDER, NEWSLETTER, CUSTOM
}
