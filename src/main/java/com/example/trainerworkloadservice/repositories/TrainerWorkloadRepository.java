package com.example.trainerworkloadservice.repositories;

import com.example.trainerworkloadservice.entities.TrainerWorkload;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerWorkloadRepository extends JpaRepository<TrainerWorkload, Long> {
    Optional<TrainerWorkload> findByMonthAndYearAndTrainer_Username(int month, int year, String username);
}