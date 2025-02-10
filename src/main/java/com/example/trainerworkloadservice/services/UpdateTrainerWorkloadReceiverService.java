package com.example.trainerworkloadservice.services;

import com.example.trainerworkloadservice.dto.requestdto.UpdateTrainerWorkloadRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateTrainerWorkloadReceiverService {
    private static final String UPDATE_TRAINER_WORKLOAD_QUEUE = "update-trainer-workload-queue";
    private final TrainerWorkloadService trainerWorkloadService;
    private final Validator validator;

    /** receiveMessage for update trainer's workload. */
    @JmsListener(destination = UPDATE_TRAINER_WORKLOAD_QUEUE)
    public void receiveMessage(UpdateTrainerWorkloadRequestDto updateTrainerWorkloadRequestDto) {
        log.debug("Receiving message from ActiveMQ");
        log.debug("message: {} ", updateTrainerWorkloadRequestDto);
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
        log.debug("Successfully received message from ActiveMQ");
    }
}
