package com.example.forum_hitsumabushi.controller;

import com.example.forum_hitsumabushi.controller.form.UserForm;
import com.example.forum_hitsumabushi.service.UserService;
import com.example.forum_hitsumabushi.validation.AccountNotWhitespace;
import com.example.forum_hitsumabushi.validation.PasswordNotWhitespace;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    UserService userService;

    // 初回アクセス時の処理：ログインページを表示
    @GetMapping("/login")
    public ModelAndView setViewLogin(@ModelAttribute("userForm") UserForm userForm, @RequestParam(value = "error", required = false) String error,
                                     RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();

        if ("unauthorized".equals(error)) {
            redirectAttributes.addFlashAttribute("errorCode", "ログインしてください");
            return new ModelAndView("redirect:/login");
        }
        mav.setViewName("/login");
        return mav;
    }

    // ログイン処理
    @PostMapping("/login")
    public ModelAndView login(
            @ModelAttribute("userForm") @Validated({AccountNotWhitespace.class, PasswordNotWhitespace.class}) UserForm userForm,
            BindingResult bindingResult,
            HttpSession session) {

        // ログイン情報に関するバリデーション
        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("login");
            mav.addObject("userForm", userForm);
            return mav;
        }

        UserForm user = userService.findUserLogin(userForm);

        // ログインチェック(usersテーブルとの照合)
        if (user == null) {
            List<String> errorMessages = new ArrayList<String>();
            errorMessages.add("ログインに失敗しました");
            ModelAndView mav = new ModelAndView("login");
            mav.addObject("errorCode", errorMessages);
            return mav;
        }

        //ログイン日時を更新する処理
        user.setLastLoginAt(LocalDateTime.now());
        userService.saveUser(user);

        session.setAttribute("loginUser", user);
        return new ModelAndView("redirect:/forum-hitsumabushi");
    }

    @PostMapping("/logout")
    public ModelAndView logout(HttpSession session){
        session.invalidate();
        return new ModelAndView("redirect:/login");
    }
}
