package com.example.trainerworkloadservice.config;

import com.example.trainerworkloadservice.TrainerWorkloadServiceApplication;
import com.example.trainerworkloadservice.services.GetTrainerWorkloadSender;
import com.example.trainerworkloadservice.services.UpdateTrainerWorkloadSender;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@AutoConfigureMockMvc
@Slf4j
@CucumberContextConfiguration
@SpringBootTest
@TestPropertySource(properties = "eureka.client.enabled=false")
@ContextConfiguration(classes = TrainerWorkloadServiceApplication.class)
@TestPropertySource(locations = "classpath:application.yml")
@MockitoBean(types = {UpdateTrainerWorkloadSender.class, GetTrainerWorkloadSender.class})
public class CucumberConfiguration {
}



