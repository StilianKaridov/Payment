package com.tinqin.payment.restexport;

import com.tinqin.payment.api.PaymentRequest;
import com.tinqin.payment.api.PaymentResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestBody;

@Headers({
        "Content-Type: application/json"
})
public interface PaymentRestClient {

    @RequestLine("POST /api/payment/process")
    PaymentResponse processPayment(@RequestBody PaymentRequest paymentRequest);
}
