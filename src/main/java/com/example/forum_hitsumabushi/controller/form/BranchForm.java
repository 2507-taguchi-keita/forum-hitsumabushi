package com.example.forum_hitsumabushi.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BranchForm {

    private Integer id;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
