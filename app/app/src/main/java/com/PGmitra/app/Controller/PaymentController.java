package com.PGmitra.app.Controller;

import com.PGmitra.app.Entity.Payment;
import com.PGmitra.app.Service.PaymentService;
import com.PGmitra.app.Response.PaymentResponse;
import com.PGmitra.app.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/pending")
    public ResponseEntity<List<PaymentResponse>> getPendingPayments() {
        try {
            List<Payment> pendingPayments = paymentService.getPendingPayments();
            List<PaymentResponse> responses = new ArrayList<>();
            
            for(Payment payment : pendingPayments) {
                responses.add(new PaymentResponse(
                    payment.getId(),
                    payment.getTenant().getName(),
                    payment.getAmount(),
                    payment.getStatus(),
                    payment.getDueDate(),
                    payment.getTenant().getRoom().getRoom_no(),
                    payment.getTenant().getRoom().getProperty().getName()
                ));
            }
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/complete/{paymentId}")
    public ResponseEntity<Payment> completePayment(@PathVariable Long paymentId) {
        try {
            Payment updatedPayment = paymentService.completePayment(paymentId);
            return ResponseEntity.ok(updatedPayment);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
} 