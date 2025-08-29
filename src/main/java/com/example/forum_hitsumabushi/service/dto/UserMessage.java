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
public class UserMessage {

    private Integer id;
    private String title;
    private String text;
    private String category;
    private Date createdDate;
    private Date updatedDate;
    private Integer userId;
    private String account;
    private String name;
    private Integer branchId;
    private Integer departmentId;
}
