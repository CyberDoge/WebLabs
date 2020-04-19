package com.web.app.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.app.dao.AutoDao;
import com.web.app.dao.AutoRentalDao;
import com.web.app.dao.UserDao;
import com.web.app.dbServices.DBService;
import com.web.app.dto.AutoDto;
import com.web.app.dto.AutoRentalDto;
import com.web.app.model.User;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        PrintWriter writer = resp.getWriter();

        this.userDao.getModelById(currentUserId).ifPresentOrElse(user -> {
            mapUser(writer, user);
        }, () -> resp.setStatus(404));
    }

    private void mapUser(PrintWriter writer, User user) {
        try {
            List<AutoRentalDto> autoRentalList = new ArrayList<>();
            user.getAutoRentalIds().forEach(uuid -> {
                autoRentalDao.getModelById(uuid).ifPresent(autoRental -> {
                            List<AutoDto> autoList = new ArrayList<>();
                            autoRental.getAutos().forEach(
                                    autoId -> autoDao.getModelById(autoId).ifPresent(
                                            auto -> autoList.add(AutoDto.from(auto)
                                            )
                                    )
                            );
                            autoRentalList.add(AutoRentalDto.from(autoRental.getId(), autoList));
                        }
                );
            });
            objectMapper.writeValue(writer, from(user, autoRentalList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
