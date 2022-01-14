package com.example.sideproject.service;

import com.example.sideproject.mapper.TaskMapper;
import com.example.sideproject.mapper.UserMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@Component
public class UpdateCurrentTask {

    @Autowired
    DataSource dataSource;
    @Autowired
    TaskMapper mapper;
    @Autowired
    UserMapper userMapper;

    //@Scheduled(fixedRate = 3000)
    @Scheduled(cron = "0 0 1 * * ?")
    public void scheduledTask() {
        List<Integer> users = mapper.queryAllTodayUser();
        for (int i : users) {
            if (mapper.queryNumOfDoneTask(i) == mapper.queryNumofTodayTask(i)) {
                userMapper.updateDays(i);
            }
        }
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        Connection con;
        try {
            con = dataSource.getConnection();
            con.prepareStatement("TRUNCATE TABLE today;").executeUpdate();
            String sql = "INSERT INTO today (name, user_id)" +
                    "    SELECT name, task.user_id FROM task Where id IN" +
                    "    (SELECT task_id FROM repeat_task WHERE day=?);";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, day);
            stmt.executeUpdate();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
