package com.web.app.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.app.dao.UserDao;
import com.web.app.dbServices.DBService;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import static com.web.app.dto.UserCredentialsDto.from;

@WebServlet("/current-user")
public class UserServlet extends HttpServlet {

    private ObjectMapper objectMapper;
    private UserDao userDao;

    @Override
    public void init(ServletConfig config) {
        DBService dbService = (DBService) config.getServletContext().getAttribute("dbService");
        userDao = new UserDao(dbService.getOrCreateMongoClient("users"));
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UUID currentUserId = (UUID) req.getSession(false).getAttribute("currentUserId");
        PrintWriter writer = resp.getWriter();

        this.userDao.getModelById(currentUserId).ifPresentOrElse(user -> {
            try {
                objectMapper.writeValue(writer, from(user));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, () -> resp.setStatus(404));
    }
}
