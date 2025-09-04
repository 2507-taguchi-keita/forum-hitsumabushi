package com.example.forum_hitsumabushi.controller;

import com.example.forum_hitsumabushi.controller.form.UserForm;
import com.example.forum_hitsumabushi.service.UserService;
import com.example.forum_hitsumabushi.utils.CipherUtil;
import com.example.forum_hitsumabushi.validation.AccountCharaLimit;
import com.example.forum_hitsumabushi.validation.AccountNotWhitespace;
import com.example.forum_hitsumabushi.validation.PasswordCharaLimit;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserEditController {

    @Autowired
    UserService userService;

    // ユーザー編集画面の表示
    @GetMapping("/editUser/{id}")
    public ModelAndView editUser(@PathVariable Integer id, ModelMap model) {
        ModelAndView mav = new ModelAndView();

        // 初回アクセス：userForm初期化
        // ユーザー編集のバリデーションエラーでリダイレクトしてきた時：Flashスコープの値を代入
        if (!model.containsAttribute("userForm")) {
            model.addAttribute("userForm", new UserForm());
            mav.addObject("userForm", userService.editUser(id));
        }

        mav.addObject("branches", userService.findAllBranch());
        mav.addObject("departments", userService.findAllDepartment());

        mav.setViewName("/userEdit");
        return mav;
    }

    // ユーザー情報の更新
    @PutMapping("/updateUser/{id}")
    public ModelAndView updateUser(
            @ModelAttribute("userForm") @Validated({Default.class, AccountNotWhitespace.class, AccountCharaLimit.class}) UserForm userForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @PathVariable Integer id,
            @RequestParam("passwordChk") String passwordChk) {

        // 入力内容に関するバリデーション -----
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userForm", bindingResult);
            redirectAttributes.addFlashAttribute("userForm", userForm);
            return new ModelAndView("redirect:/editUser/{id}");
        }

        // パスワードチェック -----
        String password = userForm.getPassword();
        if (!password.matches("^[\\x20-\\x7E]{6,20}$")){
            redirectAttributes.addFlashAttribute("errorPassword", "パスワードは半角文字かつ6文字以上20文字以下で入力してください");
            return new ModelAndView("redirect:/editUser/{id}");
        } else if (!password.equals(passwordChk)) {
            redirectAttributes.addFlashAttribute("errorPassword", "パスワードと確認用パスワードが一致しません");
            return new ModelAndView("redirect:/editUser/{id}");
        }

        // 支社と部署の組み合わせチェック -----
        boolean resultBranchDepart = userService.findByBranchDepartment(userForm.getBranchId(), userForm.getDepartmentId());
        if (!resultBranchDepart) {
            redirectAttributes.addFlashAttribute("errorBranchDepart", "支社と部署の組み合わせが不正です");
            return new ModelAndView("redirect:/editUser/{id}");
        }

        // アカウントの重複チェック -----
        // ユーザー編集時はアカウント名とidでチェック（自身のidとの重複は許可する為）
        boolean resultAccount = userService.findByAccount(userForm.getId(), userForm.getAccount());
        if (!resultAccount) {
            redirectAttributes.addFlashAttribute("errorAccount", "アカウントが重複しています");
            return new ModelAndView("redirect:/editUser/{id}");
        }

        // パスワードの暗号化 -----
        String encPassword = CipherUtil.encrypt(userForm.getPassword());
        userForm.setPassword(encPassword);

        userService.saveUser(userForm);
        return new ModelAndView("redirect:/admin");
    }
}
