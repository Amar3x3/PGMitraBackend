package com.PGmitra.app.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.PGmitra.app.Entity.Payment;
import com.PGmitra.app.Entity.Tenant;
import com.PGmitra.app.Entity.Vendor;
import com.PGmitra.app.Repository.PaymentRepo;
import com.PGmitra.app.Repository.TenantRepo;

@Service
public class ReminderScheduler {
    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private TenantRepo tenantRepo;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 9 * * ?")
    public void sendRentReminders(){
        LocalDate today= LocalDate.now();

        List<Payment> unpaidPayments = paymentRepo.findByPaidFalseAndDueDateBefore(today);

        for(Payment payment: unpaidPayments) {
            Tenant tenant = payment.getTenant();
            Vendor vendor = tenant.getVendor_id();

            emailService.sendDynamicEmail(vendor.getEmail(), vendor.getEmailAppPassword(), tenant.getEmail(), "Rent Reminder", "Hi" + tenant.getUsername() + "your rent is due on" + payment.getDueDate());

        }
    }
}
