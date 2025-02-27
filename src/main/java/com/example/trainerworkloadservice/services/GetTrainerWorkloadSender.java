package com.example.trainerworkloadservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetTrainerWorkloadSender {
    private static final String TRAINER_WORKLOAD_RESPONSE_QUEUE = "trainer-workload-response-queue";

    private final JmsTemplate jmsTemplate;

    /**
     * Sends message to ActiveMQ TRAINER_WORKLOAD_RESPONSE_QUEUE queue.
     */
    public void send(String message) {
        log.debug("Sending message to ActiveMQ TRAINER_WORKLOAD_RESPONSE_QUEUE queue.");
        jmsTemplate.convertAndSend(TRAINER_WORKLOAD_RESPONSE_QUEUE, message);
        log.debug("Successfully sent GetTrainerWorkloadResponse message to ActiveMQ.");
    }
}
