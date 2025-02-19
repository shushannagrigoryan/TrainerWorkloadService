package com.example.trainerworkloadservice.entities;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingMonth {
    private int month;
    private BigDecimal trainingDuration;
}