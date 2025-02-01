//package com.example.trainerworkloadservice.entities;
//
//import com.example.trainerworkloadservice.entities.TrainingYear;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Table(name = "training_months")
//@Getter
//@Setter
//public class TrainingMonth {
//    //    @Id
//    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    //    private Long monthId;
//    //
//    //    @Column(name = "\"month\"", nullable = false)
//    //    private Integer month;
//    //
//    //    private Integer durationMinutes;
//    //
//    //    @ManyToOne
//    //    @JoinColumn(name = "year_id")
//    //    private TrainingYear trainingYear;
//    //
//    //    // Constructors, Getters, and Setters
//    //    public TrainingMonth() {
//    //    }
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    //@Column(name = "month")
//    @Column(name = "\"month\"")
//    private String month;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "year_id")
//    private TrainingYear year;
//
//    @Column(name = "trainings_duration_sum")
//    private int trainingsDurationSum;
//
//    // Standard getters and setters
//
//}