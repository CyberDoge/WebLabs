package com.web.app.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Http фильтр, проверяющий есть ли у пользователя сессия, в которой он залогинен.
 * Делает редирект если сессии нет, либо пропускает если она есть.
 */

@WebFilter("/*")
public class AuthFilter extends HttpFilter {

    private static final List<Path> ALLOWED_PATH = List.of(Paths.get("/login"), Paths.get("/register"), Paths.get("/"));

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Path path = Paths.get(request.getServletPath());
        if (ALLOWED_PATH.contains(path)) {
            chain.doFilter(request, response);
        } else {
            HttpSession session = request.getSession(false);
            if (session == null || session.isNew() || session.getAttribute("currentUserId") == null) {
                request.getRequestDispatcher("/login").forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        }
    }
}
