package com.pm.patient_service.grpc;

import billing.BillingDeleteRequest;
import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BillingServiceGrpcClient {
    private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    //localhost:9001/BillingService/CreatePatientAccount
    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.service.grpc.port:9001}") int serverPort
    ){
        log.info("Connecting to Billing Service GRPC service at {}:{}", serverAddress, serverPort);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort)
                .usePlaintext().build();

        blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }
    public BillingResponse createBillingAccount(String patientId, String name, String email){
        BillingRequest billingRequest = BillingRequest.newBuilder().
                setPatientId(patientId).setName(name).setEmail(email)
                .build();

        //Automatically creates and returns a billing account
        BillingResponse response = blockingStub.createBillingAccount(billingRequest);
        log.info("Received response from billing service via GRPC: {}", response);

        return response;
    }

    public void deleteBillingAccount(UUID patientId){
        BillingDeleteRequest request = BillingDeleteRequest.newBuilder().setPatientId(patientId.toString()).build();
        blockingStub.deleteBillingAccount(request);
    }
}
