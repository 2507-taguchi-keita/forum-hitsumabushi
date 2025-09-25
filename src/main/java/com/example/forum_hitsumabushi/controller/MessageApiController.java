package com.example.forum_hitsumabushi.controller;

import com.example.forum_hitsumabushi.controller.form.UserCommentForm;
import com.example.forum_hitsumabushi.controller.form.UserMessageForm;
import com.example.forum_hitsumabushi.exception.MessageApiException;
import com.example.forum_hitsumabushi.service.CommentService;
import com.example.forum_hitsumabushi.service.MessageService;
import com.example.forum_hitsumabushi.service.dto.FilterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
//投稿とコメントをJavaScriptから扱えるようにするクラス。
public class MessageApiController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private CommentService commentService;

    // 投稿一覧を返すAPI
    @GetMapping("/messages")
    public Page<UserMessageForm> getMessages(
            //文字列型として受け取り、あとで数値に変換する。required=falseで、指定されなくてもエラーにならない
            @RequestParam(name="page", required = false) String strPage,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        // デフォルト値とバリデーション　pageパラメータが指定されてなかったら０ページ目をデフォルトとする。
        if (strPage == null) {
            strPage = "0"; // デフォルトで0ページ目
        } else if (!strPage.trim().matches("\\d+")){
            throw new MessageApiException("不正なパラメータが入力されました");
        }
        int page = Integer.parseInt(strPage);

        // DTOを組み立てる。投稿を検索する際に必要な条件をまとめる。
        FilterDto filterDto = new FilterDto();

        if (startDate != null) {
            filterDto.setStartDateTime(LocalDate.parse(startDate).atStartOfDay());
        } else {
            filterDto.setStartDateTime(LocalDateTime.of(2020, 1, 1, 0, 0));
        }

        if (endDate != null) {
            filterDto.setEndDateTime(LocalDate.parse(endDate).atTime(23, 59, 59));
        } else {
            filterDto.setEndDateTime(LocalDateTime.of(2100, 12, 31, 23, 59, 59));
        }

        filterDto.setCategory(category);

        // Service呼び出して、DBから投稿一覧を取得。ページング付きで返ってくる。結果はSpringがJSONに変換してレスポンスになる。
        return messageService.findAllUserMessages(filterDto, page);
    }

    //コメントも返すAPI
    @GetMapping("/messages/{id}/comments")
    public List<UserCommentForm> getComments(@PathVariable int id){
        //投稿IDに紐づくコメント一覧を返す。
        return commentService.findUserCommentsByMessageId(id);
    }
}