package com.gyb.lucenedemo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author gengyuanbo
 * 2019/04/04
 */
@Controller
@RequestMapping("/")
public class IndexController {

    public String index(){
        return "index";
    }
}
