package com.pm.billing_service.grpc;

import billing.BillingDeleteRequest;
import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//One of the perks of gRPC is it allows us to call the methods while making a call
@GrpcService
public class BillingGrpcService  extends BillingServiceGrpc.BillingServiceImplBase {


    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    //Using the method from billing_service.proto file
    @Override
    public void createBillingAccount(BillingRequest billingRequest,
                                    StreamObserver<BillingResponse> responseObserver) {
        //StreamObserver is for communication between client and server.
        log.info("createBillingAccount request received {}", billingRequest.toString());

        //Main logic, business logic
        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("112123") //dummy data
                .setStatus("ACTIVE")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    @Override
    public void deleteBillingAccount(BillingDeleteRequest request,
                                     StreamObserver<BillingResponse> responseObserver) {
        log.info("deleteBillingAccount request received {}", request.getPatientId());
        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId(request.getPatientId())
                .setStatus("DELETED")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
