package com.example.trainerworkloadservice.servicetests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.example.trainerworkloadservice.entities.TrainerWorkload;
import com.example.trainerworkloadservice.entities.TrainingYear;
import com.example.trainerworkloadservice.enums.ActionType;
import com.example.trainerworkloadservice.repositories.TrainerWorkloadRepository;
import com.example.trainerworkloadservice.services.TrainerWorkloadHelper;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TrainerWorkloadHelperTest {
    @Mock
    private TrainerWorkloadRepository trainerWorkloadRepository;

    @InjectMocks
    private TrainerWorkloadHelper trainerWorkloadHelper;

    @Test
    void testUpdateOrCreateTrainingMonthTrainingYearsNull() {
        //given
        BigDecimal updatedDuration = BigDecimal.valueOf(10.5);

        //when
        Map<Integer, TrainingYear> result = trainerWorkloadHelper.updateOrCreateTrainingMonth(
            2025, 5, null, updatedDuration);

        //then
        assertNotNull(result);
        assertTrue(result.containsKey(2025));
        assertTrue(result.get(2025).getMonths().containsKey(5));
        assertEquals(updatedDuration, result.get(2025).getMonths().get(5).getTrainingDuration());
    }

    @Test
    void testUpdateOrCreateTrainingMonth() {
        //given
        Map<Integer, TrainingYear> trainingYears = new HashMap<>();
        trainingYears.put(2025, new TrainingYear(2025, new HashMap<>()));

        BigDecimal updatedDuration = BigDecimal.valueOf(10.5);

        //when
        Map<Integer, TrainingYear> result = trainerWorkloadHelper.updateOrCreateTrainingMonth(
            2025, 5, trainingYears, updatedDuration);

        //then
        assertNotNull(result);
        assertTrue(result.containsKey(2025));
        assertTrue(result.get(2025).getMonths().containsKey(5));
        assertEquals(updatedDuration, result.get(2025).getMonths().get(5).getTrainingDuration());
    }

    @Test
    void testUpdateWorkload_Add() {
        //given
        BigDecimal trainerWorkload = BigDecimal.valueOf(10.0);
        BigDecimal additionalWorkload = BigDecimal.valueOf(5.0);

        //when
        BigDecimal result = trainerWorkloadHelper.updateWorkload(trainerWorkload, additionalWorkload, ActionType.ADD);

        //then
        assertEquals(BigDecimal.valueOf(15.0), result);
    }

    @Test
    void testUpdateWorkload() {
        //given
        BigDecimal trainerWorkload = BigDecimal.valueOf(10.0);
        BigDecimal additionalWorkload = BigDecimal.valueOf(5.0);

        //when
        BigDecimal result =
            trainerWorkloadHelper.updateWorkload(trainerWorkload, additionalWorkload, ActionType.DELETE);

        //then
        assertEquals(BigDecimal.valueOf(5.0), result);
    }

    @Test
    void testGetTrainerWorkloadWorkloadPresent() {
        //given
        String username = "username";
        int year = 2025;
        int month = 5;
        BigDecimal expectedWorkload = BigDecimal.valueOf(25.0);

        when(trainerWorkloadRepository.findTrainingWorkloadByUsernameAndYearAndMonth(username, "2025", "5"))
            .thenReturn(Optional.of(25.0));

        //when
        BigDecimal result = trainerWorkloadHelper.getTrainerWorkload(username, year, month);

        //then
        assertEquals(expectedWorkload, result);
    }

    @Test
    void testGetTrainerWorkloadWorkloadNotPresent() {
        //given
        String username = "username";
        int year = 2025;
        int month = 5;
        BigDecimal expectedWorkload = BigDecimal.ZERO;

        when(trainerWorkloadRepository.findTrainingWorkloadByUsernameAndYearAndMonth(username, "2025", "5"))
            .thenReturn(Optional.empty());

        //when
        BigDecimal result = trainerWorkloadHelper.getTrainerWorkload(username, year, month);

        //then
        assertEquals(expectedWorkload, result);
    }

    @Test
    void testGetOrCreateTrainerWorkloadByUsernameWorkloadExists() {
        //given
        String username = "username";
        String firstName = "firstName";
        String lastName = "lastName";
        boolean isActive = true;

        TrainerWorkload existingWorkload = new TrainerWorkload(username, firstName, lastName, isActive);

        when(trainerWorkloadRepository.findByUsername(username)).thenReturn(Optional.of(existingWorkload));

        //when
        TrainerWorkload result = trainerWorkloadHelper
            .getOrCreateTrainerWorkloadByUsername(username, firstName, lastName, isActive);

        //then
        assertEquals(existingWorkload, result);
    }

    @Test
    void testGetOrCreateTrainerWorkloadByUsername_WhenWorkloadDoesNotExist() {
        //given
        String username = "username";
        String firstName = "firstName";
        String lastName = "lastName";
        boolean isActive = true;

        when(trainerWorkloadRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(trainerWorkloadRepository.save(Mockito.any(TrainerWorkload.class)))
            .thenReturn(new TrainerWorkload(username, firstName, lastName, isActive));

        //when
        TrainerWorkload result = trainerWorkloadHelper
            .getOrCreateTrainerWorkloadByUsername(username, firstName, lastName, isActive);

        //then
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(isActive, result.getStatus());
    }
}
