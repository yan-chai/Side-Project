package com.example.sideproject.controller;

import com.example.sideproject.mapper.UserMapper;
import com.example.sideproject.pojo.User;
import com.example.sideproject.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserController {

    private UserService service;

    UserController (UserService service) {
        this.service = service;
    }

    @PostMapping("/api/auth")
    public RedirectView login(String email, String pwd, RedirectAttributes redirectAttributes) {
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(email, pwd);

        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            redirectView.setUrl("/login");
            redirectAttributes.addFlashAttribute("msg", "Unknown account");
            return redirectView;
        } catch (IncorrectCredentialsException e) {
            redirectView.setUrl("/login");
            redirectAttributes.addFlashAttribute("msg", "Wrong Password");
            return redirectView;
        }
        redirectView.setUrl("/home");
        return redirectView;
    }

    @RequestMapping(value = "/api/regi", method = RequestMethod.POST)
    public RedirectView register(String email, String username, String pwd, RedirectAttributes redirectAttributes) {
        service.addUser(email,username, pwd);
        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        redirectView.setUrl("/login");
        redirectAttributes.addFlashAttribute("msg", "Success, please login!");
        return redirectView;
    }

    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/login";
    }

    @RequestMapping("/profile")
    public String profile(Model model) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        model.addAttribute("name", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "user/profile";
    }

    @RequestMapping(value = "/user/name", method = RequestMethod.GET)
    public String changeNameUI(Model model) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        model.addAttribute("name", user.getUsername());
        return "user/name";
    }

    @RequestMapping(value = "/user/name", method = RequestMethod.POST)
    public RedirectView changeName(String name) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        service.updateName(user.getId(), name);
        user.setUsername(name);
        redirectView.setUrl("/profile");
        return redirectView;
    }

    @RequestMapping(value = "/user/pwd", method = RequestMethod.GET)
    public String changePwdUI() {
        return "user/pwd";
    }

    @RequestMapping(value = "/user/pwd", method = RequestMethod.POST)
    public RedirectView changePwd(String pwd) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        service.updatePwd(user.getId(), pwd);
        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        redirectView.setUrl("/home");
        return redirectView;
    }
}
