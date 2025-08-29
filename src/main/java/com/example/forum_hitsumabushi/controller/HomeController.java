package com.example.forum_hitsumabushi.controller;

import com.example.forum_hitsumabushi.controller.form.UserCommentForm;
import com.example.forum_hitsumabushi.controller.form.UserForm;
import com.example.forum_hitsumabushi.controller.form.UserMessageForm;
import com.example.forum_hitsumabushi.repository.entity.User;
import com.example.forum_hitsumabushi.service.CommentService;
import com.example.forum_hitsumabushi.service.MessageService;
import com.example.forum_hitsumabushi.service.UserService;
import com.example.forum_hitsumabushi.controller.form.UserForm;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @Autowired
    MessageService messageService;


    @GetMapping("/")
    public ModelAndView home(Principal principal,HttpSession session) {
        //String account = principal.getName();
        ModelAndView mav = new ModelAndView();

        // 初回アクセス時の処理
        UserForm user = (UserForm) session.getAttribute("loginUser");
        if (user == null) {
            return new ModelAndView("forward:/login");
        }

        mav.addObject("loginUser", user);
        //mav.addObject("isSoumuJinji", userService.isSoumuJinji(account));
        mav.addObject("comments", commentService.findAllComment());
        mav.addObject("messages", messageService.findAllUserMessages());
        mav.setViewName("/home");
        return mav;
    }
}
