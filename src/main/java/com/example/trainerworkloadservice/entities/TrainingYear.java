package com.example.trainerworkloadservice.entities;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingYear {
    private int year;
    private Map<Integer, TrainingMonth> months;

}
