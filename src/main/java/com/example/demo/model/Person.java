package com.example.demo.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // define sebuah table didatabase
@Table(name = "tb_person")
@Data // anotasi data, gantinya setter getter dari lombok
@AllArgsConstructor // constructor dengan parameter semua properti
@NoArgsConstructor // constructor tidak menggunakan parameter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 255, nullable = false)
    private String name;
    @Column(length = 255, nullable = false)
    private String email;
    @Column(nullable = false)
    private LocalDate birthdate;
    @Column(length = 1, nullable = false)
    private Character gender;

}