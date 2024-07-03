package com.boda.boda.MVCController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class MvcController {
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/home")
    public String home(){
        return "dasbroad";
    }
}
