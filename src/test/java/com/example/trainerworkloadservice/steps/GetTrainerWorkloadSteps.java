package com.example.trainerworkloadservice.steps;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import com.example.trainerworkloadservice.dto.requestdto.TrainerWorkloadRequestDto;
import com.example.trainerworkloadservice.services.GetTrainerWorkloadSender;
import com.example.trainerworkloadservice.services.TrainerWorkloadReceiverService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.validation.ValidationException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
@RequiredArgsConstructor
public class GetTrainerWorkloadSteps {

    private final TrainerWorkloadRequestDto trainerWorkloadRequestDto = new TrainerWorkloadRequestDto();
    private final TrainerWorkloadReceiverService trainerWorkloadReceiverService;
    private final Map<String, Object> headers = Map.of("traceId", "12345");
    private final GetTrainerWorkloadSender getTrainerWorkloadSender;
    private Exception exception;

    /**
     * Constructs a TrainerWorkloadRequestDto object.
     *
     * @param trainerUsername trainerUsername
     * @param trainingYear    trainingYear
     * @param trainingMonth   trainingMonth
     */
    @Given("a trainer with username {string} and trainingYear {string} and trainingMonth {string}")
    public void givenInputDataToRetrieveTrainerWorkload(String trainerUsername, String trainingYear,
                                                        String trainingMonth) {
        trainerWorkloadRequestDto.setUsername(trainerUsername);
        trainerWorkloadRequestDto.setTrainingYear(trainingYear);
        trainerWorkloadRequestDto.setTrainingMonth(trainingMonth);
    }

    /**
     * Constructs a TrainerWorkloadRequestDto object. Username is null.
     *
     * @param trainingYear  trainingYear
     * @param trainingMonth trainingMonth
     */
    @Given("trainingYear {string} and trainingMonth {string}")
    public void givenInputDataToRetrieveTrainerWorkloadUsernameIsMissing(String trainingYear, String trainingMonth) {
        trainerWorkloadRequestDto.setTrainingYear(trainingYear);
        trainerWorkloadRequestDto.setTrainingMonth(trainingMonth);
    }

    /**
     * Constructs a TrainerWorkloadRequestDto object. Invalid trainingMonth.
     *
     * @param trainerUsername trainerUsername
     * @param trainingYear    trainingYear
     * @param trainingMonth   trainingMonth
     */
    @Given("a trainer with username {string} and trainingYear {string} and invalid trainingMonth {string}")
    public void givenInputDataToRetrieveTrainerWorkloadTrainingMonthIsNotValid(
        String trainerUsername, String trainingYear, String trainingMonth) {
        trainerWorkloadRequestDto.setUsername(trainerUsername);
        trainerWorkloadRequestDto.setTrainingYear(trainingYear);
        trainerWorkloadRequestDto.setTrainingMonth(trainingMonth);
    }

    /**
     * Simulates updating trainer's workload.
     */
    @When("the client sends a message to ActiveMQ to retrieve trainer's workload")
    public void theClientSendsARequestToUpdateTrainerWorkload() {
        doNothing().when(getTrainerWorkloadSender).send(any(String.class));
        trainerWorkloadReceiverService.processWorkloadRequest(trainerWorkloadRequestDto, headers);
    }

    /**
     * Simulates getting trainer's workload when trainerUsername is null.
     */
    @When("the client sends a message to ActiveMQ to retrieve trainer's workload \\(trainer username is missing)")
    public void theClientSendsARequestToRetrieveTrainerWorkloadWithMissingUsername() {
        doNothing().when(getTrainerWorkloadSender).send(any(String.class));
        exception = assertThrows(ValidationException.class, () ->
            trainerWorkloadReceiverService.processWorkloadRequest(trainerWorkloadRequestDto, headers));
    }

    /**
     * Simulates retrieving trainer's workload when trainingMonth is not valid.
     */
    @When("the client sends a message to ActiveMQ to retrieve trainer's workload \\(invalid trainingMonth)")
    public void theClientSendsARequestToGetTrainerWorkloadWithInvalidTrainingMonth() {
        doNothing().when(getTrainerWorkloadSender).send(any(String.class));
        exception = assertThrows(ValidationException.class, () ->
            trainerWorkloadReceiverService.processWorkloadRequest(trainerWorkloadRequestDto, headers));
    }

    /**
     * Asserts that no exception was thrown.
     */
    @Then("no exception should be thrown when retrieving trainer's workload")
    public void success() {
        verify(getTrainerWorkloadSender).send(any(String.class));
    }

    /**
     * Asserts that the exception contains the given error.
     *
     * @param expectedMessage expected error message
     */
    @Then("a validation error should occur with the message {string} when retrieving trainer's workload")
    public void responseForRetrievingTrainerWorkloadShouldContainTheGivenErrorMessage(String expectedMessage) {
        verifyNoInteractions(getTrainerWorkloadSender);
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}
