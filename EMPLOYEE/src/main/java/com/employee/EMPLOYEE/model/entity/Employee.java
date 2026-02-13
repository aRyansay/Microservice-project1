package com.employee.EMPLOYEE.model.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name="employees")
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String empName;
    private String empEmail;
    private String empcode;
    private String companyName;
}

