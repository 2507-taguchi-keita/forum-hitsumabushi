package com.example.forum_hitsumabushi.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DepartmentForm {

    private Integer id;
    private String name;
    private LocalDate createdDate;
    private LocalDate updatedDate;
}
