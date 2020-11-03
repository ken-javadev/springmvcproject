package com.gvpt.admintool.common.util;

import com.gvpt.admintool.web.common.enumerations.AppConstants;

import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Service(value="email")
public class EmailUtil {
	@Autowired
	private JavaMailSender emailSender;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	private final Logger logger = Logger.getLogger(EmailUtil.class);

	public void sendSimpleMessage(String from, String to, String subject, String text) {
		logger.info("EmailUtil method sendSimpleMessage: start....");
		logger.info("param == [sender : " + from +", recipient : " + to + ", subject : " + subject + ", content : " + text + "]");
		logger.info("EmailUtil method sendSimpleMessage: from = "+from);
		logger.info("EmailUtil method sendSimpleMessage: subject = "+subject);
		logger.info("EmailUtil method sendSimpleMessage: text = "+text);
		logger.info("EmailUtil method sendSimpleMessage: to = "+to);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
		logger.info("EmailUtil method sendSimpleMessage: end....");
	}
	
	public void sendBulkSimpleMessage(String from, List<String> recipients, String subject, String text){
		for (String recipient : recipients) {
			sendSimpleMessage(from, recipient, subject, text);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void sendMimeMessage(Map<String, Object> emailContentParam, String emailTemplate, final String from, String recipients, final String subject) throws Exception{
		final String content = VelocityEngineUtils.mergeTemplateIntoString(
				velocityEngine, emailTemplate,
				emailContentParam);

		logger.info("EmailUtil method sendMimeMessage: start....");
		final InternetAddress[] to = InternetAddress.parse(recipients);
		logger.info("EmailUtil method sendMimeMessage: emailTemplate = "+emailTemplate);
		logger.info("EmailUtil method sendMimeMessage: from = "+from);
		logger.info("EmailUtil method sendMimeMessage: subject = "+subject);
		logger.info("EmailUtil method sendMimeMessage: to = "+to);
		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper messageHelper = new MimeMessageHelper(
						mimeMessage, true);
				messageHelper.setTo(to);
				messageHelper.setSubject(subject);
				messageHelper.setText(content, true);
				messageHelper.setFrom(from);
			}
		};

		emailSender.send(messagePreparator);
		logger.info("EmailUtil method sendMimeMessage: end....");
	}

}
