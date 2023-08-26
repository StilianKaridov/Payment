package com.tinqin.payment.api.base;

public interface OperationProcessor<Response extends OperationResponse, Request extends OperationRequest> {

    Response process(Request input);
}
