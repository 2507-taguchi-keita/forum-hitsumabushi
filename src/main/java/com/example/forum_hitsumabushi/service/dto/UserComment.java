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
public class UserComment {

    private Integer id;
    private String account;
    private String name;
    private Integer branchId;
    private Integer userId;
    private Integer messageId;
    private String text;
    private Date createdDate;
    private Date updatedDate;
}
