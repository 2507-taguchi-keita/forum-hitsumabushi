package com.example.forum_hitsumabushi.controller;

import com.example.forum_hitsumabushi.controller.form.MessageForm;
import com.example.forum_hitsumabushi.controller.form.UserForm;
import com.example.forum_hitsumabushi.repository.entity.Message;
import com.example.forum_hitsumabushi.service.MessageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;

    // 新規投稿の登録処理
    @PostMapping("/add")
    public ModelAndView addMessage(
            @ModelAttribute("messageForm") @Validated MessageForm messageForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        // 入力内容に関するバリデーション
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.messageForm", bindingResult);
            redirectAttributes.addFlashAttribute("messageForm", messageForm);
            return new ModelAndView("redirect:/new");
        }

        // NGワードのチェック
        String text = messageForm.getText();
        String[] keywords = {"hoge", "fuga", "piyo"};

        for (String keyword : keywords) {
            if (text.contains(keyword)) {
                redirectAttributes.addFlashAttribute("messageForm", messageForm);
                redirectAttributes.addFlashAttribute("errorCode", "不適切な表現(NGワード)が含まれています");
                redirectAttributes.addFlashAttribute("errorWord", keyword);
                return new ModelAndView("redirect:/new");
            }
        }

        UserForm loginUser = (UserForm) session.getAttribute("loginUser");
        messageForm.setUserId(loginUser.getId());
        messageService.saveMessage(messageForm);

        return new ModelAndView("redirect:/forum-hitsumabushi");
    }
}
