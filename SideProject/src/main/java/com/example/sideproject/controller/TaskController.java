package com.example.sideproject.controller;

import com.example.sideproject.mapper.TaskMapper;
import com.example.sideproject.mapper.UserMapper;
import com.example.sideproject.pojo.Task;
import com.example.sideproject.pojo.TodayTask;
import com.example.sideproject.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.List;

@Controller
public class TaskController {

    private TaskMapper taskMapper;

    public TaskController(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @RequestMapping(value = "/done", method = RequestMethod.GET)
    public RedirectView doneTask(@RequestParam int id) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        TodayTask task = taskMapper.queryTodayTaskById(id);
        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        if (task == null) {
            redirectView.setUrl("/noexist");
        } else if (task.getUser_id() != user.getId()) {
            redirectView.setUrl("/401");
        } else {
            taskMapper.done(id);
            redirectView.setUrl("/home");
        }
        return redirectView;
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    public RedirectView transferTask(@RequestParam int id) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Task task = taskMapper.queryTaskById(id);
        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        if (task == null) {
            redirectView.setUrl("/noexist");
        } else {
            taskMapper.addTask(user.getId(), task.getName(), false);
            int task_id = taskMapper.queryTaskByUserName(user.getId(), task.getName());
            for (int i=1; i<8; i++) {
                taskMapper.addRepeat(task_id, i);
            }
            redirectView.setUrl("/task");
        }
        return redirectView;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(String search, Model model) {
        List<Task> tasks = taskMapper.search(search);
        model.addAttribute("tasks", tasks);
        return "task/search";
    }
}
