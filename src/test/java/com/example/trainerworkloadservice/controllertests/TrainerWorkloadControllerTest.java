package com.example.trainerworkloadservice.controllertests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.example.trainerworkloadservice.controller.TrainerWorkloadController;
import com.example.trainerworkloadservice.dto.requestdto.UpdateTrainerWorkloadRequestDto;
import com.example.trainerworkloadservice.dto.responsedto.ResponseDto;
import com.example.trainerworkloadservice.enums.ActionType;
import com.example.trainerworkloadservice.services.TrainerWorkloadService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class TrainerWorkloadControllerTest {
    @Mock
    private TrainerWorkloadService trainerWorkloadService;
    @InjectMocks
    private TrainerWorkloadController trainerWorkloadController;

    @Test
    public void testUpdateTrainerWorkload() {
        //given
        String username = "user";
        String firstName = "fName";
        String lastName = "lName";
        boolean isActive = true;
        BigDecimal trainingDuration = BigDecimal.valueOf(60);
        LocalDateTime date = LocalDateTime.now();
        ActionType actionType = ActionType.ADD;
        UpdateTrainerWorkloadRequestDto requestDto = new UpdateTrainerWorkloadRequestDto(username, firstName, lastName,
            isActive, date, trainingDuration, actionType);
        doNothing().when(trainerWorkloadService)
            .updateTrainerWorkload(username, firstName, lastName, isActive, date, trainingDuration, actionType);

        //when
        ResponseEntity<ResponseDto<String>> result = trainerWorkloadController.updateTrainerWorkload(requestDto);

        //then
        verify(trainerWorkloadService)
            .updateTrainerWorkload(username, firstName, lastName, isActive, date, trainingDuration, actionType);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(Objects.requireNonNull(result.getBody()).getMessage(), "Successfully updated trainer's workload.");
    }

    @Test
    public void testGetTrainerWorkloadByMonth() {
        //given
        String username = "user";
        String year = "2024";
        String month = "7";
        BigDecimal trainingDuration = BigDecimal.valueOf(60);
        doReturn(trainingDuration).when(trainerWorkloadService)
            .getTrainerWorkload(username, Integer.parseInt(year), Integer.parseInt(month));

        //when
        ResponseEntity<ResponseDto<BigDecimal>> result =
            trainerWorkloadController.getTrainerWorkloadByMonth(username, year, month);

        //then
        verify(trainerWorkloadService).getTrainerWorkload(username, Integer.parseInt(year), Integer.parseInt(month));
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(Objects.requireNonNull(result.getBody()).getMessage(),
            String.format("Successfully retrieved the workload of trainer: %s for the month: %s",
                username, year + ":" + month));
    }
}
