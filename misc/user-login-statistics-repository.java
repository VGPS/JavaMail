package com.esrx.services.common.repository;

import com.esrx.services.common.entity.UserLoginEmailHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Date;

@Repository
public interface UserLoginStatisticsRepository extends JpaRepository<UserLoginEmailHistory, Long> {

    // Find by userId
    List<UserLoginEmailHistory> findByUserid(String userid);

    // Find by email
    List<UserLoginEmailHistory> findByEmail(String email);

    // Find by lastLogin
    List<UserLoginEmailHistory> findByLastlogin(OffsetDateTime lastlogin);

    // Find by success
    List<UserLoginEmailHistory> findBySuccess(Boolean success);

    // Find by userId and email
    List<UserLoginEmailHistory> findByUseridAndEmail(String userid, String email);

    // Find by userId and success
    List<UserLoginEmailHistory> findByUseridAndSuccess(String userid, Boolean success);

    // Find by email and success
    List<UserLoginEmailHistory> findByEmailAndSuccess(String email, Boolean success);

    // Find by lastLogin after a certain date
    List<UserLoginEmailHistory> findByLastloginAfter(OffsetDateTime date);

    // Find by lastLogin before a certain date
    List<UserLoginEmailHistory> findByLastloginBefore(OffsetDateTime date);

    // Find by lastLogin between two dates
    List<UserLoginEmailHistory> findByLastloginBetween(OffsetDateTime startDate, OffsetDateTime endDate);

    // Find by userId containing a certain string (case-insensitive)
    List<UserLoginEmailHistory> findByUseridContainingIgnoreCase(String useridPart);

    // Find by email containing a certain string (case-insensitive)
    List<UserLoginEmailHistory> findByEmailContainingIgnoreCase(String emailPart);

    // Find top N entries ordered by lastLogin descending
    List<UserLoginEmailHistory> findTopNByOrderByLastloginDesc(@Param("n") int n);

    // Custom query to find entries with successful logins count greater than a specified number
    @Query("SELECT u FROM UserLoginEmailHistory u WHERE u.success = true GROUP BY u.userid HAVING COUNT(u) > :count")
    List<UserLoginEmailHistory> findUsersWithSuccessfulLoginsMoreThan(@Param("count") long count);

    // Count successful logins for each email address
    @Query("SELECT u.email, COUNT(u) FROM UserLoginEmailHistory u WHERE u.success = true GROUP BY u.email")
    List<Object[]> countSuccessfulLoginsByEmail();

    // Count successful logins for each userid
    @Query("SELECT u.userid, COUNT(u) FROM UserLoginEmailHistory u WHERE u.success = true GROUP BY u.userid")
    List<Object[]> countSuccessfulLoginsByUserid();

    // Count successful logins for a specific email address
    @Query("SELECT COUNT(u) FROM UserLoginEmailHistory u WHERE u.email = :email AND u.success = true")
    long countSuccessfulLoginsByEmail(@Param("email") String email);

    // Count successful logins for a specific userid
    @Query("SELECT COUNT(u) FROM UserLoginEmailHistory u WHERE u.userid = :userid AND u.success = true")
    long countSuccessfulLoginsByUserid(@Param("userid") String userid);

    // Count unsuccessful logins for each email address
    @Query("SELECT u.email, COUNT(u) FROM UserLoginEmailHistory u WHERE u.success = false GROUP BY u.email")
    List<Object[]> countUnsuccessfulLoginsByEmail();

    // Count unsuccessful logins for each userid
    @Query("SELECT u.userid, COUNT(u) FROM UserLoginEmailHistory u WHERE u.success = false GROUP BY u.userid")
    List<Object[]> countUnsuccessfulLoginsByUserid();

    // Count unsuccessful logins for a specific email address
    @Query("SELECT COUNT(u) FROM UserLoginEmailHistory u WHERE u.email = :email AND u.success = false")
    long countUnsuccessfulLoginsByEmail(@Param("email") String email);

    // Count unsuccessful logins for a specific userid
    @Query("SELECT COUNT(u) FROM UserLoginEmailHistory u WHERE u.userid = :userid AND u.success = false")
    long countUnsuccessfulLoginsByUserid(@Param("userid") String userid);

    /**
     * Retrieves the most recent login date from the USER_LOGIN_EMAIL_HISTORY table.
     * @return the most recent login date, or null if the table is empty
     */
    @Query("SELECT MAX(u.lastlogin) FROM UserLoginEmailHistory u")
    Date getLatestHistoryDate();

    @Query("SELECT u.userid, u.email, u.lastlogin, DATEDIFF(CURRENT_DATE, u.lastlogin) as daysInactive " +
           "FROM UserLoginEmailHistory u " +
           "WHERE DATEDIFF(CURRENT_DATE, u.lastlogin) >= :days")
    List<Object[]> findInactiveUsers(@Param("days") int days);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO USER_LOGIN_EMAIL_HISTORY (userid, email, lastlogin, success) " +
           "VALUES (:#{#history.userid}, :#{#history.email}, :#{#history.lastlogin}, :#{#history.success})",
           nativeQuery = true)
    void insertUserLoginHistory(@Param("history") UserLoginEmailHistory history);

    @Modifying
    @Transactional
    default void updateUserLoginHistory(List<Object> historyList) {
        for (Object obj : historyList) {
            if (obj instanceof UserLoginEmailHistory) {
                UserLoginEmailHistory history = (UserLoginEmailHistory) obj;
                insertUserLoginHistory(history);
            }
        }
    }
}
