package com.PGmitra.app.Service;

import com.PGmitra.app.Entity.Payment;
import com.PGmitra.app.Entity.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPaymentReminder(Tenant tenant, Payment payment, int daysUntilDue) {
        String subject = "Rent Payment Reminder";
        String message = buildReminderMessage(tenant, payment, daysUntilDue);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(tenant.getEmail());
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

    private String buildReminderMessage(Tenant tenant, Payment payment, int daysUntilDue) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dueDate = payment.getDueDate().format(formatter);
        String amount = payment.getAmount().toString();

        String greeting = "Dear " + tenant.getName() + ",\n\n";
        String reminderText;
        
        if (daysUntilDue == 0) {
            reminderText = "This is a reminder that your rent payment of Rs. " + amount + 
                         " is due TODAY (" + dueDate + "). Please make the payment as soon as possible.\n\n";
        } else {
            reminderText = "This is a reminder that your rent payment of Rs. " + amount + 
                         " is due in " + daysUntilDue + " days (" + dueDate + "). Please ensure timely payment.\n\n";
        }

        String paymentInfo = "Payment Details:\n" +
                           "Amount: Rs. " + amount + "\n" +
                           "Due Date: " + dueDate + "\n" +
                           "Payment Method: " + payment.getPaymentMethod() + "\n\n";

        String closing = "Thank you for your attention to this matter.\n\n" +
                        "Best regards,\n" +
                        "PGMitra Team";

        return greeting + reminderText + paymentInfo + closing;
    }
}
