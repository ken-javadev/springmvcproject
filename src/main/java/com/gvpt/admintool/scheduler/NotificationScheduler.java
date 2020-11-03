package com.gvpt.admintool.scheduler;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

	Logger log = Logger.getLogger(NotificationScheduler.class);
	
	@Scheduled(cron = "0 */1 * * * *")
	public void sendNotification(){
		System.out.println("SCHEDULLER RUNNING..........");
	}

	}
