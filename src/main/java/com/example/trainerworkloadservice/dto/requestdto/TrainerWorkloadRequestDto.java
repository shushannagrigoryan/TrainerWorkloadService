package com.example.trainerworkloadservice.dto.requestdto;

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
public class TrainerWorkloadRequestDto {
    private String username;
    private LocalDateTime trainingDate;

}
