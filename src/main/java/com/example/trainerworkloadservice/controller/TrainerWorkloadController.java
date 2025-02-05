package com.example.trainerworkloadservice.controller;

import com.example.trainerworkloadservice.TrainerWorkloadService;
import com.example.trainerworkloadservice.dto.requestdto.UpdateTrainerWorkloadRequestDto;
import com.example.trainerworkloadservice.dto.responsedto.ResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
public class TrainerWorkloadController {
    private final TrainerWorkloadService trainerWorkloadService;

    /**
     * update workload.
     */
    @PutMapping("/workload")
    public ResponseEntity<ResponseDto<String>> updateTrainerWorkload(
        @Valid @RequestBody UpdateTrainerWorkloadRequestDto request) {
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
        @RequestParam("username") @NotBlank(message = "Username can't be blank") String username,
        @RequestParam("year")
        @NotBlank(message = "Training year can't be blank")
        @Pattern(regexp = "\\d{4}", message = "Year must be a 4-digit number") String year,
        @RequestParam("month")
        @NotBlank(message = "Training month can't be blank")
        @Pattern(regexp = "^(0?[1-9]|1[0-2])$", message = "Month must be between 1 and 12")
        String month
    ) {
        log.debug("Getting trainers: {} workload for the given month: {}.", username, year + ":" + month);

        BigDecimal workload = trainerWorkloadService.getTrainerWorkload(username, Integer.parseInt(year),
            Integer.parseInt(month));
        return ResponseEntity.ok(new ResponseDto<>(workload,
            String.format("Successfully retrieved the workload of trainer: %s for the month: %s",
                username, year + ":" + month)));
    }

}
