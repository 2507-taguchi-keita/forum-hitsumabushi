package com.example.forum_hitsumabushi.filter;

import com.example.forum_hitsumabushi.controller.form.UserForm;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

// URL（/admin）に対してフィルタを適用
@Component
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{

        // 型変換
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // リクエストからセッションデータ取得　＊false＝新しいセッションを作成しない
        HttpSession session = httpRequest.getSession(false);

        // セッションデータの有無を判定（true → セッションから"loginUser"属性を取得）
        if (session != null) {
            Object loginUser = session.getAttribute("loginUser");

            // loginUser属性の有無を判定（true → 部署idを取得）
            if (loginUser != null) {
                UserForm userForm = (UserForm) loginUser;
                Integer departmentId = userForm.getDepartmentId();

                // 総務人事（id＝1）以外でログインしていないかを判定（true → エラーメッセージを設定しホーム画面へリダイレクト）
                if (departmentId != 1) {
                    httpResponse.sendRedirect("/forum-hitsumabushi?error=invalidAccess");
                    return;
                }
            }
        }

        // 総務人事（id＝1）でログインしている場合は次の処理へ進む
        chain.doFilter(request, response);
    }

//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException{
//    }

    @Override
    public void destroy() {
    }
}
