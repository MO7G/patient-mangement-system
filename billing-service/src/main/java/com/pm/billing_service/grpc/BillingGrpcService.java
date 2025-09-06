package com.pm.billing_service.grpc;


import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;


@GrpcService
@Slf4j
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

    @Override
    public void createBillingAccount(billing.BillingRequest billingRequest , StreamObserver<billing.BillingResponse> responseObserver){

        log.info("createBillingAccount request received {}" , billingRequest.toString());


        // implement the business logic here save to the database

        BillingResponse response = BillingResponse.newBuilder().setAccountId("1234").setStatus("ACTIVE").build();


        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
