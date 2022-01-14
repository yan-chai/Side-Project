package com.example.sideproject.controller;

import com.example.sideproject.mapper.TaskMapper;
import com.example.sideproject.pojo.Task;
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
public class UserTaskController {

    private TaskMapper taskMapper;

    public UserTaskController(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPage() {
        return "task/addTask";
    }

    @RequestMapping(value = "/api/add", method = RequestMethod.POST)
    public RedirectView addTask(String name, String[] checkbox, String is_public) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (Integer.parseInt(is_public) == 1) {
            taskMapper.addTask(user.getId(), name, true);
        } else {
            taskMapper.addTask(user.getId(), name, false);
        }

        int task_id = taskMapper.queryTaskByUserName(user.getId(), name);
        for (int i=1; i<8; i++) {
            taskMapper.addRepeat(task_id, i);
        }
        for (String s : checkbox) {
            taskMapper.schedule(task_id, Integer.parseInt(s), true);
        }
        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        redirectView.setUrl("/task");
        return redirectView;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        model.addAttribute("username", user.getUsername());
        model.addAttribute("numDay", user.getDays());
        model.addAttribute("tasks", taskMapper.getTodayTaskById(user.getId()));
        return "index";
    }

    @RequestMapping(value = "/task", method = RequestMethod.GET)
    public String userTask(Model model) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Task> list = taskMapper.queryTaskByUser(user.getId());
        model.addAttribute("tasks", list);
        return "task/myTask";
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public String allTask(Model model) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Task> list = taskMapper.queryAllTask();
        model.addAttribute("tasks", list);
        return "task/allTask";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editTask(@RequestParam int id, Model model) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Task task = taskMapper.queryTaskById(id);
        if (task == null) {
            return "error/noexist";
        }
        if (task.getUser_id() != user.getId()) {
            return "error/error401";
        }
        List<Integer> days = taskMapper.queryTaskRepeatById(id);
        boolean[] flags = new boolean[]{false, false, false, false, false, false, false};
        for (int i : days) {
            flags[i-1] = true;
        }
        for (int i = 0; i < flags.length; i++) {
            model.addAttribute("flag"+(i+1), flags[i]);
        }
        model.addAttribute("name", task.getName());
        model.addAttribute("id", id);
        if (task.is_public()) {
            model.addAttribute("public", true);
            model.addAttribute("non_public", false);
        } else {
            model.addAttribute("public", false);
            model.addAttribute("non_public", true);
        }

        return "task/editTask";
    }

    @RequestMapping(value = "/api/edit", method = RequestMethod.POST)
    public RedirectView editTask(@RequestParam int id, String name, String[] checkbox, String is_public) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Task task = taskMapper.queryTaskById(id);
        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        if (task == null) {
            redirectView.setUrl("/401");
        } else if (task.getUser_id() != user.getId()) {
            redirectView.setUrl("/noexist");
        } else {
            boolean[] flags = new boolean[]{false, false, false, false, false, false, false};
            for (String s : checkbox) {
                flags[Integer.parseInt(s)-1] = true;
            }
            for (int i = 0; i < flags.length; i++) {
                taskMapper.schedule(id, i+1, flags[i]);
            }
            if (Integer.parseInt(is_public) == 1) {
                taskMapper.updateTask(id, name, true);
            } else {
                taskMapper.updateTask(id, name, false);
            }

            redirectView.setUrl("/task");
        }
        return redirectView;
    }

    @RequestMapping(value = "/delete")
    public RedirectView delete(@RequestParam int id) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Task task = taskMapper.queryTaskById(id);
        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        if (task == null) {
            redirectView.setUrl("/401");
        } else if (task.getUser_id() != user.getId()) {
            redirectView.setUrl("/noexist");
        } else {
            taskMapper.deleteTask(id);
            taskMapper.deleteRepeat(id);
            redirectView.setUrl("/task");
        }
        return redirectView;
    }

}
