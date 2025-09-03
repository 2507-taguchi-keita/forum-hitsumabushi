package com.example.forum_hitsumabushi.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBranchDepartment {

    // users
    private Integer id;
    private String account;
    private String name;
    private Integer branchId;
    private Integer departmentId;
    private Integer isStopped;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // branches
    private String branchName;

    // departments
    private String departmentName;
}