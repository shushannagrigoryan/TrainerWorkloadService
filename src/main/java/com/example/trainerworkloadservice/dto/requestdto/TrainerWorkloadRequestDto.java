package com.example.trainerworkloadservice.dto.requestdto;

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
    private String username;
    private String trainingYear;
    private String trainingMonth;
}
