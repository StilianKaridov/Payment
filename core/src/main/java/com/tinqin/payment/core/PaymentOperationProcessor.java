package com.tinqin.payment.core;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import com.stripe.net.RequestOptions;
import com.tinqin.payment.api.PaymentOperation;
import com.tinqin.payment.api.PaymentRequest;
import com.tinqin.payment.api.PaymentResponse;
import com.tinqin.payment.api.StripeTokenResponse;
import com.tinqin.payment.core.exception.PaymentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentOperationProcessor implements PaymentOperation {

    @Value("${stripe.secret.key}")
    private String stripeSecretApiKey;

    @Value("${stripe.public.key}")
    private String stripePublicApiKey;

    @Override
    public PaymentResponse process(PaymentRequest input) {
        StripeTokenResponse cardToken = createCardToken(input);

        return charge(input, cardToken.getToken());
    }

    private StripeTokenResponse createCardToken(PaymentRequest input) {
        try {
            Map<String, Object> card = new HashMap<>();
            card.put("number", input.getCardNumber());
            card.put("exp_month", Integer.parseInt(input.getExpMonth()));
            card.put("exp_year", Integer.parseInt(input.getExpYear()));
            card.put("cvc", input.getCvc());

            Map<String, Object> params = new HashMap<>();
            params.put("card", card);

            RequestOptions requestOptions = RequestOptions
                    .builder()
                    .setApiKey(stripePublicApiKey)
                    .build();
            Token token = Token.create(params, requestOptions);
            return StripeTokenResponse
                    .builder()
                    .token(token.getId())
                    .build();
        } catch (StripeException e) {
            throw new PaymentException(e.getMessage());
        }
    }

    private PaymentResponse charge(PaymentRequest paymentRequest, String cardToken) {
        try {
            int amount = Double
                    .valueOf(paymentRequest.getAmount().doubleValue() * 100)
                    .intValue();

            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", amount);
            chargeParams.put("currency", "BGN");
            chargeParams.put("source", cardToken);

            RequestOptions requestOptions = RequestOptions
                    .builder()
                    .setApiKey(stripeSecretApiKey)
                    .build();
            Charge charge = Charge.create(chargeParams, requestOptions);
            if (charge.getPaid()) {
                return PaymentResponse
                        .builder()
                        .isSuccessful(true)
                        .build();
            }

            return PaymentResponse
                    .builder()
                    .isSuccessful(false)
                    .build();
        } catch (StripeException e) {
            throw new PaymentException(e.getMessage());
        }
    }
}
