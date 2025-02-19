package com.example.trainerworkloadservice.services;

import com.example.trainerworkloadservice.entities.TrainerWorkload;
import com.example.trainerworkloadservice.entities.TrainingMonth;
import com.example.trainerworkloadservice.entities.TrainingYear;
import com.example.trainerworkloadservice.enums.ActionType;
import com.example.trainerworkloadservice.repositories.TrainerWorkloadRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainerWorkloadService {

    private final TrainerWorkloadRepository trainerWorkloadRepository;

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
        log.debug("Request to update trainer's workload for the given month.");

        int year = trainingDate.getYear();
        int month = trainingDate.getMonthValue();

        TrainerWorkload trainerWorkload =
            getOrCreateTrainerWorkloadByUsername(username, firstName, lastName, isActive);

        BigDecimal updatedDuration = updateWorkload(getTrainerWorkload(username, year, month),
            duration, actionType);

        Map<Integer, TrainingYear> trainingYears = trainerWorkload.getTrainingYears();
        trainerWorkload.setTrainingYears(updateOrCreateTrainingMonth(year, month, trainingYears, updatedDuration));

        trainerWorkloadRepository.save(trainerWorkload);

        log.debug("Successfully updated trainer's workload for the given month.");
    }


    /**
     * Updates or creates the training year and training month in the
     * trainer's trainingYears map for the given month. Sets the new updated duration value.
     *
     * @param year            year of the training
     * @param month           month of the training
     * @param trainingYears   trainer's trainingYears map
     * @param updatedDuration updated duration for the given month
     * @return {@code Map<Integer, TrainingYear>}
     */
    public Map<Integer, TrainingYear> updateOrCreateTrainingMonth(
        int year, int month, Map<Integer, TrainingYear> trainingYears, BigDecimal updatedDuration) {
        if (trainingYears == null) {
            trainingYears = new HashMap<>();
        }

        TrainingYear trainingYear = trainingYears.computeIfAbsent(year, y -> new TrainingYear(y, new HashMap<>()));

        trainingYear.getMonths().computeIfAbsent(month, m -> new TrainingMonth(m, BigDecimal.ZERO))
            .setTrainingDuration(updatedDuration);

        return trainingYears;
    }

    /**
     * Updates workload based on actionType.
     *
     * @param trainerWorkload    trainer workload
     * @param additionalWorkload additional workload to update(ADD/DELETE) trainer's workload
     * @param actionType         actionType(ADD/DELETE)
     * @return {@code BigDecimal}
     */
    private BigDecimal updateWorkload(BigDecimal trainerWorkload,
                                      BigDecimal additionalWorkload, ActionType actionType) {
        log.debug("Updating workload based on actionType: {} ", actionType.name());
        if (actionType.equals(ActionType.ADD)) {
            return trainerWorkload.add(additionalWorkload);
        } else if (actionType.equals(ActionType.DELETE)) {
            return trainerWorkload.subtract(additionalWorkload);
        } else {
            return trainerWorkload;
        }
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
        log.debug("Getting trainers workload for the given month: {}.", year + ":" + month);
        log.debug("year: {}", year);
        log.debug("month: {}", month);
        Optional<Double> trainerWorkload = trainerWorkloadRepository
            .findTrainingWorkloadByUsernameAndYearAndMonth(username, String.valueOf(year), String.valueOf(month));

        return trainerWorkload.map(BigDecimal::valueOf).orElse(BigDecimal.ZERO);
    }

    /**
     * Returns trainer's workload if present, or creates a new one for the given username.
     *
     * @param username  username
     * @param firstName firstName
     * @param lastName  lastName
     * @param isActive  isActive
     * @return {@code TrainerWorkload}
     */
    public TrainerWorkload getOrCreateTrainerWorkloadByUsername(String username, String firstName, String lastName,
                                                                boolean isActive) {
        log.debug("Request to return trainer's workload by username or create new workload if it is not present.");
        Optional<TrainerWorkload> workload = trainerWorkloadRepository.findByUsername(username);
        return workload.orElseGet(() -> trainerWorkloadRepository.save(new TrainerWorkload(
            username, firstName, lastName, isActive)));
    }
}
