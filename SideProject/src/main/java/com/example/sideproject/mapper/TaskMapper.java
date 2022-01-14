package com.example.sideproject.mapper;

import com.example.sideproject.pojo.Task;
import com.example.sideproject.pojo.TodayTask;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TaskMapper {

    public void addTask(int user_id, String name, boolean isPublic);

    public int queryTaskByUserName(int user_id, String name);

    public Task queryTaskById(int id);

    public List<Integer> queryTaskRepeatById(int id);

    public void addRepeat(int task_id, int day);

    public void schedule(int task_id, int day, boolean schedule);

    public void updateTask(int task_id, String name, boolean isPublic);

    public List<Task> queryTaskByUser(int user_id);

    public List<Task> queryAllTask();

    public void deleteTask(int task_id);

    public void deleteRepeat(int task_id);

    public List<TodayTask> getTodayTaskById(int id);

    public void done(int id);

    public TodayTask queryTodayTaskById(int id);

    public List<Integer> queryAllTodayUser();

    public int queryNumofTodayTask(int user_id);

    public int queryNumOfDoneTask(int user_id);

    public List<Task> search(String s);
}
