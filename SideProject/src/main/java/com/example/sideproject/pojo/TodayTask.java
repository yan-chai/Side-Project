package com.example.sideproject.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodayTask {
    private int id;
    private int user_id;
    private String name;
    private boolean isDone;
}
