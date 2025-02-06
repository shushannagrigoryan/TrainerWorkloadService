package com.example.trainerworkloadservice.services;

import com.example.trainerworkloadservice.entities.Trainer;
import com.example.trainerworkloadservice.entities.TrainerWorkload;
import com.example.trainerworkloadservice.enums.ActionType;
import com.example.trainerworkloadservice.repositories.TrainerWorkloadRepository;
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
     * Updates trainer's workload for the given month.
     *
     * @param username     username
     * @param firstName    firstName
     * @param lastName     lastName
     * @param isActive     trainer active status
     * @param trainingDate trainingDate
     * @param duration     training duration
     * @param actionType   actionType (ADD/DELETE)
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

    /**
     * Updates workload based on actionType.
     *
     * @param trainingDuration   training duration
     * @param additionalDuration duration to ADD/DELETE
     * @param actionType         actionType(ADD/DELETE)
     * @return {@code BigDecimal}
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
     * Returns trainer's current workload or creates a new workload if trainer's workload is not found.
     *
     * @param trainer      {@code Trainer}
     * @param trainingDate trainingDate
     * @return {@code TrainerWorkload}
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
     * Returns trainer's workload for the given month.
     *
     * @param username username
     * @param year     year
     * @param month    month
     * @return {@code BigDecimal}
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
