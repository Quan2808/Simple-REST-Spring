package com.service.studentservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "yearOfBirth")
    private int yearOfBirth;

    @Column(name = "phone")
    private String phone;

}
