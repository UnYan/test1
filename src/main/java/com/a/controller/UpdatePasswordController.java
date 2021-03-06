package com.a.controller;
import com.a.entity.User;
import com.a.repository.UserRepository;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
@Controller
public class UpdatePasswordController {
    @Autowired
    UserRepository userRepository;
    int s;
    @PostMapping(value = "/updatepassword")
    public String updatepassword(@RequestParam("username") String username,
                                 @RequestParam("email") String email,
                                 @RequestParam("newpassword") String password1,
                                 @RequestParam("confirmpassword") String password2,
                                 Model model)
    {
        s=userRepository.findByUsernameAndEmail(username,email).size();
        if (s==0){
            model.addAttribute("msg", "用户名或邮箱不存在");
            return "/retrievepassword";
        }
        if (password1.compareTo(password2)!=0){
            model.addAttribute("msg", "密码不一致");
            return "/retrievepassword";
        }
        User tmp =userRepository.findByUsernameAndEmail(username,email).get(0);
        tmp.setPassword(password1);
        userRepository.save(tmp);
        return "redirect:/";
    }
    @PostMapping(value = "/settingupdatepassword")
    public String settingupdatepassword(@RequestParam("email") String email,
                                @RequestParam("oldpassword") String oldpassword,
                                 @RequestParam("newpassword") String password1,
                                 @RequestParam("confirmpassword") String password2,
                                 Model model)
    {
        s=userRepository.findByEmail(email).size();
        User tmp =userRepository.findByEmail(email).get(0);
        if (s==0){
            model.addAttribute("msg", "邮箱不存在");
            return "/setting";
        }
        else if (password1.compareTo(password2)!=0){
            model.addAttribute("msg", "新密码不一致");
            return "/setting";
        }
        else if (tmp.getPassword().compareTo(oldpassword)!=0){
            model.addAttribute("msg", "原密码错误");
            return "/setting";
        }
        else {
            tmp.setPassword(password1);
            userRepository.save(tmp);
            model.addAttribute("msg", "修改成功");
            System.out.println(1);
            return "/setting";
        }
    }
}