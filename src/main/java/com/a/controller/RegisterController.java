package com.a.controller;


import com.a.entity.User;
import com.a.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

@Controller

public class RegisterController {
    @Autowired
    UserRepository userRepository;
    int s;
    String usernamePattern = "[a-zA-Z]*\\d+";
    String passwordPattern = "[a-zA-Z]*\\d+";
    String emailPattern;
    @PostMapping(value = "/register_apply")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password1") String password1,
                        @RequestParam("password2") String password2,
                        @RequestParam("email") String email,
                        Map<String,Object>map)
    {
        s=userRepository.findByUsername(username).size()+userRepository.findByEmail(email).size();
        if (s!=0){
            map.put("msg", "用户名或邮箱已存在");
            return "register";
        }
        if (password1.compareTo(password2)!=0){
            map.put("msg", "密码不一致");
            return "register";
        }

        User tmp =new User();
        tmp.username=username;
        tmp.level=1;
        tmp.setPassword(password1);
        tmp.email=email;
        tmp.setStatus(true);
        tmp.headImgName = "profile.png";
        userRepository.save(tmp);
        return "redirect:/";
    }
}