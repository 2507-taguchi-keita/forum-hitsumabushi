package com.example.forum_hitsumabushi.controller;

import com.example.forum_hitsumabushi.controller.form.*;
import com.example.forum_hitsumabushi.service.CommentService;
import com.example.forum_hitsumabushi.service.MessageService;
import com.example.forum_hitsumabushi.service.UserService;
import com.example.forum_hitsumabushi.controller.form.UserForm;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

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
    //public ModelAndView home(Principal principal){
    public ModelAndView home(Model model, HttpSession session) {
        //String account = principal.getName();
        ModelAndView mav = new ModelAndView();

        // 初回アクセス時の処理
        UserForm user = (UserForm) session.getAttribute("loginUser");
        if (user == null) {
            return new ModelAndView("forward:/login");
        }

        //mav.addObject("isSoumuJinji", userService.isSoumuJinji(account));
        mav.addObject("comments", commentService.findAllComment());
        mav.addObject("messages", messageService.findAllUserMessages());

        UserForm loginUser = (UserForm) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);

        mav.setViewName("/home");
        return mav;
    }

    // 新規投稿画面の表示
    @GetMapping("/new")
    public ModelAndView newMessage(ModelMap model) {
        ModelAndView mav = new ModelAndView();

        // Flashスコープの messageForm に値が返ってきているかのチェック
        // 値なし＝初回アクセス（初期化）、値あり＝Flashスコープの値を代入（投稿入力に関するバリデーションでリダイレクトされた場合）
        if (!model.containsAttribute("messageForm")) {
            model.addAttribute("messageForm", new MessageForm());
        }

        mav.setViewName("/new");
        return mav;
    }

    // ユーザー管理画面の表示
    @GetMapping("/admin")
    public ModelAndView adminUser() {
        ModelAndView mav = new ModelAndView();

        List<UserForm> allUser = userService.findAllUser();

        mav.addObject("users", allUser);
        mav.setViewName("/user");
        return mav;
    }

    // 投稿の削除
    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteMessage(@PathVariable Integer id){
        messageService.deleteMessage(id);
        return new ModelAndView("redirect:/");
    }
}
