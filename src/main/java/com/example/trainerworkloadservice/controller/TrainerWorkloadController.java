package com.example.trainerworkloadservice.controller;

import com.example.trainerworkloadservice.dto.requestdto.TrainerWorkloadRequestDto;
import com.example.trainerworkloadservice.dto.responsedto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TrainerWorkloadController {

    @PostMapping("/updateWorkload")
    public ResponseEntity<ResponseDto<String>> updateTrainerWorkload(@RequestBody TrainerWorkloadRequestDto request) {
        System.out.println("Request to update the workload of trainer with username: " + request.getUsername());
        //TODO update workload
        ResponseDto<String> responseDto =
            new ResponseDto<>(null, "Successfully updated trainer's workload.");

        return ResponseEntity.ok(responseDto);
    }



}
