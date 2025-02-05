package com.example.trainerworkloadservice.controller;

import com.example.trainerworkloadservice.dto.requestdto.UpdateTrainerWorkloadRequestDto;
import com.example.trainerworkloadservice.dto.responsedto.ResponseDto;
import com.example.trainerworkloadservice.exceptionhandlers.ExceptionResponse;
import com.example.trainerworkloadservice.services.TrainerWorkloadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "TrainerWorkloadController")
public class TrainerWorkloadController {
    private final TrainerWorkloadService trainerWorkloadService;

    /**
     * Updates trainer's workload for the given month.
     *
     * @param request {@code UpdateTrainerWorkloadRequestDto}
     * @return {@code ResponseEntity<ResponseDto<String>>}
     */
    @PutMapping("/workload")
    @Operation(description = "Updating trainer's workload")
    @ApiResponses(
        {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully updated trainer's workload.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseEntity.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "The resource you were trying to reach is not found",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "405",
                description = "Method is not allowed.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)
                )
            )
        }
    )
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
     * Returns trainer's workload for a given month.
     *
     * @param username username
     * @param year     year
     * @param month    month
     * @return {@code ResponseEntity<ResponseDto<BigDecimal>>}
     */

    @Operation(description = "Getting trainer's workload for a given month.")
    @ApiResponses(
        {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved trainer's workload for a given month.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseEntity.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "The resource you were trying to reach is not found",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "405",
                description = "Method is not allowed.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)
                )
            )
        }
    )
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
