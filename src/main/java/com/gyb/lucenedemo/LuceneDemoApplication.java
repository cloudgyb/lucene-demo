package com.gyb.lucenedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@SpringBootApplication
@Controller
public class LuceneDemoApplication {

    @GetMapping("/test")
    public ModelAndView testFreemarker(HttpSession session){
        session.setAttribute("userName","耿渊博");
        ModelAndView modelAndView = new ModelAndView("test");
        modelAndView.addObject("userName","耿远博");
        return modelAndView;
    }

    public static void main(String[] args) {
        SpringApplication.run(LuceneDemoApplication.class, args);
    }

}
