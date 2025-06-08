package com.PGmitra.app.DTO;

import com.PGmitra.app.Enums.PaymentMethod;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private Long tenantId;
    private Long ownerId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private LocalDate dueDate;
}
