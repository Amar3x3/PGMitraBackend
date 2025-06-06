package com.PGmitra.app.Service;

import com.PGmitra.app.Entity.Payment;
import com.PGmitra.app.Repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepo paymentRepo;

//    public Payment createNewInvoice(Payment request){
//
//    }
}
