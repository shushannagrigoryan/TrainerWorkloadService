package com.example.trainerworkloadservice.servicetests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.trainerworkloadservice.entities.TrainerWorkload;
import com.example.trainerworkloadservice.entities.TrainingYear;
import com.example.trainerworkloadservice.enums.ActionType;
import com.example.trainerworkloadservice.repositories.TrainerWorkloadRepository;
import com.example.trainerworkloadservice.services.TrainerWorkloadHelper;
import com.example.trainerworkloadservice.services.TrainerWorkloadService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TrainerWorkloadServiceTest {

    @Mock
    private TrainerWorkloadRepository trainerWorkloadRepository;

    @Mock
    private TrainerWorkloadHelper trainerWorkloadHelper;

    @InjectMocks
    private TrainerWorkloadService trainerWorkloadService;

    @Test
    void testUpdateTrainerWorkload() {
        //given
        String username = "user";
        String firstName = "fName";
        String lastName = "lName";
        boolean isActive = true;
        BigDecimal duration = BigDecimal.valueOf(10);
        ActionType actionType = ActionType.ADD;

        TrainerWorkload trainerWorkload = mock(TrainerWorkload.class);
        when(trainerWorkloadHelper.getOrCreateTrainerWorkloadByUsername(username, firstName, lastName, isActive))
            .thenReturn(trainerWorkload);
        Map<Integer, TrainingYear> trainingYears = new HashMap<>();
        when(trainerWorkload.getTrainingYears()).thenReturn(trainingYears);
        when(trainerWorkloadHelper.getTrainerWorkload(username, 2025, 2)).thenReturn(null);
        when(trainerWorkloadHelper.updateWorkload(null, duration, actionType)).thenReturn(duration);
        when(trainerWorkloadHelper.updateOrCreateTrainingMonth(2025, 2, trainingYears, duration))
            .thenReturn(trainingYears);

        LocalDateTime trainingDate = LocalDateTime
            .of(2025, 2, 19, 10, 0, 0, 0);

        //when
        trainerWorkloadService.updateTrainerWorkload(
            username, firstName, lastName, isActive, trainingDate, duration, actionType);

        //then
        verify(trainerWorkloadRepository, times(1)).save(trainerWorkload);
        verify(trainerWorkloadHelper, times(1))
            .updateOrCreateTrainingMonth(2025, 2, trainingYears, duration);
        verify(trainerWorkloadHelper, times(1))
            .updateWorkload(null, duration, actionType);
    }
}
