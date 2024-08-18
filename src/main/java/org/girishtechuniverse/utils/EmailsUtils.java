package org.girishtechuniverse.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailsUtils {

	@Autowired
	private JavaMailSender javaMailSender;

	public boolean sendEmail(String subject, String body, String to ) {
		boolean isSent= false;

		MimeMessage createMimeMessage = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(createMimeMessage);

		try {
			helper.setSubject(subject);
			helper.setText(body, true);
			helper.setTo(to);

			javaMailSender.send(createMimeMessage);
			isSent = true;
		} catch (MessagingException e) {
			e.printStackTrace();
		}


		return isSent;
	}
}
