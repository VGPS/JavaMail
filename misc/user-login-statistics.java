package com.esrx.services.auth.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * ToDo: Create a method called from a Controller (to be created) and/or a trigger to kick off a process
 * that sends emails out to people who have not logged in more than 75 days AND/OR 86 Days according to requirements.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginStatistics {
    private String userId;
    private String email;
    private long successfulLogins;
    private long unsuccessfulLogins;
}
