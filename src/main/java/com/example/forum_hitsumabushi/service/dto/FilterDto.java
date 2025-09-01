package com.example.forum_hitsumabushi.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FilterDto {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String category;
}