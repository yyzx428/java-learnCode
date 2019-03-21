package com.example.jpademo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(updatable = false, name = "createTime")
    @CreationTimestamp
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column
    private LocalDateTime updateTime;

    @Column
    private String createUser;

    @Column
    private String updateUser;

    @Column
    private String remark;
}
