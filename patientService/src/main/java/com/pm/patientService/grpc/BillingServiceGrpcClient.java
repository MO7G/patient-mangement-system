package com.pm.patientService.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import billing.*;

@Service
@Slf4j
public class BillingServiceGrpcClient {

  private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

  public BillingServiceGrpcClient(
          @Value("${billing.service.address.localhost}") String serverAddress,
          @Value("${billing.service.grpc.port:9001}") int serverPort
  ) {
    log.info("Connecting to Billing gRPC service at {}:{}", serverAddress, serverPort);

    ManagedChannel channel = ManagedChannelBuilder
            .forAddress(serverAddress, serverPort)
            .usePlaintext() // disable TLS for local dev
            .build();

    this.blockingStub = BillingServiceGrpc.newBlockingStub(channel);
  }

  public BillingResponse createBillingAccount(String patientId, String name, String email) {
    BillingRequest request = BillingRequest.newBuilder()
            .setPatientId(patientId)
            .setName(name)
            .setEmail(email)
            .build();

    BillingResponse response = blockingStub.createBillingAccount(request);
    log.info("Received response from Billing gRPC service: {}", response);

    return response;
  }
}
