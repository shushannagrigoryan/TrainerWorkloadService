package com.example.trainerworkloadservice.dto.requestdto;

import com.example.trainerworkloadservice.enums.ActionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateTrainerWorkloadRequestDto {
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    private Boolean isActive;
    @NotNull(message = "Training date is required.")
    private LocalDateTime trainingDate;
    @NotNull(message = "Training duration is required")
    private BigDecimal trainingDuration;
    @NotNull(message = "Action type is required")
    private ActionType actionType;

}
