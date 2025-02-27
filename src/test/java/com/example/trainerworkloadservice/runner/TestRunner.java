package com.example.trainerworkloadservice.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = {"src/test/resources"},
    plugin = {"pretty"},
    glue = {"com.example.trainerworkloadservice.steps", "com.example.trainerworkloadservice.config"}
)
public class TestRunner {
}
