package com.example.forum_hitsumabushi.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "branchdepartment")
@Getter
@Setter
public class BranchDepartment {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer branchId;

    @Column
    private Integer departmentId;

    @Column
    private boolean isAll;

    @CreationTimestamp
    @Column(updatable = false, name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    @UpdateTimestamp
    private Date updatedDate;
}
