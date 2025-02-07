package com.example.trainerworkloadservice.services;

import com.example.trainerworkloadservice.entities.Trainer;
import com.example.trainerworkloadservice.repositories.TrainerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainerService {
    private final TrainerRepository trainerRepository;

    /**
     * Returns trainer by username if present, or creates and returns a new trainer if not present.
     *
     * @param username  username
     * @param firstName firstName
     * @param lastName  lastName
     * @param isActive  isActive
     * @return {@code Trainer}
     */
    public Trainer getOrCreateTrainer(String username, String firstName, String lastName, Boolean isActive) {
        log.debug("Request to retrieve trainer by username if present or create a new trainer if not.");
        return trainerRepository.findByUsername(username)
            .orElseGet(() -> trainerRepository.save(new Trainer(username, firstName, lastName, isActive)));
    }
}
