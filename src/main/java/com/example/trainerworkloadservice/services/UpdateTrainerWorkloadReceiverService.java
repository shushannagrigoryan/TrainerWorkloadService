package com.example.trainerworkloadservice.services;

import com.example.trainerworkloadservice.dto.requestdto.UpdateTrainerWorkloadRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateTrainerWorkloadReceiverService {
    private static final String UPDATE_TRAINER_WORKLOAD_QUEUE = "update-trainer-workload-queue";
    private static final String UPDATE_TRAINER_WORKLOAD_RESPONSE_QUEUE = "update-trainer-workload-response-queue";
    private static final long TIMEOUT_THRESHOLD = 5000;
    private final TrainerWorkloadService trainerWorkloadService;
    private final Validator validator;
    private final JmsTemplate jmsTemplate;

    /**
     * receiveMessage for update trainer's workload.
     */
    @JmsListener(destination = UPDATE_TRAINER_WORKLOAD_QUEUE)
    public void receiveMessage(UpdateTrainerWorkloadRequestDto updateTrainerWorkloadRequestDto) {
        log.debug("Receiving message from ActiveMQ");
        log.debug("message: {} ", updateTrainerWorkloadRequestDto);
        long startTime = System.currentTimeMillis();
        Set<ConstraintViolation<UpdateTrainerWorkloadRequestDto>> violations =
            validator.validate(updateTrainerWorkloadRequestDto);

        if (!violations.isEmpty()) {
            log.debug("Validation error");
            violations.forEach(violation ->
                log.error("Validation failed: {}", violation.getMessage())
            );
            log.error("Error processing message: ");
            throw new IllegalArgumentException("Invalid message");
        }
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
    }
}
