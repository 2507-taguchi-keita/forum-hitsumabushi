package com.example.forum_hitsumabushi.controller;

import com.example.forum_hitsumabushi.controller.form.UserForm;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView home(HttpSession session) {
        ModelAndView mav = new ModelAndView();

        // 初回アクセス時の処理
        UserForm user = (UserForm) session.getAttribute("loginUser");
        if (user == null) {
            return new ModelAndView("forward:/login");
        }

        mav.setViewName("/home");
        return mav;
    }
}
