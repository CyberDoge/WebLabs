package com.web.app.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.app.customExcpetion.NoSuchUserException;
import com.web.app.dao.AutoDao;
import com.web.app.dao.AutoRentalDao;
import com.web.app.dao.UserDao;
import com.web.app.dbServices.DBService;
import com.web.app.dto.AutoDto;
import com.web.app.dto.AutoRentalDto;
import com.web.app.dto.UserDto;
import com.web.app.model.User;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.web.app.dto.UserDto.from;

@WebServlet("/current-user")
public class UserServlet extends HttpServlet {

    private ObjectMapper objectMapper;
    private UserDao userDao;
    private AutoRentalDao autoRentalDao;
    private AutoDao autoDao;

    @Override
    public void init(ServletConfig config) {
        DBService dbService = (DBService) config.getServletContext().getAttribute("dbService");
        userDao = new UserDao(dbService.getOrCreateMongoClient("users"));
        autoRentalDao = new AutoRentalDao(dbService.getOrCreateMongoClient("autoRentalDao"));
        autoDao = new AutoDao(dbService.getOrCreateMongoClient("autoRental"));
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UUID currentUserId = (UUID) req.getSession(false).getAttribute("currentUserId");

        if (Boolean.parseBoolean(req.getParameter("download"))) {
            resp.setContentType("text/plain");
            resp.setHeader("Content-disposition", "attachment; filename=result.json");
        } else {
            resp.setContentType("application/json");
        }
        User user = this.userDao.getModelById(currentUserId).orElseThrow(() -> new NoSuchUserException(HttpServletResponse.SC_NOT_FOUND));
        objectMapper.writeValue(resp.getWriter(), mapUser(user));
    }

    private UserDto mapUser(User user) {
        List<AutoRentalDto> autoRentalDtoList = new ArrayList<>();
        autoRentalDao.getAllByIds(user.getAutoRentalIds()).forEach(autoRental -> {
            List<AutoDto> autos = autoDao.getAllByIds(autoRental.getAutos()).stream().map(AutoDto::from).collect(Collectors.toList());
            autoRentalDtoList.add(AutoRentalDto.from(autoRental.getId(), autos));
        });
        return from(user, autoRentalDtoList);
    }
}
