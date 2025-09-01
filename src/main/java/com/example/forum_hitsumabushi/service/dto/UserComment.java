package com.example.forum_hitsumabushi.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
