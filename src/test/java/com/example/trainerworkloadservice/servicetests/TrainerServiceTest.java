package com.example.trainerworkloadservice.servicetests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.trainerworkloadservice.entities.Trainer;
import com.example.trainerworkloadservice.repositories.TrainerRepository;
import com.example.trainerworkloadservice.services.TrainerService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceTest {
    @Mock
    private TrainerRepository trainerRepository;
    @InjectMocks
    private TrainerService trainerService;

    @Test
    public void testGetOrCreateTrainerFound() {
        //given
        String username = "user";
        String firstName = "fName";
        String lastName = "lName";
        boolean isActive = true;
        Trainer trainer = new Trainer();
        trainer.setUsername(username);
        trainer.setFirstName(firstName);
        trainer.setLastName(lastName);
        trainer.setStatus(isActive);
        when(trainerRepository.findByUsername(username)).thenReturn(Optional.of(trainer));

        //when
        Trainer result = trainerService.getOrCreateTrainer(username, firstName, lastName, isActive);

        //then
        verify(trainerRepository).findByUsername(username);
        assertEquals(trainer, result);
    }

    @Test
    public void testGetOrCreateTrainerNotFound() {
        //given
        String username = "user";
        String firstName = "fName";
        String lastName = "lName";
        boolean isActive = true;
        Trainer trainer = new Trainer();
        trainer.setUsername(username);
        trainer.setFirstName(firstName);
        trainer.setLastName(lastName);
        trainer.setStatus(isActive);
        when(trainerRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);

        //when
        Trainer result = trainerService.getOrCreateTrainer(username, firstName, lastName, isActive);

        //then
        verify(trainerRepository).findByUsername(username);
        verify(trainerRepository).save(any(Trainer.class));
        assertEquals(username, result.getUsername());
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(isActive, result.getStatus());
    }
}
