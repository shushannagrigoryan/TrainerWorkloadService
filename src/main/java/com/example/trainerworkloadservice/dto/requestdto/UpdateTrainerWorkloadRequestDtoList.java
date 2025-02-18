package com.example.trainerworkloadservice.dto.requestdto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
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
public class UpdateTrainerWorkloadRequestDtoList {
    @NotEmpty(message = "Request list cannot be empty.")
    @Valid
    private List<UpdateTrainerWorkloadRequestDto> updateTrainerWorkloadRequestDtoList;
}