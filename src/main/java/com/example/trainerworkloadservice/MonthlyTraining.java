//package com.example.trainerworkloadservice;
//
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
//@Getter
//@Setter
//@Table(name = "monthly_trainings")
//public class MonthlyTraining {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "training_month")
//    private int month;
//
//    @Column(name = "training_year")
//    private int year;
//
//    @Column(name = "summary_duration")
//    private int summaryDuration;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "trainer_id")
//    private TrainerEntity trainer;
//
//}