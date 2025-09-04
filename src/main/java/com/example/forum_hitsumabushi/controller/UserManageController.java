package com.example.forum_hitsumabushi.controller;

import com.example.forum_hitsumabushi.controller.form.UserForm;
import com.example.forum_hitsumabushi.service.UserService;
import com.example.forum_hitsumabushi.utils.CipherUtil;
import com.example.forum_hitsumabushi.validation.AccountCharaLimit;
import com.example.forum_hitsumabushi.validation.AccountNotWhitespace;
import com.example.forum_hitsumabushi.validation.PasswordCharaLimit;
import com.example.forum_hitsumabushi.validation.PasswordNotWhitespace;
import jakarta.validation.groups.Default;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class UserManageController {

    @Autowired
    UserService userService;

    // ユーザー登録画面の表示
    @GetMapping("/addUser")
    public ModelAndView addUser(ModelMap model) {
        ModelAndView mav = new ModelAndView();

        // 初回アクセス：userForm初期化
        // ユーザー登録のバリデーションエラーでリダイレクトしてきた時：Flashスコープの値を代入
        if (!model.containsAttribute("userForm")) {
            model.addAttribute("userForm", new UserForm());
        }

        mav.addObject("branches", userService.findAllBranch());
        mav.addObject("departments", userService.findAllDepartment());
        mav.setViewName("/userAdd");
        return mav;
    }

    // ユーザーの登録
    @PostMapping("/register")
    public ModelAndView registerUser(
            @ModelAttribute("userForm") @Validated({Default.class, AccountNotWhitespace.class, PasswordNotWhitespace.class, AccountCharaLimit.class, PasswordCharaLimit.class}) UserForm userForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @RequestParam("passwordChk") String passwordChk) {

        // 入力内容に関するバリデーション -----
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userForm", bindingResult);
            redirectAttributes.addFlashAttribute("userForm", userForm);
            return new ModelAndView("redirect:/addUser");
        }

        // パスワードチェック -----
        String password = userForm.getPassword();
        if (!password.equals(passwordChk)) {
            redirectAttributes.addFlashAttribute("errorPassword", "パスワードと確認用パスワードが一致しません");
            return new ModelAndView("redirect:/addUser");
        }

        // 支社と部署の組み合わせチェック -----
        boolean resultBranchDepart = userService.findByBranchDepartment(userForm.getBranchId(), userForm.getDepartmentId());
        if (!resultBranchDepart) {
            redirectAttributes.addFlashAttribute("errorBranchDepart", "支社と部署の組み合わせが不正です");
            return new ModelAndView("redirect:/addUser");
        }

        // アカウントの重複チェック -----
        // ユーザー登録時はアカウント名のみでチェック（id情報を持っていない為）
        boolean resultAccount = userService.findByAccount(null, userForm.getAccount());
        if (!resultAccount) {
            redirectAttributes.addFlashAttribute("errorAccount", "アカウントが重複しています");
            return new ModelAndView("redirect:/addUser");
        }

        // パスワードの暗号化 -----
        String encPassword = CipherUtil.encrypt(userForm.getPassword());
        userForm.setPassword(encPassword);

        userService.saveUser(userForm);
        return new ModelAndView("redirect:/admin");
    }

    // ユーザー状態の更新
    @PutMapping ("/change/{id}")
    public ModelAndView editUserStatus(@PathVariable Integer id, @RequestParam("userStatus") Integer userStatus) {
        UserForm userForm = userService.editUser(id);
        userForm.setId(id);
        userForm.setIsStopped(userStatus);

        userService.saveUser(userForm);
        return new ModelAndView("redirect:/admin");
    }
}
