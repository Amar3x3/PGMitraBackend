package com.PGmitra.app.Response;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.PGmitra.app.Enums.Status;

public record PaymentResponse(
    Long id,
    String tenantName,
    BigDecimal amount,
    Status status,
    LocalDate dueDate,
    int roomNo,
    String propertyName
) {}
