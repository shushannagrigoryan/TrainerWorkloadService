//package com.example.trainerworkloadservice;
//
//import jakarta.persistence.CollectionTable;
//import jakarta.persistence.Column;
//import jakarta.persistence.ElementCollection;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.MapKeyColumn;
//import jakarta.persistence.Table;
//import java.time.LocalDate;
//import java.util.HashMap;
//import java.util.Map;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "trainer_workload")
//@Getter
//@NoArgsConstructor
//public class TrainerWorkload {
//
//    @Id
//    private String username; // Primary Key
//
//    private String firstName;
//    private String lastName;
//    private boolean isActive;
//
//    @ElementCollection
//    @CollectionTable(name = "trainer_workload_summary", joinColumns = @JoinColumn(name = "username"))
//    @MapKeyColumn(name = "month_year")
//    @Column(name = "total_duration")
//    private Map<String, Integer> workload = new HashMap<>(); // "YYYY-MM" -> duration
//
//    /** constructor. */
//    public TrainerWorkload(String username, String firstName, String lastName, boolean isActive) {
//        this.username = username;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.isActive = isActive;
//    }
//
//
//    public void addTraining(LocalDate date, int duration) {
//        String key = date.getYear() + "-" + date.getMonthValue();
//        workload.merge(key, duration, Integer::sum);
//    }
//
//    public void removeTraining(LocalDate date, int duration) {
//        String key = date.getYear() + "-" + date.getMonthValue();
//        workload.computeIfPresent(key, (k, v) -> (v > duration) ? v - duration : null);
//    }
//}
