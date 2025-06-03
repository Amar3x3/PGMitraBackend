package com.PGmitra.app.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

// Define this enum separately or in this file if small
// public enum PaymentStatus { PAID, PENDING, OVERDUE, PARTIALLY_PAID }

@Entity
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    private BigDecimal amount; // CRITICAL: Added amount
    private String status;
    private LocalDate date; // Consider LocalDateTime if time is important
    private String paymentMethod;
    private int paymentForMonth; // e.g., 1 for Jan, 12 for Dec
    private int paymentForYear;  // e.g., 2024
    private LocalDate dueDate;
    private Integer amountPaid;
    private boolean paid;

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Integer amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getPaymentForMonth() {
        return paymentForMonth;
    }

    public void setPaymentForMonth(int paymentForMonth) {
        this.paymentForMonth = paymentForMonth;
    }

    public int getPaymentForYear() {
        return paymentForYear;
    }

    public void setPaymentForYear(int paymentForYear) {
        this.paymentForYear = paymentForYear;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;
}