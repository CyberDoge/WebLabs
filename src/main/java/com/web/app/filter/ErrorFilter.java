package com.web.app.filter;

import com.web.app.customExcpetion.NoSuchUserException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Http фильтр, для гибкой обарботки ошибок через отлов ислключений. Может, как отправлять кастомное сообщение об ошибке,
 * так и перенаправлять на нужную страницу.
 */

@WebFilter("/*")
public class ErrorFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            super.doFilter(request, response, chain);
        } catch (NoSuchUserException e) {
            response.getWriter().println(e.getErrorMessage());
            response.setStatus(e.getStatusCode());
        }
    }
}
