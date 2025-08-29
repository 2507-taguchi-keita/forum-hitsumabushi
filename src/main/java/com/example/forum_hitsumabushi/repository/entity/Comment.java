package com.example.forum_hitsumabushi.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment {

    @Id
    private Integer id;
    private String text;
    private Integer messageId;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "updated_date")
    private Date updatedDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
