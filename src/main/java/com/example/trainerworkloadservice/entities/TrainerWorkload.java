package com.example.trainerworkloadservice.entities;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "trainerWorkloadSummary")
@CompoundIndexes({
    @CompoundIndex(name = "idx_trainer_name", def = "{'firstName': 1, 'lastName': 1}")
})
public class TrainerWorkload {
    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    private String firstName;
    private String lastName;
    private Boolean status;
    private Map<Integer, TrainingYear> trainingYears;

    /**
     * constructor.
     */
    public TrainerWorkload(String username, String firstName, String lastName, Boolean status) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }
}
