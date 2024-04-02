package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Student")
@Table(name = "Student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @Column(name = "year_of_admission")
    private int yearOfAdmission;

    @Column(name = "idnp", unique = true, nullable = false, length = 13)
    private String idnp;
}