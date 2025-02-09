package com.example.trainerworkloadservice.services;

import com.example.trainerworkloadservice.dto.requestdto.TrainerWorkloadRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainerWorkloadSenderService {
    private static final String TRAINER_WORKLOAD_QUEUE = "trainer-workload-queue";

    private final JmsTemplate jmsTemplate;

    /** send method for sending trainer's workload. */
    public void send(TrainerWorkloadRequestDto trainerWorkloadRequestDto) {
        log.debug("Sending TrainerWorkloadRequestDto to ActiveMQ");
        System.out.println(trainerWorkloadRequestDto);
        jmsTemplate.convertAndSend(TRAINER_WORKLOAD_QUEUE, trainerWorkloadRequestDto);
        log.debug("Successfully sent TrainerWorkloadRequestDto to ActiveMQ.");
    }
}
