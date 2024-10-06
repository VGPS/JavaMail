package com.esrx.services.auth.service;

import com.esrx.services.common.repository.UserLoginStatisticsRepository;
import com.esrx.services.common.entity.UserLoginEmailHistory;
import com.esrx.services.auth.model.UserLoginHistoryStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * ToDo: Review this class and determine which methods are needed and what needs to be added. 
 * Create a test class after that is done.
 * 
 * ToDo: Create a method called from a Controller (to be created) and/or a trigger to kick off a process
 * that sends emails out to people who have not logged in more than 75 days AND/OR 86 Days according to requirements.
 */
@Service
public class UserLoginStatisticsServiceImpl implements UserLoginStatisticsService {

    private final UserLoginStatisticsRepository repository;
    
    private List<UserLoginEmailHistory> userLoginEmailHistoryList;

    @Autowired
    public UserLoginStatisticsServiceImpl(UserLoginStatisticsRepository repository) {
        this.repository = repository;
        this.userLoginEmailHistoryList = new ArrayList<>();
    }
    
	/**
	UserLoginStatisticsServiceImpl.startProcess()
		UserLoginStatisticsServiceImpl.getInactiveUsers() ->		
		UserLoginStatisticsServiceImpl.processEmailList() ->		
		UserLoginStatisticsServiceImpl.updateUserLoginHistory(List<Object> historyList)
	**/
	
	public int startProcess() {
		List<Object[]> inactiveUsers = getInactiveUsers();
		List<Object> historyList = processEmailList(inactiveUsers);
		updateUserLoginHistory(List<Object> historyList);
	}	
	
	@Override
    public void updateUserLoginHistory(List<Object> historyList) {
        repository.updateUserLoginHistory(historyList);
    }
	
	@Override
    public List<Object[]> getInactiveUsers() {
        // This is a placeholder implementation. Replace with actual logic to fetch inactive users.
        return repository.findInactiveUsers(75); // Assuming 75 days of inactivity
    }
	
	/**
	ToDo: return collection of sent mail with success/fail statuses
	and combine into single processedList collection. Return
	processedList;
	**/
	
	@Override
    public List<Object> processEmailList(List<Object[]> emailList) {
		List<Object> processedList;
		List<Object> processed76List;
		List<Object> processed87List;
		
        for (Object[] user : emailList) {
            String userId = (String) user[0];
            String email = (String) user[1];
            java.sql.Date lastLogin = (java.sql.Date) user[2];
            int daysElapsed = (int) user[3];

            if (daysElapsed > 86) {
				
				// create new EmailRequest here and send it over...
				
				
                processed87List = sendEmail_87(user);
				
            } else if (daysElapsed >= 75 && daysElapsed <= 87) {
				
				
                processed76List = sendEmail_76(user);
            }
        }
		
		// best way to merge lists?
		return processedList;
    }
	
	private List<Object> sendEmail_87(Object[] userDetails) {
		List<Object> processedList;
        // Implementation for sending email for users inactive for more than 86 days
        String userId = (String) userDetails[0];
        String email = (String) userDetails[1];
		
        // TODO: Implement email sending logic
		
		// build out emailRequest here 
		EmailRequest emailRequest = new EmailRequest();
		// required fields:
		// String fullname
		// String contacttype
		// String contactcategory;
		// String email
		// String contactme (false or 0??)
		// String comments
		// 
		
		
		
		boolean retVal = emailService.sendEmail(emailRequest);
        System.out.println("Sending 87-day inactivity email to user " + userId + " at " + email);
		return processedList;
    }

    private List<Object> sendEmail_76(Object[] userDetails) {
		List<Object> processedList;
        // Implementation for sending email for users inactive between 75 and 87 days
        String userId = (String) userDetails[0];
        String email = (String) userDetails[1];
        // TODO: Implement email sending logic
        System.out.println("Sending 76-day inactivity email to user " + userId + " at " + email);
		return procesedList;
    }
	
	
	

    @Override
    public List<UserLoginHistoryStatistics> getAllUserLoginStatistics() {
        List<UserLoginHistoryStatistics> statistics = new ArrayList<>();
        List<Object[]> successfulLogins = repository.countSuccessfulLoginsByUserid();
        List<Object[]> unsuccessfulLogins = repository.countUnsuccessfulLoginsByUserid();

        // Combine successful and unsuccessful login counts
        // This is a simplified approach and might need optimization for large datasets
        for (Object[] successLogin : successfulLogins) {
            String userId = (String) successLogin[0];
            long successCount = (Long) successLogin[1];
            long unsuccessCount = 0;

            for (Object[] unsuccessLogin : unsuccessfulLogins) {
                if (userId.equals(unsuccessLogin[0])) {
                    unsuccessCount = (Long) unsuccessLogin[1];
                    break;
                }
            }

            statistics.add(new UserLoginHistoryStatistics(userId, null, successCount, unsuccessCount));
        }

        return statistics;
    }

    @Override
    public Date getLastRun() {
        return repository.getLatestHistoryDate();
    }

   

    @Override
    public long getSuccessfulLoginCount(String userId) {
        return repository.countSuccessfulLoginsByUserid(userId);
    }

    @Override
    public long getSuccessfulLoginCountByEmail(String email) {
        return repository.countSuccessfulLoginsByEmail(email);
    }

    @Override
    public long getUnsuccessfulLoginCount(String userId) {
        return repository.countUnsuccessfulLoginsByUserid(userId);
    }

    @Override
    public long getUnsuccessfulLoginCountByEmail(String email) {
        return repository.countUnsuccessfulLoginsByEmail(email);
    }

    @Override
    public List<UserLoginEmailHistory> getUserLoginEmailHistoryList() {
        return new ArrayList<>(userLoginEmailHistoryList);
    }

    // Void methods

    @Override
    public void clearUserLoginEmailHistoryList() {
        userLoginEmailHistoryList.clear();
    }

   

    @Override
    public void recordLoginAttempt(String userId, String email, boolean success) {
        UserLoginEmailHistory loginAttempt = new UserLoginEmailHistory();
        loginAttempt.setUserid(userId);
        loginAttempt.setEmail(email);
        loginAttempt.setLastlogin(OffsetDateTime.now());
        loginAttempt.setSuccess(success);
        repository.save(loginAttempt);
        userLoginEmailHistoryList.add(loginAttempt);
    }

    @Override
    public void updateUserLoginHistory(List<Object> historyList) {
        repository.updateUserLoginHistory(historyList);
    }

    // Private helper methods

    
}
