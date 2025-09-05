package com.example.forum_hitsumabushi.controller;

import com.example.forum_hitsumabushi.controller.form.*;
import com.example.forum_hitsumabushi.service.CommentService;
import com.example.forum_hitsumabushi.service.MessageService;
import com.example.forum_hitsumabushi.service.UserService;
import com.example.forum_hitsumabushi.controller.form.UserForm;
import com.example.forum_hitsumabushi.service.dto.FilterDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @Autowired
    MessageService messageService;


    @GetMapping("/forum-hitsumabushi")
    public ModelAndView home(
            @ModelAttribute("filterForm") FilterForm filterForm,
            Model model,
            HttpSession session,
            @RequestParam(value = "error", required = false) String error,
            RedirectAttributes redirectAttributes) {

        ModelAndView mav = new ModelAndView();

        // 初回アクセス時の処理
        UserForm user = (UserForm) session.getAttribute("loginUser");
        if (user == null) {
            return new ModelAndView("forward:/login");
        }

        // 管理者権限フィルターでエラーが返ってきたらエラーメッセージを詰めてリダイレクト
        if ("invalidAccess".equals(error)) {
            redirectAttributes.addFlashAttribute("errorCode", "無効なアクセスです");
            return new ModelAndView("redirect:/forum-hitsumabushi");
        }

        // フォームから受け取った値
        LocalDate startDate = filterForm.getStartDate();
        LocalDate endDate = filterForm.getEndDate();
        FilterDto filterDto = new FilterDto();;

        // デフォルト値を設定
        if (startDate != null) {
            // startDateに時刻の情報を追加　＊atStartOfDay()：指定日の0時00分のLocalDateTimeを返す。
            filterDto.setStartDateTime(startDate.atStartOfDay());
        } else {
            // 初期値　＊2020-01-01 00:00:00
            filterDto.setStartDateTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        }

        if (endDate != null) {
            // endDateに時刻の情報を追加。
            filterDto.setEndDateTime(endDate.atTime(23, 59, 59));
        } else {
            // 初期値　＊2100-12-31 23:59:59
            filterDto.setEndDateTime(LocalDateTime.of(2100, 12, 31, 23, 59, 59));
        }

        filterDto.setCategory(filterForm.getCategory());
        List<UserMessageForm> userMessageList = messageService.findAllUserMessages(filterDto);

        mav.addObject("messageList", userMessageList);
        mav.addObject("comments", commentService.findAllUserComment());
        if(!model.containsAttribute("commentForm")){
            mav.addObject("commentForm", new CommentForm());
        }

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
    public ModelAndView adminUser(HttpSession session, Model model) {
        ModelAndView mav = new ModelAndView();

        List<UserForm> allUser = userService.findAllUser();

        UserForm loginUser = (UserForm) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);
        mav.addObject("users", allUser);
        mav.setViewName("/user");
        return mav;
    }

    // 投稿の削除
    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteMessage(@PathVariable Integer id){
        messageService.deleteMessage(id);
        return new ModelAndView("redirect:/forum-hitsumabushi");
    }
}
