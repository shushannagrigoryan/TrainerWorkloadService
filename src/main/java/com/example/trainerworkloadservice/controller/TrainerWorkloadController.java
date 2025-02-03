package com.example.trainerworkloadservice.controller;

import com.example.trainerworkloadservice.TrainerWorkloadService;
import com.example.trainerworkloadservice.dto.requestdto.UpdateTrainerWorkloadRequestDto;
import com.example.trainerworkloadservice.dto.responsedto.ResponseDto;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TrainerWorkloadController {
    private final TrainerWorkloadService trainerWorkloadService;

    /**
     * update workload.
     */
    @PutMapping("/workload")
    public ResponseEntity<ResponseDto<String>> updateTrainerWorkload(
        @RequestBody UpdateTrainerWorkloadRequestDto request) {
        trainerWorkloadService.updateTrainerWorkload(
            request.getUsername(),
            request.getFirstName(),
            request.getLastName(),
            request.getIsActive(),
            request.getTrainingDate(),
            request.getTrainingDuration(),
            request.getActionType()
        );
        ResponseDto<String> responseDto =
            new ResponseDto<>(null, "Successfully updated trainer's workload.");

        return ResponseEntity.ok(responseDto);
    }

    /**
     * get workload.
     */
    @GetMapping("/workload")
    public ResponseEntity<ResponseDto<BigDecimal>> getTrainerWorkloadByMonth(
        @RequestParam("username") String username,
        @RequestParam("year") String year,
        @RequestParam("month") String month) {
        log.debug("Getting trainers: {} workload for the given month: {}.", username, year + ":" + month);

        BigDecimal workload = trainerWorkloadService.getTrainerWorkload(username, Integer.parseInt(year),
            Integer.parseInt(month));
        return ResponseEntity.ok(new ResponseDto<>(workload,
            String.format("Successfully retrieved the workload of trainer: %s for the month: %s",
                username, year + ":" + month)));
    }

}
