package com.example.jpademo.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Student")
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class Student extends BaseEntity {
    @Column
    private String name;
    @Column
    private String className;
}
