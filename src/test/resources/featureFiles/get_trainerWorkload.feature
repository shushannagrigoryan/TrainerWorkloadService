Feature: Retrieve trainer's workload

  Scenario: Successfully retrieving trainer's workload
    Given a trainer with username "testUsername" and trainingYear "2025" and trainingMonth "6"
    When the client sends a message to ActiveMQ to retrieve trainer's workload
    Then no exception should be thrown when retrieving trainer's workload

  Scenario: Failing to retrieve trainer's workload due to missing username
    Given trainingYear "2025" and trainingMonth "6"
    When the client sends a message to ActiveMQ to retrieve trainer's workload (trainer username is missing)
    Then a validation error should occur with the message "Username can't be blank" when retrieving trainer's workload

  Scenario: Failing to retrieve trainer's workload due to invalid input data
    Given a trainer with username "testUsername" and trainingYear "2025" and invalid trainingMonth "13"
    When the client sends a message to ActiveMQ to retrieve trainer's workload (invalid trainingMonth)
    Then a validation error should occur with the message "Month must be between 1 and 12" when retrieving trainer's workload
