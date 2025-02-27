package com.example.trainerworkloadservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateTrainerWorkloadSender {
    private static final String UPDATE_TRAINER_WORKLOAD_RESPONSE_QUEUE = "update-trainer-workload-response-queue";
    private final JmsTemplate jmsTemplate;

    /**
     * Sends message to ActiveMQ UPDATE_TRAINER_WORKLOAD_QUEUE_TRAININGS_LIST queue.
     */
    public void send(String message) {
        log.debug("Sending message to ActiveMQ UPDATE_TRAINER_WORKLOAD_RESPONSE_QUEUE queue.");
        jmsTemplate.convertAndSend(UPDATE_TRAINER_WORKLOAD_RESPONSE_QUEUE, message);
        log.debug("Successfully sent UpdateTrainerWorkloadResponse message to ActiveMQ.");
    }
}
