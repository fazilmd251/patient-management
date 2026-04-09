package com.pm.billingservice.grpc;

import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(billing.BillingRequest billingRequest,
                                     StreamObserver<BillingResponse> responseStreamObserver){
        log.info("creating billing account request received {}",billingRequest.toString());

        //business logic -yet to implement

        BillingResponse response=BillingResponse.newBuilder()
                .setAccountId("12345").setStatus("Active").build();

        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }
}
