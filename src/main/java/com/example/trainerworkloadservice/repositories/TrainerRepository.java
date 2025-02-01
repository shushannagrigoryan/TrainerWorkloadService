package com.example.trainerworkloadservice.repositories;

import com.example.trainerworkloadservice.entities.Trainer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, String> {
    Optional<Trainer> findByUsername(String username);
}