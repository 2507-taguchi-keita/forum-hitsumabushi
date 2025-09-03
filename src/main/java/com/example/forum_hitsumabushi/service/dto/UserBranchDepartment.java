package com.example.forum_hitsumabushi.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
    private Date createdDate;
    private Date updatedDate;

    // branches
    private String branchName;

    // departments
    private String departmentName;
}