Feature: Update trainer's workload

  Scenario: Successfully updating trainer's workload
    Given a trainer with username "testUsername" firstName "fN", lastName "lN", isActiveStatus "true" and trainingData including trainingDate "2025-02-28T09:00:00" trainingDuration: "60" and actionType "ADD"
    When the client sends an update message to ActiveMQ to update the trainer's workload
    Then no exception should be thrown

  Scenario: Failing to update trainer's workload due to missing username
    Given a trainer with firstName "fN", lastName "lN", isActiveStatus "true" and trainingData including trainingDate "2025-02-28T09:00:00" trainingDuration: "60" and actionType "ADD"
    When the client sends an update message to ActiveMQ to update the trainer's workload (trainer username is missing)
    Then a validation error should occur with the message "Username cannot be blank"


  Scenario: Failing to update trainer's workload due to missing trainingDuration
    Given a trainer with username "testUsername", firstName "fN", lastName "lN", isActiveStatus "true" and trainingData including trainingDate "2025-02-28T09:00:00" and actionType "ADD"
    When the client sends an update message to ActiveMQ to update the trainer's workload (trainingDuration is missing)
    Then a validation error should occur with the message "Training duration is required"
