package com.web.app.servlet;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.app.dao.UserDao;
import com.web.app.dbServices.DBService;
import com.web.app.dto.UserCredentialsDto;
import com.web.app.model.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDao userDao;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userDao = new UserDao((
                (DBService) config.getServletContext().getAttribute("dbService")
        ).getOrCreateMongoClient("userDao"));
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserCredentialsDto credentials = objectMapper.readValue(req.getReader(), UserCredentialsDto.class);
        Optional<User> userByLogin = this.userDao.getUserByLogin(credentials.login);
        userByLogin.filter(user ->
                BCrypt.verifyer().verify(credentials.password.toCharArray(), user.getPasswordHash()).verified
        ).ifPresentOrElse(user -> {
            req.getSession(true).setAttribute("currentUserId", user.getId());
            resp.setHeader("Access-Control-Allow-Credentials", "true");
            resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
            resp.setStatus(200);
        }, () -> {
            resp.setStatus(403);
        });
    }

    @Override
    public void destroy() {
        super.destroy();
        this.userDao.close();
    }
}
