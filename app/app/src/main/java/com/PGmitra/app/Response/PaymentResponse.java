package com.PGmitra.app.Response;

import com.PGmitra.app.Enums.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentResponse(Long id, String tenantName, BigDecimal amount, Status status, LocalDate dueDate) {
}
