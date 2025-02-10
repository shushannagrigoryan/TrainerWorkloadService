//package com.example.trainerworkloadservice.services;
//
//import java.math.BigDecimal;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class TrainerWorkloadSenderService {
//    private static final String TRAINER_WORKLOAD_RESPONSE_QUEUE = "trainer-workload-response-queue";
//
//    private final JmsTemplate jmsTemplate;
//
//    /** send method for sending trainer's workload. */
//    public void send(BigDecimal trainerWorkload) {
//        log.debug("Sending BigDecimal to ActiveMQ");
//        System.out.println(trainerWorkload);
//        jmsTemplate.convertAndSend(TRAINER_WORKLOAD_RESPONSE_QUEUE, trainerWorkload);
//        log.debug("Successfully sent BigDecimal to ActiveMQ.");
//    }
//}
