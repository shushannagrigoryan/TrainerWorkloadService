package com.example.trainerworkloadservice.servicetests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.trainerworkloadservice.entities.Trainer;
import com.example.trainerworkloadservice.entities.TrainerWorkload;
import com.example.trainerworkloadservice.enums.ActionType;
import com.example.trainerworkloadservice.repositories.TrainerWorkloadRepository;
import com.example.trainerworkloadservice.services.TrainerService;
import com.example.trainerworkloadservice.services.TrainerWorkloadService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
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
    private TrainerService trainerService;
    @InjectMocks
    private TrainerWorkloadService trainerWorkloadService;

    @Test
    void testUpdateTrainerWorkloadAdd() {
        String username = "user";
        String firstName = "fName";
        String lastName = "lName";
        boolean isActive = true;
        LocalDateTime trainingDate = LocalDateTime.now();
        BigDecimal duration = new BigDecimal("60");
        ActionType actionType = ActionType.ADD;

        Trainer trainer = new Trainer(username, firstName, lastName, isActive);
        TrainerWorkload trainerWorkload = new TrainerWorkload(
            trainingDate.getYear(), trainingDate.getMonthValue(), new BigDecimal("30"), trainer);

        when(trainerService.getOrCreateTrainer(username, firstName, lastName, isActive)).thenReturn(trainer);
        when(trainerWorkloadRepository.findByMonthAndYearAndTrainer_Username(
            trainingDate.getMonthValue(), trainingDate.getYear(), username))
            .thenReturn(Optional.of(trainerWorkload));

        trainerWorkloadService
            .updateTrainerWorkload(username, firstName, lastName, isActive, trainingDate, duration, actionType);

        assertEquals(new BigDecimal("90"), trainerWorkload.getDuration());
        verify(trainerWorkloadRepository, times(1)).save(trainerWorkload);
    }

    @Test
    void testUpdateTrainerWorkloadDelete() {
        String username = "user";
        String firstName = "fName";
        String lastName = "lName";
        boolean isActive = true;
        LocalDateTime trainingDate = LocalDateTime.now();
        BigDecimal duration = new BigDecimal("20");
        ActionType actionType = ActionType.DELETE;

        Trainer trainer = new Trainer(username, firstName, lastName, isActive);
        TrainerWorkload trainerWorkload = new TrainerWorkload(trainingDate.getYear(),
            trainingDate.getMonthValue(), new BigDecimal("90"), trainer);

        when(trainerService.getOrCreateTrainer(username, firstName, lastName, isActive)).thenReturn(trainer);
        when(trainerWorkloadRepository.findByMonthAndYearAndTrainer_Username(
            trainingDate.getMonthValue(), trainingDate.getYear(), username))
            .thenReturn(Optional.of(trainerWorkload));

        trainerWorkloadService
            .updateTrainerWorkload(username, firstName, lastName, isActive, trainingDate, duration, actionType);

        assertEquals(new BigDecimal("70"), trainerWorkload.getDuration());
        verify(trainerWorkloadRepository, times(1)).save(trainerWorkload);
    }

    @Test
    void testGetTrainerWorkloadWorkloadExists() {
        String username = "user";
        int year = 2024;
        int month = 9;
        BigDecimal expectedWorkload = new BigDecimal("40");

        when(trainerWorkloadRepository.findByMonthAndYearAndTrainer_Username(month, year, username))
            .thenReturn(Optional.of(new TrainerWorkload(
                year, month, expectedWorkload, new Trainer(username, "fName", "lName", true))));

        BigDecimal actualWorkload = trainerWorkloadService.getTrainerWorkload(username, year, month);

        assertEquals(expectedWorkload, actualWorkload);
    }

    @Test
    void testGetTrainerWorkloadWorkloadDoesNotExist() {
        String username = "user";
        int year = 2024;
        int month = 9;

        when(trainerWorkloadRepository.findByMonthAndYearAndTrainer_Username(month, year, username))
            .thenReturn(Optional.empty());

        BigDecimal actualWorkload = trainerWorkloadService.getTrainerWorkload(username, year, month);

        assertEquals(BigDecimal.ZERO, actualWorkload);
    }
}
