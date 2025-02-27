package com.example.trainerworkloadservice.steps;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import com.example.trainerworkloadservice.dto.requestdto.UpdateTrainerWorkloadRequestDto;
import com.example.trainerworkloadservice.enums.ActionType;
import com.example.trainerworkloadservice.services.UpdateTrainerWorkloadReceiverService;
import com.example.trainerworkloadservice.services.UpdateTrainerWorkloadSender;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.validation.ValidationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
@RequiredArgsConstructor
public class UpdateTrainerWorkloadSteps {

    private final UpdateTrainerWorkloadRequestDto updateTrainerWorkloadRequestDto =
        new UpdateTrainerWorkloadRequestDto();
    private final UpdateTrainerWorkloadReceiverService updateTrainerWorkloadReceiverService;

    private final Map<String, Object> headers = Map.of("traceId", "12345");


    private final UpdateTrainerWorkloadSender updateTrainerWorkloadSender;
    private Exception exception;

    /**
     * Constructs a UpdateTrainerWorkloadRequestDto object.
     *
     * @param trainerUsername  trainerUsername
     * @param firstName        firstName
     * @param lastName         lastName
     * @param status           trainer isActive status
     * @param trainingDate     trainingDate
     * @param trainingDuration trainingDuration
     * @param actionType       actionType(ADD/DELETE)
     */
    @Given("a trainer with username {string} firstName {string}, lastName {string}, isActiveStatus {string} "
        + "and trainingData including trainingDate {string} trainingDuration: {string} and actionType {string}")
    public void givenInputDataToUpdateTrainerWorkload(String trainerUsername, String firstName, String lastName,
                                                      String status, String trainingDate, String trainingDuration,
                                                      String actionType) {
        updateTrainerWorkloadRequestDto.setUsername(trainerUsername);
        updateTrainerWorkloadRequestDto.setFirstName(firstName);
        updateTrainerWorkloadRequestDto.setLastName(lastName);
        updateTrainerWorkloadRequestDto.setIsActive(Boolean.valueOf(status));
        updateTrainerWorkloadRequestDto.setTrainingDate(LocalDateTime.parse(trainingDate));
        updateTrainerWorkloadRequestDto.setTrainingDuration(new BigDecimal(trainingDuration));
        updateTrainerWorkloadRequestDto.setActionType(ActionType.valueOf(actionType));
    }

    /**
     * Constructs a UpdateTrainerWorkloadRequestDto object. Username is null.
     *
     * @param firstName        firstName
     * @param lastName         lastName
     * @param status           trainer isActive status
     * @param trainingDate     trainingDate
     * @param trainingDuration trainingDuration
     * @param actionType       actionType(ADD/DELETE)
     */
    @Given("a trainer with firstName {string}, lastName {string}, isActiveStatus {string} "
        + "and trainingData including trainingDate {string} trainingDuration: {string} and actionType {string}")
    public void givenInputDataToUpdateTrainerWorkloadUsernameIsMissing(
        String firstName, String lastName, String status,
        String trainingDate, String trainingDuration, String actionType) {
        updateTrainerWorkloadRequestDto.setFirstName(firstName);
        updateTrainerWorkloadRequestDto.setLastName(lastName);
        updateTrainerWorkloadRequestDto.setIsActive(Boolean.valueOf(status));
        updateTrainerWorkloadRequestDto.setTrainingDate(LocalDateTime.parse(trainingDate));
        updateTrainerWorkloadRequestDto.setTrainingDuration(new BigDecimal(trainingDuration));
        updateTrainerWorkloadRequestDto.setActionType(ActionType.valueOf(actionType));
    }

    /**
     * Constructs a UpdateTrainerWorkloadRequestDto object. trainingDuration is null.
     *
     * @param trainerUsername trainerUsername
     * @param firstName       firstName
     * @param lastName        lastName
     * @param status          trainer isActive status
     * @param trainingDate    trainingDate
     * @param actionType      actionType(ADD/DELETE)
     */
    @Given("a trainer with username {string}, firstName {string}, lastName {string}, isActiveStatus {string} "
        + "and trainingData including trainingDate {string} and actionType {string}")
    public void givenInputDataToUpdateTrainerWorkloadTrainingDurationIsMissing(
        String trainerUsername, String firstName, String lastName, String status,
        String trainingDate, String actionType) {
        updateTrainerWorkloadRequestDto.setUsername(trainerUsername);
        updateTrainerWorkloadRequestDto.setFirstName(firstName);
        updateTrainerWorkloadRequestDto.setLastName(lastName);
        updateTrainerWorkloadRequestDto.setIsActive(Boolean.valueOf(status));
        updateTrainerWorkloadRequestDto.setTrainingDate(LocalDateTime.parse(trainingDate));
        updateTrainerWorkloadRequestDto.setActionType(ActionType.valueOf(actionType));
    }

    /**
     * Simulates updating trainer's workload.
     */
    @When("the client sends an update message to ActiveMQ to update the trainer's workload")
    public void theClientSendsARequestToUpdateTrainerWorkload() {
        doNothing().when(updateTrainerWorkloadSender).send(any(String.class));
        updateTrainerWorkloadReceiverService.receiveMessage(updateTrainerWorkloadRequestDto,
            headers);
    }

    /**
     * Simulates updating trainer's workload when trainerUsername is null.
     */
    @When("the client sends an update message to ActiveMQ to update the trainer's"
        + " workload \\(trainer username is missing)")
    public void theClientSendsARequestToUpdateTrainerWorkloadWithMissingUsername() {
        doNothing().when(updateTrainerWorkloadSender).send(any(String.class));
        exception = assertThrows(ValidationException.class, () ->
            updateTrainerWorkloadReceiverService.receiveMessage(updateTrainerWorkloadRequestDto, headers));
    }

    /**
     * Simulates updating trainer's workload when trainingDuration is null.
     */
    @When("the client sends an update message to ActiveMQ to update the trainer's "
        + "workload \\(trainingDuration is missing)")
    public void theClientSendsARequestToUpdateTrainerWorkloadWithMissingTrainingDuration() {
        doNothing().when(updateTrainerWorkloadSender).send(any(String.class));
        exception = assertThrows(ValidationException.class, () ->
            updateTrainerWorkloadReceiverService.receiveMessage(updateTrainerWorkloadRequestDto, headers));
    }

    /**
     * Asserts that no exception was thrown.
     */
    @Then("no exception should be thrown")
    public void result() {
        verify(updateTrainerWorkloadSender).send(any(String.class));
    }

    @Then("a validation error should occur with the message {string}")
    public void responseShouldContainTheGivenErrorMessage(String expectedMessage) {
        verifyNoInteractions(updateTrainerWorkloadSender);
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}
