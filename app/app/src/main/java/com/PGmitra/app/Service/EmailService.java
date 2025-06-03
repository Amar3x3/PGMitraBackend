//package com.PGmitra.app.Service;
//
//import jakarta.mail.Authenticator;
//import jakarta.mail.PasswordAuthentication;
//import java.util.Properties;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.PGmitra.app.Entity.Payment;
//
//import jakarta.mail.Message;
//import jakarta.mail.MessagingException;
//import jakarta.mail.Session;
//import jakarta.mail.Transport;
//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeMessage;
//
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//
//@Service
//
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    public void sendReminderEmail(String toEmail, String tenantName, Payment payment) {
//        String subject = "Rent Payment Reminder";
//
//        String body = "Dear" + tenantName + ",\n\n" + "This is a reminder that your rent of Rs" + payment.getAmountPaid() + "was due on " + payment.getDueDate() + ". Please make the payment at the earliest.\n\n" + "Thank you,\nPGMitra";
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(toEmail);
//        message.setSubject(subject);
//        message.setText(body);
//
//        mailSender.send(message);
//    }
//
//    public void sendDynamicEmail(String fromEmail, String appPassword, String toEmail, String subject, String body) {
//        Properties props = new Properties();
//
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//
//        Session session = Session.getInstance(props, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(fromEmail, appPassword);
//            }
//        });
//        try{
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(fromEmail));
//
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
//
//            message.setSubject(subject);
//            message.setText(body);
//
//            Transport.send(message);
//
//        }
//        catch (MessagingException e) {
//            e.printStackTrace();
//        }
//
//
//
//    }
//}
