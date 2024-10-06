package com.esrx.services.auth.service;

import com.esrx.services.common.entity.UserLoginEmailHistory;
import com.esrx.services.auth.model.UserLoginHistoryStatistics;

import java.util.List;
import java.util.Date;

public interface UserLoginStatisticsService {
    
    // Non-void methods in alphabetical order
    List<UserLoginHistoryStatistics> getAllUserLoginStatistics();
    
    Date getLastRun();
    
    List<Object[]> getInactiveUsers();
    
    long getSuccessfulLoginCount(String userId);
    
    long getSuccessfulLoginCountByEmail(String email);
    
    long getUnsuccessfulLoginCount(String userId);
    
    long getUnsuccessfulLoginCountByEmail(String email);
    
    List<UserLoginEmailHistory> getUserLoginEmailHistoryList();

    // Void methods
    void clearUserLoginEmailHistoryList();
    
    void processEmailList(List<Object[]> emailList);
    
    void recordLoginAttempt(String userId, String email, boolean success);
    
    void updateUserLoginHistory(List<Object> historyList);
}
