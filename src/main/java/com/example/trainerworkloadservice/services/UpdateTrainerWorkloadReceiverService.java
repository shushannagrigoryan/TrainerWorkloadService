package com.example.trainerworkloadservice.services;

import com.example.trainerworkloadservice.dto.requestdto.UpdateTrainerWorkloadRequestDto;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class UpdateTrainerWorkloadReceiverService {
    private static final String UPDATE_TRAINER_WORKLOAD_QUEUE = "update-trainer-workload-queue";
    private static final String UPDATE_TRAINER_WORKLOAD_RESPONSE_QUEUE = "update-trainer-workload-response-queue";
    private static final long TIMEOUT_THRESHOLD = 5000;
    private final TrainerWorkloadService trainerWorkloadService;
    private final JmsTemplate jmsTemplate;

    /**
     * receiveMessage for update trainer's workload.
     */
    @JmsListener(destination = UPDATE_TRAINER_WORKLOAD_QUEUE)
    public void receiveMessage(@Valid UpdateTrainerWorkloadRequestDto updateTrainerWorkloadRequestDto,
                               @Headers Map<String, Object> headers) {
        String trace = (String) headers.get("traceId");
        MDC.put("traceId", trace);
        log.debug("Receiving message from ActiveMQ");
        log.debug("message: {} ", updateTrainerWorkloadRequestDto);
        long startTime = System.currentTimeMillis();

        trainerWorkloadService.updateTrainerWorkload(
            updateTrainerWorkloadRequestDto.getUsername(),
            updateTrainerWorkloadRequestDto.getFirstName(),
            updateTrainerWorkloadRequestDto.getLastName(),
            updateTrainerWorkloadRequestDto.getIsActive(),
            updateTrainerWorkloadRequestDto.getTrainingDate(),
            updateTrainerWorkloadRequestDto.getTrainingDuration(),
            updateTrainerWorkloadRequestDto.getActionType()
        );

        long time = System.currentTimeMillis() - startTime;
        if (time > TIMEOUT_THRESHOLD) {
            log.warn("Processing took too long: {}", time);
            throw new RuntimeException("Processing took too long.");
        }
        jmsTemplate.convertAndSend(UPDATE_TRAINER_WORKLOAD_RESPONSE_QUEUE, "Success.");

        log.debug("Successfully received message from ActiveMQ");
        MDC.clear();
    }
}
