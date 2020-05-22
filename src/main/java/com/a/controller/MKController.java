package com.a.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MKController {
    @RequestMapping("/mk")
    public String markdown(){
        return "editor/editormd";
    }

    @RequestMapping("/article/addArticle")
    public String upload(){
        return "redirect:/main";
    }

}