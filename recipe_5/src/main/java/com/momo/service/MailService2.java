package com.momo.service;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailService2 {		

	@Autowired
    private JavaMailSender javaMailSender;

    public String generateRandomCode(int length) {
        // 랜덤한 숫자 코드 생성
        String characters = "0123456789";
        StringBuilder code = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }

    public void sendVerificationCodeByEmail(String recipientEmail, String verificationCode) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipientEmail);
            helper.setSubject("인증번호 발급");
            String emailContent = "인증번호: " + verificationCode;
            helper.setText(emailContent, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
	
//	public void welcomeMailSend() {
//		// 메일 설정 정보
//		Properties prop = System.getProperties();
//		prop.put("mail.smtp.starttls.enable", "true"); // 로그인시 TLS를 사용할 것인지 설정
//		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");	
//		prop.put("mail.smtp.host", "smtp.gmail.com");// SMTP서버
//		prop.put("mail.smtp.auth", "true");// SMTP 서버의 인증 사용
//		prop.put("mail.smtp.port", "587");// TLS 포트번호= 587, SSL 포트번호= 465
//		
//		String mail_id = "아이디";
//		String mail_pw = "앱 비밀번호";
//		
//		// 구글 계정 인증용 ID/PW 세팅
//		Authenticator auth = new MailAuth(mail_id, mail_pw);
//		// 세션 및 메세지 생성 (프로퍼티, 인증)
//		Session session = Session.getDefaultInstance(prop, auth);
//		MimeMessage msg = new MimeMessage(session);
//		
//		try {
//			// 보내는 날짜 지정
//			msg.setSentDate(new Date());
//			// 발송자 설정 (발송자의 메일, 발송자명)
//			msg.setFrom(new InternetAddress("nasajang2020@gmail.com", "나사장"));
//			// 수신자 설정 
//			// Message.RecipientType.TO : 받는 사람 
//			InternetAddress to = new InternetAddress("nasajang2020@gmail.com");
//			msg.setRecipient(Message.RecipientType.TO, to);
//			
//			// 메일 제목
//			msg.setSubject("환영합니다.", "UTF-8");
//			// 메일 내용
//			msg.setText("가입을 축하드립니다.\n인증번호는 1234 입니다.", "UTF-8");
//			
//			// 메일 발송
//			Transport.send(msg);
//			
//		} catch (AddressException ae) {// 주소를 입력하지 않았을 경우
//			System.out.println("AddressException : " + ae.getMessage());
//		} catch (MessagingException me) {// 메세지에 이상이 있을 경우
//			System.out.println("MessagingException : " + me.getMessage());
//		} catch (UnsupportedEncodingException e) {
//			System.out.println("UnsupportedEncodingException : " + e.getMessage());
//		}
//	}
//}
//
//class MailAuth extends Authenticator {
//	
//	PasswordAuthentication pa;
//	
//	public MailAuth(String mail_id, String mail_pw) {
//		// 사용자 인증 정보를 담아서 반환
//		pa = new PasswordAuthentication(mail_id, mail_pw);
//	}
//	
//	public PasswordAuthentication getPasswordAuthentication() {
//		return pa;
//	}
//}




//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.momo.vo.Member;
//
//import java.util.Properties;
//import java.util.Random;

//@Service
//public class MailService {
//
//    private static final String SENDER_EMAIL = "your_sender_email@gmail.com";
//    private static final String SENDER_PASSWORD = "your_sender_email_password";
//
//    private static final String SMTP_HOST = "smtp.gmail.com";
//    private static final int SMTP_PORT = 587;
//
//    private static final int TEMP_PASSWORD_LENGTH = 10; // 임시 비밀번호 길이
//
//    @Autowired
//    private MemberService service;
//
//    public String generateTemporaryPassword() {
//        // 임시 비밀번호를 랜덤하게 생성하여 반환
//        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//        Random random = new Random();
//        StringBuilder sb = new StringBuilder(TEMP_PASSWORD_LENGTH);
//        for (int i = 0; i < TEMP_PASSWORD_LENGTH; i++) {
//            sb.append(characters.charAt(random.nextInt(characters.length())));
//        }
//        return sb.toString();
//    }
//
//    public void sendTemporaryPasswordByEmail(String email) {
//        Member member = service.sendPwBy(member);
//        if (member != null) {
//            String temporaryPassword = generateTemporaryPassword();
//            member.setPassword(temporaryPassword);
//            service.save(member);
//
//            sendEmail(email, "임시 비밀번호 발급", "임시 비밀번호: " + temporaryPassword);
//        }
//    }
//
//    private void sendEmail(String recipient, String subject, String body) {
//        // 메일 설정
//        Properties properties = new Properties();
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");
//        properties.put("mail.smtp.host", SMTP_HOST);
//        properties.put("mail.smtp.port", SMTP_PORT);
//
//        // 보내는 사람 계정 인증
//        Authenticator authenticator = new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
//            }
//        };
//
//        // 세션 및 메세지 생성
//        Session session = Session.getInstance(properties, authenticator);
//        Message message = new MimeMessage(session);
//
//        try {
//            message.setFrom(new InternetAddress(SENDER_EMAIL));
//            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
//            message.setSubject(subject);
//            message.setText(body);
//            Transport.send(message);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }
//}


//@Service
//public class MailService {
//	
//	
//
//    @Value("${spring.mail.username}")
//    private String fromEmail;
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    public void sendTemporaryPassword(Member member, String temporaryPassword) {
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        //mailMessage.setTo(recipientEmail);
//        mailMessage.setFrom(fromEmail);
//        mailMessage.setSubject("임시 비밀번호 발급");
//        mailMessage.setText("임시 비밀번호: " + temporaryPassword);
//
//        javaMailSender.send(mailMessage);
//    }
//}


