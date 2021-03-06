package com.a.controller;

import com.a.entity.Article;
import com.a.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

//这个不知道是干啥的，问倪某
@Controller
public class CourseSearchController {

    @Autowired
    ArticleRepository articleRepository;
    @GetMapping(value = {"/course"})
    public String search(@RequestParam("way") String way,
                         @RequestParam("searchname") String searchname,Model model){
        List<Article> articleList=null;
        List<Article> courseList=new ArrayList<>();
        List<Article> commentList=new ArrayList<>();
        List<Article> coursesList=null;
        if(way.compareTo("title")==0) {
            articleList = articleRepository.findArticleByTitleContaining(searchname);
            }
        else if(way.compareTo("author")==0) {
            articleList = articleRepository.findArticleByAuthorContaining(searchname);
            }
//            else if(way.compareTo("levelmore")==0) {
//                articleList = articleRepository.findArticleByLevelGreaterThan(Integer.parseInt(searchname));
//                model.addAttribute("msg", "等级关键词:大于"+searchname+"。已找到"+articleList.size()+"个相关帖子");
//            }
//            else if(way.compareTo("levelless")==0) {
//                articleList = articleRepository.findArticleByLevelLessThan(Integer.parseInt(searchname));
//                model.addAttribute("msg", "等级关键词:小于"+searchname+"。已找到"+articleList.size()+"个相关帖子");
//            }

        int l=articleList.size();
        for(int i=0;i<l;i++){
            if(articleList.get(i).categoryName.compareTo("课程推荐")==0){
                commentList.add(articleList.get(i));
            }
            else if(articleList.get(i).categoryName.compareTo("课程")==0){
                courseList.add(articleList.get(i));
            }
        }
        int t=courseList.size()+commentList.size();
        if(way.compareTo("title")==0) {
            model.addAttribute("msg", "标题关键词:"+searchname+"。已找到"+t+"个相关帖子");
        }
        else if(way.compareTo("author")==0) {
            model.addAttribute("msg", "作者关键词:"+searchname+"。已找到"+t+"个相关帖子");
        }
        model.addAttribute("courses", courseList);
        model.addAttribute("articles", commentList);
        model.addAttribute("category","Course");
        List<Article> hot = articleRepository.findAll();
        hot.sort(comparing(Article::getlikes).reversed());
        List<Article> sidebar = new ArrayList<>();
        for(int i=0;i<5;i++){
            if(hot.size()<=i)
                break;
            sidebar.add(hot.get(i));
        }
        model.addAttribute("sidebar",sidebar);
        return "Course";
    }
}