package com.tinqin.payment.api;

import com.tinqin.payment.api.base.OperationRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
public class PaymentRequest implements OperationRequest {
    private String cardNumber;
    private String expMonth;
    private String expYear;
    private String cvc;
    private BigDecimal amount;
}
