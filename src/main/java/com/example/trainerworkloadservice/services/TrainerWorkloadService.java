package com.example.trainerworkloadservice.services;

import com.example.trainerworkloadservice.entities.TrainerWorkload;
import com.example.trainerworkloadservice.entities.TrainingYear;
import com.example.trainerworkloadservice.enums.ActionType;
import com.example.trainerworkloadservice.repositories.TrainerWorkloadRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainerWorkloadService {

    private final TrainerWorkloadRepository trainerWorkloadRepository;
    private final TrainerWorkloadHelper trainerWorkloadHelper;

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
            trainerWorkloadHelper.getOrCreateTrainerWorkloadByUsername(username, firstName, lastName, isActive);

        BigDecimal updatedDuration = trainerWorkloadHelper.updateWorkload(
            trainerWorkloadHelper.getTrainerWorkload(username, year, month), duration, actionType);

        Map<Integer, TrainingYear> trainingYears = trainerWorkload.getTrainingYears();
        trainerWorkload.setTrainingYears(trainerWorkloadHelper
            .updateOrCreateTrainingMonth(year, month, trainingYears, updatedDuration));

        trainerWorkloadRepository.save(trainerWorkload);

        log.debug("Successfully updated trainer's workload for the given month.");
    }
}
