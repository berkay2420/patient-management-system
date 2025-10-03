package com.pm.patient_service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.event.PatientEvent;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "patient", groupId = "patient-group")
    public void consume(byte [] eventBytes){
        try {
            PatientEvent event = PatientEvent.parseFrom(eventBytes);
            log.info("CONSUMER: Received event:" + event);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
