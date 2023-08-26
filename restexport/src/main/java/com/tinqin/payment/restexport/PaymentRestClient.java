package com.tinqin.payment.restexport;

import com.tinqin.payment.api.PaymentRequest;
import com.tinqin.payment.api.PaymentResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

@Headers({
        "Content-Type: application/json"
})
public interface PaymentRestClient {

    @RequestLine("POST /api/payment/process")
    PaymentResponse processPayment(@Param PaymentRequest paymentRequest);
}
