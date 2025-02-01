package com.example.trainerworkloadservice;

import com.example.trainerworkloadservice.entities.Trainer;
import com.example.trainerworkloadservice.entities.TrainerWorkload;
import com.example.trainerworkloadservice.repositories.TrainerWorkloadRepository;
import com.example.trainerworkloadservice.services.TrainerService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainerWorkloadService {

    private final TrainerWorkloadRepository trainerWorkloadRepository;
    private final TrainerService trainerService;

    /**
     * update trainer workload.
     */

    @Transactional
    public void updateTrainerWorkload(String username, String firstName, String lastName, boolean isActive,
                                      LocalDateTime trainingDate, BigDecimal duration, ActionType actionType) {

        Trainer trainer = trainerService.getOrCreateTrainer(username, firstName, lastName, isActive);

        TrainerWorkload trainerWorkload = getOrCreateTrainerWorkload(trainer, trainingDate);

        BigDecimal updatedDuration = updateWorkload(trainerWorkload.getDuration(), duration, actionType);

        trainerWorkload.setDuration(updatedDuration);

        trainerWorkloadRepository.save(trainerWorkload);
    }

    //    /** getTrainerWorkload. */
    //    public Map<String, Integer> getTrainerWorkload(String username) {
    //        return repository.findById(username)
    //            .map(TrainerWorkload::getWorkload)
    //            .orElse(Collections.emptyMap());
    //    }

    /**
     * update workload based on actionType.
     */
    private BigDecimal updateWorkload(BigDecimal trainingDuration,
                                      BigDecimal additionalDuration, ActionType actionType) {
        log.debug("Updating workload based on actionType: {} ", actionType.name());
        if (actionType.equals(ActionType.ADD)) {
            return trainingDuration.add(additionalDuration);
        } else if (actionType.equals(ActionType.DELETE)) {
            return trainingDuration.subtract(additionalDuration);
        } else {
            return trainingDuration;
        }
    }


    /**
     * get trainer workload.
     */
    public TrainerWorkload getOrCreateTrainerWorkload(Trainer trainer, LocalDateTime trainingDate) {
        log.debug("Request to retrieve trainerWorkload by username,"
            + " year and month,if present or create a new trainerWorkload if not.");

        return trainerWorkloadRepository.findByMonthAndYearAndTrainer_Username(trainingDate.getMonthValue(),
                trainingDate.getYear(), trainer.getUsername())
            .orElseGet(() ->
                new TrainerWorkload(trainingDate.getYear(), trainingDate.getMonthValue(), BigDecimal.ZERO, trainer));
    }

    /**
     * getTrainerWorkload.
     */
    public BigDecimal getTrainerWorkload(String username, int year, int month) {
        log.debug("Getting trainers: {} workload for the given month: {}.",
            username, year + ":" + month);
        log.debug("year: {}", year);
        log.debug("month: {}", month);
        return trainerWorkloadRepository.findByMonthAndYearAndTrainer_Username(month,
            year, username).map(TrainerWorkload::getDuration).orElse(BigDecimal.ZERO);

    }
}
