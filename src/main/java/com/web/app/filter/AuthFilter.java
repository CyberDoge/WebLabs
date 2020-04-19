package com.web.app.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final List<Path> ALLOWED_PATH = List.of(Paths.get("/login"), Paths.get("/register"), Paths.get("/"));

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Path path = Paths.get(httpServletRequest.getServletPath());
        if (ALLOWED_PATH.contains(path)) {
            chain.doFilter(request, response);
        } else {
            HttpSession session = httpServletRequest.getSession(false);
            if (session == null || session.isNew() || session.getAttribute("currentUserId") == null) {
                request.getRequestDispatcher("/login").forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
