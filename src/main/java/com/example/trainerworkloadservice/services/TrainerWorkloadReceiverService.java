package com.example.trainerworkloadservice.services;

import com.example.trainerworkloadservice.dto.requestdto.TrainerWorkloadRequestDto;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class TrainerWorkloadReceiverService {
    private static final String TRAINER_WORKLOAD_REQUEST_QUEUE = "trainer-workload-request-queue";
    private static final long TIMEOUT_THRESHOLD = 5000;
    private final TrainerWorkloadHelper trainerWorkloadHelper;
    private final GetTrainerWorkloadSender getTrainerWorkloadSender;

    /**
     * Listener for the TRAINER_WORKLOAD_REQUEST_QUEUE.
     */
    @JmsListener(destination = TRAINER_WORKLOAD_REQUEST_QUEUE)
    public void processWorkloadRequest(@Valid TrainerWorkloadRequestDto request,
                                       @Headers Map<String, Object> headers) {
        String trace = (String) headers.get("traceId");
        MDC.put("traceId", trace);
        log.debug("Processing WorkloadRequest");
        long startTime = System.currentTimeMillis();

        BigDecimal workload = trainerWorkloadHelper.getTrainerWorkload(request.getUsername(),
            Integer.parseInt(request.getTrainingYear()),
            Integer.parseInt(request.getTrainingMonth()));

        long time = System.currentTimeMillis() - startTime;
        if (time > TIMEOUT_THRESHOLD) {
            log.warn("Processing took too long: {}", time);
            throw new RuntimeException("Processing took too long.");
        }

        getTrainerWorkloadSender.send(workload.toString());
        MDC.clear();
    }
}
