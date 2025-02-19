package com.example.trainerworkloadservice.repositories;

import com.example.trainerworkloadservice.entities.TrainerWorkload;
import java.util.Optional;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrainerWorkloadRepository extends MongoRepository<TrainerWorkload, String> {
    Optional<TrainerWorkload> findByUsername(String username);

    @Aggregation(pipeline = {
        "{ $match: { 'username': ?0 } }",
        "{ $project: { trainingYears: { $objectToArray: '$trainingYears' } } }",
        "{ $unwind: '$trainingYears' }",
        "{ $match: { 'trainingYears.k': ?1 } }",
        "{ $project: { months: { $objectToArray: '$trainingYears.v.months' } } }",
        "{ $unwind: '$months' }",
        "{ $match: { 'months.k': ?2 } }",
        "{ $project: { _id: 0, trainingDuration: { $toDouble: '$months.v.trainingDuration' } } }"
    })
    Optional<Double> findTrainingWorkloadByUsernameAndYearAndMonth(String username, String year, String month);
}
