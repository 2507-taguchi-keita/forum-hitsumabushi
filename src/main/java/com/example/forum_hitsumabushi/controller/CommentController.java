package com.example.forum_hitsumabushi.controller;

import com.example.forum_hitsumabushi.controller.form.CommentForm;
import com.example.forum_hitsumabushi.controller.form.UserForm;
import com.example.forum_hitsumabushi.service.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;

    //コメントを登録する(返信ボタンを押した際の処理)
    @PostMapping("/comment/{messageId}")
    public ModelAndView commentMessage(@PathVariable Integer messageId, @ModelAttribute("commentForm") @Validated CommentForm commentForm, BindingResult result,
                                       HttpSession session, RedirectAttributes redirectAttributes){
        if (result.hasErrors()) {
            commentService.findCommentById(messageId);
            session.setAttribute("messageId", messageId);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.commentForm", result);
            redirectAttributes.addFlashAttribute("commentForm", commentForm);
            return new ModelAndView("redirect:/forum-hitsumabushi");
        }
        // セッションのログインユーザーを取得
        Integer loginUserId = ((UserForm) session.getAttribute("loginUser")).getId();
        // Service に処理を依頼
        commentService.addComment(messageId, commentForm, loginUserId);
        session.removeAttribute("messageId");
        return new ModelAndView("redirect:/forum-hitsumabushi");
    }

    //コメント削除機能の実装(削除ボタンを押下した際の処理)
    @DeleteMapping("/comment/delete/{id}")
    public ModelAndView deleteComment(@PathVariable Integer id) {
        //Service層にある投稿削除の処理を呼び出して実行する
        //URLから取得してきたIDはコメントを指定する際に必要なので、引数に指定しServiceを呼出
        commentService.deleteById(id);
        // rootつまり、⑤サーバー側：投稿内容表示機能の処理へリダイレクト
        //同じ画面を表示するには、Controllerに指示する必要がある。
        return new ModelAndView("redirect:/forum-hitsumabushi");
    }

}
