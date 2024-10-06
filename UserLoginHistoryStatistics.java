package com.esrx.services.auth.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * NOTE: Usage example:
 * UserLoginHistoryStatistics stats = new UserLoginHistoryStatistics("user123", "user@example.com", 10, 2);
 * System.out.println("User ID: " + stats.getUserId());
 * System.out.println("Email: " + stats.getEmail());
 * System.out.println("Successful Logins: " + stats.getSuccessfulLogins());
 * System.out.println("Unsuccessful Logins: " + stats.getUnsuccessfulLogins());
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginHistoryStatistics {
    private String userId;
    private String email;
    private long successfulLogins;
    private long unsuccessfulLogins;
}
