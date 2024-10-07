package com.wgblackmon.model;

// import javax.persistence.*;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;


// @Entity
public class Email implements Serializable {
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    
    // @Column(length = 1000)
    private String body;
    
    // @ManyToOne
    private String receiver;
    
    @Enumerated(EnumType.STRING)
    private EmailType type;


}

