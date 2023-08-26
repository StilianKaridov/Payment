package com.tinqin.payment.rest.controller;

import com.tinqin.payment.api.PaymentOperation;
import com.tinqin.payment.api.PaymentRequest;
import com.tinqin.payment.api.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentOperation paymentOperation;

    @Autowired
    public PaymentController(PaymentOperation paymentOperation) {
        this.paymentOperation = paymentOperation;
    }

    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok(this.paymentOperation.process(paymentRequest));
    }
}
