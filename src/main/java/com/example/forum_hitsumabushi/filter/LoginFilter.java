//package com.example.forum_hitsumabushi.filter;
//
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class LoginFilter implements Filter {
//
//    @Autowired
//    HttpSession session;
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
//
//        //型変換
//        HttpServletRequest httpRequest = (HttpServletRequest)request;
//        HttpServletResponse httpResponse = (HttpServletResponse)response;
//        session = httpRequest.getSession(false);
//        String path = httpRequest.getRequestURI();
//
//        // ログイン不要なページは通す
//        if (path.startsWith("/login") || path.startsWith("/css") || path.startsWith("/js")) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        //ログイン済みであれば通す
//        if (session != null && session.getAttribute("loginUser") != null) {
//            chain.doFilter(httpRequest, httpResponse);
//            return;
//        }
//        //ログインページにリダイレクト
//        httpResponse.sendRedirect("/login");
//    }
//
//    @Override
//    public void init(FilterConfig config) {
//    }
//
//    @Override
//    public void destroy() {
//    }
//}
