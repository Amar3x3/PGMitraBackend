package com.PGmitra.app.Service;

import com.PGmitra.app.DTO.PaymentDTO;
import com.PGmitra.app.Entity.Owner;
import com.PGmitra.app.Entity.Payment;
import com.PGmitra.app.Entity.Room;
import com.PGmitra.app.Entity.Tenant;
import com.PGmitra.app.Enums.Status;
import com.PGmitra.app.Exception.ResourceNotFoundException;
import com.PGmitra.app.Repository.PaymentRepo;
import com.PGmitra.app.Repository.TenantRepo;
import com.PGmitra.app.Repository.VenderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private TenantRepo tenantRepo;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private VenderRepo ownerRepo;

    public Payment createPayment(PaymentDTO paymentDTO) {

        Tenant tenant = tenantRepo.findById(paymentDTO.getTenantId())
            .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + paymentDTO.getTenantId()));
        
        Owner owner = ownerRepo.findById(paymentDTO.getOwnerId())
            .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + paymentDTO.getOwnerId()));


        Payment payment = new Payment();
        payment.setTenant(tenant);
        payment.setOwner(owner);
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setDueDate(paymentDTO.getDueDate());
        payment.setStatus(Status.INCOMPLETE);
        payment.setPaidDate(null);

        return paymentRepo.save(payment);
    }

    public List<Payment> getPaymentsByTenant(Long tenantId) {
        Tenant tenant = tenantRepo.findById(tenantId)
            .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));
        return paymentRepo.findByTenant(tenant);
    }

    public List<Payment> getPaymentsByOwner(Long ownerId) {
        Owner owner = ownerRepo.findById(ownerId)
            .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + ownerId));
        return paymentRepo.findByOwner(owner);
    }

    public Payment updatePaymentStatus(Long paymentId, Status status) {
        Payment payment = paymentRepo.findById(paymentId)
            .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + paymentId));
        
        payment.setStatus(status);
        if (status == Status.COMPLETE) {
            payment.setPaidDate(LocalDate.now());
        }
        
        return paymentRepo.save(payment);
    }

    public List<Payment> createPaymentForOwnersTenants(Long ownerId, LocalDate duDate) {
        Owner owner = ownerRepo.findById(ownerId).orElseThrow(() -> new ResourceNotFoundException("Onwer not found with ID: " + ownerId));
        List<Tenant> tenants = tenantService.getAllTenants(ownerId);
        List<Payment> createdPayments = new ArrayList<>();
        if (tenants == null) {
            throw new ResourceNotFoundException("no tenants found for owner with ID:" + ownerId);
        }

        YearMonth dueMonth = YearMonth.from(duDate);

        for (Tenant tenant : tenants) {
            boolean alreadyExists = paymentRepo.existsByTenantIdAndMonth(tenant.getId(), dueMonth);
            Room room = tenant.getRoom();
            if (!alreadyExists) {
                Payment payment = new Payment();
                payment.setDueDate(duDate);
                payment.setOwner(owner);
                payment.setStatus(Status.INCOMPLETE);
                payment.setTenant(tenant);
                BigDecimal rentAmt = room.getRent().divide(BigDecimal.valueOf(room.getCapacity()),2, RoundingMode.HALF_UP);
                payment.setAmount(rentAmt);
                createdPayments.add(paymentRepo.save(payment));
                
            }
        }
        return createdPayments;
    }
}
