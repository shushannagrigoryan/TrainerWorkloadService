package com.example.trainerworkloadservice.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class TrainerWorkloadRequestDto {
    @NotBlank(message = "Username can't be blank")
    private String username;
    @NotBlank(message = "Training year can't be blank")
    @Pattern(regexp = "\\d{4}", message = "Year must be a 4-digit number")
    private String trainingYear;
    @NotBlank(message = "Training month can't be blank")
    @Pattern(regexp = "^(0?[1-9]|1[0-2])$", message = "Month must be between 1 and 12")
    private String trainingMonth;
}
