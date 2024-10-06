package com.esrx.services.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

@Service
public class ScheduledTaskService {

    private static final Logger LOGGER = Logger.getLogger(ScheduledTaskService.class.getName());

    @Autowired
    private UserLoginStatisticsService userLoginStatisticsService;

    @Value("${scheduler.frequency.hours:24}")
    private int frequencyHours;

    @Scheduled(fixedRateString = "#{${scheduler.frequency.hours:24} * 60 * 60 * 1000}")
    public void scheduledTask() {
        LOGGER.info("Starting scheduled task to process inactive users");
        try {
            processInactiveUsers();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occurred while processing inactive users", e);
        }
    }

    private void processInactiveUsers() {
        // Fetch the list of inactive users
        List<Object[]> inactiveUsers = userLoginStatisticsService.getInactiveUsers();
        
        // Process the list and send emails
        userLoginStatisticsService.processEmailList(inactiveUsers);
        
        LOGGER.info("Completed processing inactive users. Total processed: " + inactiveUsers.size());
    }
}
