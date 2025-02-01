package com.example.trainerworkloadservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TrainerWorkload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "training_year")
    private int year;
    @Column(name = "training_month")
    private int month;
    private BigDecimal duration;

    @ManyToOne
    @JoinColumn(name = "trainer_username")
    private Trainer trainer;

    /**
     * constructor.
     */
    public TrainerWorkload(int year, int month, BigDecimal duration, Trainer trainer) {
        this.year = year;
        this.month = month;
        this.duration = duration;
        this.trainer = trainer;
    }
}
