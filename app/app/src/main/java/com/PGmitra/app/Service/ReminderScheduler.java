package com.PGmitra.app.Service;

import com.PGmitra.app.Entity.Payment;
import com.PGmitra.app.Entity.Tenant;
import com.PGmitra.app.Enums.Status;
import com.PGmitra.app.Repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReminderScheduler {

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private EmailService emailService;

    // Run at 9 AM every day
    @Scheduled(cron = "0 0 9 * * ?")
    public void sendPaymentReminders() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate dayAfterTomorrow = today.plusDays(2);

        // Get all incomplete payments
        List<Payment> incompletePayments = paymentRepo.findByStatus(Status.INCOMPLETE);

        for (Payment payment : incompletePayments) {
            LocalDate dueDate = payment.getDueDate();
            Tenant tenant = payment.getTenant();

           
            if (dueDate.equals(today)) {
                
                emailService.sendPaymentReminder(tenant, payment, 0);
            } else if (dueDate.equals(tomorrow)) {
               
                emailService.sendPaymentReminder(tenant, payment, 1);
            } else if (dueDate.equals(dayAfterTomorrow)) {
               
                emailService.sendPaymentReminder(tenant, payment, 2);
            }
        }
    }
}
