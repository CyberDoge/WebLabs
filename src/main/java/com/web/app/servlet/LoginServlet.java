package com.web.app.servlet;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.app.customExcpetion.NoSuchUserException;
import com.web.app.dao.UserDao;
import com.web.app.dbServices.DBService;
import com.web.app.dto.UserCredentialsDto;
import com.web.app.model.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Http servlet для аутентификации пользователя.
 */

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDao userDao;
    private ObjectMapper objectMapper;

    /**
     * Получает объект {@link DBService} из контекста сервлетов и создает объект для запросов в таблицу с пользователями
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userDao = new UserDao((
                (DBService) config.getServletContext().getAttribute("dbService")
        ).getOrCreateMongoClient("userDao"));
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserCredentialsDto credentials = objectMapper.readValue(req.getReader(), UserCredentialsDto.class);
        Optional<User> userByLogin = this.userDao.getUserByLogin(credentials.login);
        User user = userByLogin.filter(u ->
                BCrypt.verifyer().verify(credentials.password.toCharArray(), u.getPasswordHash()).verified
        ).orElseThrow(NoSuchUserException::new);
        req.getSession(true).setAttribute("currentUserId", user.getId());
        resp.setStatus(200);
    }

    @Override
    public void destroy() {
        super.destroy();
        this.userDao.close();
    }
}
