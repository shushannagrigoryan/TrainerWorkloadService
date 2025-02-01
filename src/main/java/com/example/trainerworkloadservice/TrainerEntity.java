//package com.example.trainerworkloadservice;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.Table;
//import java.util.ArrayList;
//import java.util.List;
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "trainers")
//public class TrainerEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "username", nullable = false, unique = true)
//    private String username;
//
//    @Column(name = "first_name", nullable = false)
//    private String firstName;
//
//    @Column(name = "last_name", nullable = false)
//    private String lastName;
//
//    @Column(name = "is_active")
//    private boolean isActive;
//
//    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<MonthlyTraining> monthlyTrainings = new ArrayList<>();
//}