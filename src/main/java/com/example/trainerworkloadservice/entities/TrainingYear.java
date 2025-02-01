//package com.example.trainerworkloadservice.entities;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.Table;
//import java.util.List;
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Table(name = "training_years")
//@Getter
//@Setter
//public class TrainingYear {
//    //    @Id
//    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    //    private Long yearId;
//    //
//    //    @Column(name = "\"year\"", nullable = false)
//    //    private Integer year;
//    //
//    //    @ManyToOne
//    //    @JoinColumn(name = "trainer_id")
//    //    private Trainer trainer;
//    //
//    //    @OneToMany(mappedBy = "trainingYear", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    //    private List<TrainingMonth> trainingMonths;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    //@Column(name = "year")
//    @Column(name = "\"year\"")
//    private int year;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "username")
//    //@JoinColumn(name = "trainer_id")
//    private Trainer trainer;
//
//    @OneToMany(mappedBy = "year", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<TrainingMonth> months;
//
//    // Standard getters and setters
//}