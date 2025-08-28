package com.example.forum_hitsumabushi.controller;

import com.example.forum_hitsumabushi.controller.form.UserCommentForm;
import com.example.forum_hitsumabushi.controller.form.UserForm;
import com.example.forum_hitsumabushi.controller.form.UserMessageForm;
import com.example.forum_hitsumabushi.repository.entity.User;
import com.example.forum_hitsumabushi.service.CommentService;
import com.example.forum_hitsumabushi.service.MessageService;
import com.example.forum_hitsumabushi.service.UserService;
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
    public ModelAndView top(){
        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/home")
    //public ModelAndView home(Principal principal){
    public ModelAndView home(){
        //String account = principal.getName();
        ModelAndView mav = new ModelAndView("/home");
        //mav.addObject("isSoumuJinji", userService.isSoumuJinji(account));
        mav.addObject("comments", commentService.findAllComment());
        mav.addObject("messages", messageService.findAllMessage());
        return mav;
    }
}
