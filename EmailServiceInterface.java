package com.yourcompany.service;

import com.yourcompany.model.Email;

// add method to send multiple emails for batch contained in an ArrayList of typee
public interface EmailService {
    void sendEmail(Email email);
}
