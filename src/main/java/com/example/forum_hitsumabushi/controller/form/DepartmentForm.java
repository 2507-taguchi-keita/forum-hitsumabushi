package com.example.forum_hitsumabushi.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DepartmentForm {

    private Integer id;
    private String name;
    private Date createdDate;
    private Date updatedDate;
}
