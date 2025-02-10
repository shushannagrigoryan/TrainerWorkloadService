package com.example.trainerworkloadservice.services;

import com.example.trainerworkloadservice.dto.requestdto.TrainerWorkloadRequestDto;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainerWorkloadReceiverService {
    private static final String TRAINER_WORKLOAD_REQUEST_QUEUE = "trainer-workload-request-queue";
    private final TrainerWorkloadService trainerWorkloadService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = TRAINER_WORKLOAD_REQUEST_QUEUE)
    public void processWorkloadRequest(TrainerWorkloadRequestDto request, Message message) {
        try {
            log.debug("Processing WorkloadRequest");
            // header
            String responseQueue = message.getStringProperty("responseQueue");

            BigDecimal workload = trainerWorkloadService.getTrainerWorkload(request.getUsername(),
                Integer.parseInt(request.getTrainingYear()),
                Integer.parseInt(request.getTrainingMonth()));

            // Send to header response queue
            jmsTemplate.convertAndSend(responseQueue, workload.toString());
        } catch (JMSException e) {
            log.debug(e.getMessage());
        }
    }
}
