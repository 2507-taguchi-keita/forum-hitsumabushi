package com.example.forum_hitsumabushi.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Column
    @Id
    private Integer id;
    @Column
    private String account;
    @Column
    private String name;
    @Column
    private Integer branchId;
    @Column
    private Integer departmentId;
    @Column
    private Integer isstopped;
    @Column(name = "created_date")
    @CreationTimestamp
    private Date createddate;
    @Column(name = "updated_date")
    @UpdateTimestamp
    private Date updateddate;
}
