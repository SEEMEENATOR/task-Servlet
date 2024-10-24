package org.example.servlet;

import org.example.dto.UserDTO;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private final UserService userService = new UserService(new UserRepository());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                Optional<UserDTO> user = userService.getUserById(id);

                if (user.isPresent()) {
                    JSONObject json = new JSONObject(user.get());
                    resp.getWriter().write(json.toString(4));
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found: " + id);
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID");
            }
        } else {
            List<UserDTO> users = userService.getAllUsers();
            JSONArray jsonArray = new JSONArray(users);
            resp.getWriter().write(jsonArray.toString(4));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();

        UserDTO userDTO;

        if ("application/json".equals(contentType)) {
            StringBuilder sb = new StringBuilder();
            String line;
            try (BufferedReader reader = req.getReader()) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            String nameParam = jsonObject.getString("name");
            String surnameParam = jsonObject.getString("surname");
            String emailParam = jsonObject.getString("email");
            String phoneParam = jsonObject.getString("phone");

            userDTO = UserDTO.builder()
                    .name(nameParam)
                    .surname(surnameParam)
                    .email(emailParam)
                    .phoneNumber(phoneParam)
                    .build();

        } else {
            String nameParam = req.getParameter("name");
            String surnameParam = req.getParameter("surname");
            String emailParam = req.getParameter("email");
            String phoneParam = req.getParameter("phone");

            userDTO = UserDTO.builder()
                    .name(nameParam)
                    .surname(surnameParam)
                    .email(emailParam)
                    .phoneNumber(phoneParam)
                    .build();
        }

        userService.addUser(userDTO);
        resp.sendRedirect(req.getContextPath() + "/user");
    }
}
